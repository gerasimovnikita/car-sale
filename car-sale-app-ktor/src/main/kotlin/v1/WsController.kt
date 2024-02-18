package com.github.gerasimovnikita.otus.carsale.app.ktor.v1

import CarSaleContext
import carsale.marketplace.api.apiMapper
import carsale.marketplace.api.apiRequestDeserialize
import carsale.marketplace.api.apiResponseSerialize
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.gerasimovnikita.otus.carsale.api.v1.models.IRequest
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gersimovnikita.otus.carsale.app.common.CarSaleAppSettings
import fromTransport
import helpers.addError
import helpers.asCarSaleError
import helpers.isUpdatableCommand
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import models.CarSaleWorkMode
import ru.otus.otuskotlin.marketplace.app.common.process
import toTransport
import toTransportInit

class WsHandler {
    private val mutex = Mutex()
    private val sessions = mutableSetOf<WebSocketSession>()

    suspend fun handle(session: WebSocketSession, appSettings: CarSaleAppSettings) {
        mutex.withLock {
            sessions.add(session)
        }

        val logger = appSettings.logger.logger(WsHandler::class)
        val processor = appSettings.processor

        // Handle init request
        processor.process(logger, "init",
            fromTransport = {
                // TODO remove after prod mode support
                workMode = CarSaleWorkMode.STUB
            },
            sendResponse = {
                val init = apiResponseSerialize(toTransportInit())
                session.outgoing.send(Frame.Text(init))
            }
        )

        // Handle flow
        session.incoming.receiveAsFlow().mapNotNull { it ->
            val frame = it as? Frame.Text ?: return@mapNotNull

            val jsonStr = frame.readText()

            // Handle without flow destruction
            processor.process(logger, "webSocket",
                fromTransport = {
                    val request = apiRequestDeserialize<IRequest>(jsonStr)
                    fromTransport(request)
                },
                sendResponse = {
                    try {
                        val result = apiResponseSerialize(toTransport())

                        // If change request, response is sent to everyone
                        if (isUpdatableCommand()) {
                            mutex.withLock {
                                sessions.forEach {
                                    it.send(Frame.Text(result))
                                }
                            }
                        } else {
                            session.outgoing.send(Frame.Text(result))
                        }
                    } catch (_: ClosedReceiveChannelException) {
                        mutex.withLock {
                            sessions.clear()
                        }
                    }
                })
        }.collect()
    }
}
