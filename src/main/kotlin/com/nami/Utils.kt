package com.nami

class Utils {

    companion object {
        fun getID(year: Int, day: Int): Int {
            return year * 100 + day
        }
    }

}