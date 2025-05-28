package com.nami.test

class TestInputDuplex(
    private val a: String,
    private val b: String
) : TestInput {

    override fun getRawTestInputA(): String {
        return a
    }

    override fun getRawTestInputB(): String {
        return b
    }

}