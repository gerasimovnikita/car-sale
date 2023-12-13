package com.github.gerasimovnikita.otus.carsale.biz

import CarSaleContext
import car.sale.cor.rootChain
import car.sale.cor.worker
import com.github.gerasimovnikita.otus.carsale.biz.groups.operation
import com.github.gerasimovnikita.otus.carsale.biz.groups.stubs
import com.github.gerasimovnikita.otus.carsale.biz.validation.*
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
                validation {
                    worker("Копирование полей") { carSaleAdValidating = carSaleRequest.deepCopy() }
                    worker("Очистка id") { carSaleAdValidating.id = CarSaleAdId.NONE }
                    worker("Очистка названия"){ carSaleAdValidating.carName = carSaleAdValidating.carName.trim() }
                    worker("Очистка описания") { carSaleAdValidating.description = carSaleAdValidating.description.trim() }
                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
                    validateTitleHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")
                    finishAdValidation("Завершение проверок")
                }
            }

            operation("Чтение", CarSaleCommand.READ){
                stubs("Обработка стабов"){
                    stubReadSuccess("Имитация успешного чтения")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { carSaleAdValidating = carSaleRequest.deepCopy() }
                    worker("Очистка id") { carSaleAdValidating.id = CarSaleAdId(carSaleAdValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
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

                validation {
                    worker("Копируем поля в adValidating") { carSaleAdValidating = carSaleRequest.deepCopy() }
                    worker("Очистка id") { carSaleAdValidating.id = CarSaleAdId(carSaleAdValidating.id.asString().trim()) }
                    worker("Очистка заголовка") { carSaleAdValidating.carName = carSaleAdValidating.carName.trim() }
                    worker("Очистка описания") { carSaleAdValidating.description = carSaleAdValidating.description.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }

            operation("Удаление", CarSaleCommand.DELETE){
                stubs("Обработка стабов"){
                    stubDeleteSuccess("Имитация успешного удаления")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") {
                        carSaleAdValidating = carSaleRequest.deepCopy() }
                    worker("Очистка id") { carSaleAdValidating.id = CarSaleAdId(carSaleAdValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }

            operation("Поиск", CarSaleCommand.SEARCH){
                stubs("Обработка стабов"){
                    stubSearchSuccess("Имитация успешного поиска")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { carSaleAdFilterValidating = carSaleAdFilterRequest.copy() }
                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
            }

            operation("Поиск", CarSaleCommand.OFFERS){
                stubs("Обработка стабов"){
                    stubOffersSuccess("Имитация успешного поиска")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { carSaleAdValidating = carSaleRequest.deepCopy() }
                    worker("Очистка id") { carSaleAdValidating.id = CarSaleAdId(carSaleAdValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}
