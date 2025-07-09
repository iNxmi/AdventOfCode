package com.nami.task

data class Verification(
    val uid: UID,
    val status: Status,
    val expected: String?,
    val result: Result
)