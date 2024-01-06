package repo

import models.CarSaleError


interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<CarSaleError>
}
