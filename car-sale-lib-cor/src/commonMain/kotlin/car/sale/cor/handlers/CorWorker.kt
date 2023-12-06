package car.sale.cor.handlers

import car.sale.cor.CorDslMarker
import car.sale.cor.ICorExec
import car.sale.cor.ICorWorkerDsl

class  CorWorker<T>(
    title: String,
    description: String = "",
    private val blockHandle: suspend T.() -> Unit = {},
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},
): AbstractCorExec<T>(title, description, blockOn, blockExcept){
    override suspend fun handle(context: T) = context.blockHandle()

}

@CorDslMarker
class CorWorkerDsl<T>: CorExecDsl<T>(), ICorWorkerDsl<T>{
    protected var blockHandle: suspend  T.() -> Unit = { }
    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build(): ICorExec<T> = CorWorker<T>(
        title = title,
        description = description,
        blockHandle = blockHandle,
        blockOn = blockOn,
        blockExcept = blockExcept
    )

}