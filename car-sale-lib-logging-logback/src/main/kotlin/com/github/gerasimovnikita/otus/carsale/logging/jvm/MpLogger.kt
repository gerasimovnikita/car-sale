package com.github.gersimovnikita.otus.carsale.logging.jvm

import ch.qos.logback.classic.Logger
import com.github.gersimovnikita.otus.carsale.logging.common.IMpLogWrapper
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun mpLoggerLogback(logger: Logger): IMpLogWrapper = MpLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun mpLoggerLogback(clazz: KClass<*>): IMpLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun mpLoggerLogback(loggerId: String): IMpLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
