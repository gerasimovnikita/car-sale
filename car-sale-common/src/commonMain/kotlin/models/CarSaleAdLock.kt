package models

@JvmInline
value class CarSaleAdLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CarSaleAdLock("")
    }
}