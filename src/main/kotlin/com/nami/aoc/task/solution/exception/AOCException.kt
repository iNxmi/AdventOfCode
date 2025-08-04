package com.nami.aoc.task.solution.exception

import io.github.oshai.kotlinlogging.KLogger

open class AOCException(
    logger: KLogger,
    message: String,
) : RuntimeException("${logger.name} -> $message")