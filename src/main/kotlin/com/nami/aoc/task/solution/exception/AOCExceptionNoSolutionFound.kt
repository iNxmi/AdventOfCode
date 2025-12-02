package com.nami.aoc.task.solution.exception

import io.github.oshai.kotlinlogging.KLogger

class AOCExceptionNoSolutionFound(
    logger: KLogger
) : AOCException(logger, "No solution found.")