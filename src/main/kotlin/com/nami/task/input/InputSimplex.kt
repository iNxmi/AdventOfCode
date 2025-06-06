package com.nami.task.input

class InputSimplex(
    private val raw: String
) : Input {

    override fun getRawTestInputA(): String {
        return raw
    }

    override fun getRawTestInputB(): String {
        return raw
    }

}