package models

import kotlin.jvm.JvmInline

@JvmInline
value class CarSaleAdId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CarSaleAdId("")
    }
}