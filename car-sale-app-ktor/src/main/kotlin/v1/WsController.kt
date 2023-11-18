package com.github.gerasimovnikita.otus.carsale.app.ktor.v1

import CarSaleContext
import carsale.marketplace.api.apiMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.gerasimovnikita.otus.carsale.api.v1.models.IRequest
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import fromTransport
import helpers.addError
import helpers.asCarSaleError
import helpers.isUpdatableCommand
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.*
import models.CarSaleWorkMode
import toTransport
import toTransportInit

val sessions = mutableListOf<WebSocketSession>()

suspend fun WebSocketSession.wsHandler(processor: CarSaleAdProcessor){
    sessions += this

    //init
    val ctx = CarSaleContext()
    ctx.workMode = CarSaleWorkMode.STUB
    processor.exec(ctx)

    val init = apiMapper.writeValueAsString(ctx.toTransportInit())
    outgoing.send(Frame.Text(init))

    incoming.receiveAsFlow().mapNotNull { it ->
        val frame = it as? Frame.Text ?: return@mapNotNull

        val jsonStr = frame.readText()
        val context = CarSaleContext()

        try {
            val request = apiMapper.readValue<IRequest>(jsonStr)
            context.fromTransport(request)
            processor.exec(context)

            val result = apiMapper.writeValueAsString(context.toTransport())

            if (context.isUpdatableCommand()) {
                sessions.forEach {
                    it.send(Frame.Text(result))
                }
            } else {
                outgoing.send(Frame.Text(result))
            }
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        } catch (t: Throwable) {
            context.addError(t.asCarSaleError())

            val result = apiMapper.writeValueAsString(context.toTransportInit())
            outgoing.send(Frame.Text(result))
        }
    }.collect()
}