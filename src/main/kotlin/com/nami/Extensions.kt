package com.nami

//Y15D07
fun UShort.shl(bits: Int): UShort {
    return this.toInt().shl(bits).toUShort()
}

//Y15D07
fun UShort.shr(bits: Int): UShort {
    return this.toInt().shr(bits).toUShort()
}