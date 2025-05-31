package com.nami.task.test

class TestInputDuplex(
    private val aRaw: String,
    private val bRaw: String
) : TestInput {

    override fun getRawTestInputA(): String {
        return aRaw
    }

    override fun getRawTestInputB(): String {
        return bRaw
    }

}