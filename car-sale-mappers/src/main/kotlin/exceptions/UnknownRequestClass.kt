package exceptions

import java.lang.RuntimeException

class UnknownRequestClass(claz: Class<*>):RuntimeException("Class $claz can not be mapped to CarSaleContext")