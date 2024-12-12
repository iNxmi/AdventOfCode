package com.nami.test

class TestInputSimplex(val raw: String) : TestInput {

    override fun getRawTestInputA(): String {
        return raw
    }

    override fun getRawTestInputB(): String {
        return raw
    }

}