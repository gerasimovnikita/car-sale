package models

import kotlin.jvm.JvmInline

@JvmInline
value class CarSaleCarId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CarSaleCarId("")
    }
}