package com.nami.task.input

class InputDuplex(
    private val aRaw: String,
    private val bRaw: String
) : Input {

    override fun getRawTestInputA(): String {
        return aRaw
    }

    override fun getRawTestInputB(): String {
        return bRaw
    }

}