package ru.otus.otuskotlin.marketplace.app.common

import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gersimovnikita.otus.carsale.logging.common.IMpLogWrapper
import helpers.asCarSaleError
import helpers.fail
import kotlinx.datetime.Clock
import models.CarSaleCommand
import toLog

suspend fun <T> CarSaleAdProcessor.process(
    logger: IMpLogWrapper,
    logId: String,
    fromTransport: suspend CarSaleContext.() -> Unit,
    sendResponse: suspend CarSaleContext.() -> T): T {
    var command = CarSaleCommand.NONE
    return try {
        val ctx = CarSaleContext(
            timeStart = Clock.System.now(),
        )

        logger.doWithLogging(id = logId) {
            ctx.fromTransport()
            command = ctx.command

            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            ctx.sendResponse()
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            logger.error(msg = "$command handling failed")

            val ctx = CarSaleContext(
                timeStart = Clock.System.now(),
                command = command
            )

            ctx.fail(e.asCarSaleError())
            exec(ctx)
            sendResponse(ctx)
        }
    }
}
