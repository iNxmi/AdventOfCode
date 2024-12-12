package com.nami.test

class TestInputDuplex(val a: String, val b:String) : TestInput {

    override fun getRawTestInputA(): String {
        return a
    }

    override fun getRawTestInputB(): String {
        return b
    }

}