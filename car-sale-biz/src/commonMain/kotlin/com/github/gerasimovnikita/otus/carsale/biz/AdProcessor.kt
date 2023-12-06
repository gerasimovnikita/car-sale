package com.github.gerasimovnikita.otus.carsale.biz

import CarSaleContext
import car.sale.cor.chain
import car.sale.cor.rootChain
import com.github.gerasimovnikita.otus.carsale.biz.groups.operation
import com.github.gerasimovnikita.otus.carsale.biz.groups.stubs
import com.github.gerasimovnikita.otus.carsale.biz.workers.*
import models.*

class CarSaleAdProcessor {
    suspend fun exec(ctx: CarSaleContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<CarSaleContext> {
            initStatus("Инициализация")

            operation("Создание", CarSaleCommand.CREATE){
                stubs("Обработка стабов"){
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadCarName("Имитация ошибки валидации названия машины")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("Чтение", CarSaleCommand.READ){
                stubs("Обработка стабов"){
                    stubReadSuccess("Имитация успешного чтения")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("Обновление", CarSaleCommand.UPDATE){
                stubs("Обработка стабов"){
                    stubUpdateSuccess("Имитация успешного обновления")
                    stubValidationBadCarName("Имитация ошибки валидации названия машины")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")

                }
            }

            operation("Удаление", CarSaleCommand.DELETE){
                stubs("Обработка стабов"){
                    stubDeleteSuccess("Имитация успешного удаления")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("Поиск", CarSaleCommand.SEARCH){
                stubs("Обработка стабов"){
                    stubSearchSuccess("Имитация успешного поиска")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("Поиск", CarSaleCommand.OFFERS){
                chain{
                    stubOffersSuccess("Имитация успешного поиска")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
        }.build()
    }
}
