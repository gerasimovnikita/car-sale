import com.github.gersimovnikita.otus.carsale.logging.common.CarSaleLoggerProvider
import repo.IAdRepository

data class CarSaleCorSettings(
    val loggerProvider: CarSaleLoggerProvider = CarSaleLoggerProvider(),
    val repoStub: IAdRepository = IAdRepository.NONE,
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
){
    companion object {
        val NONE = CarSaleCorSettings()
    }
}
