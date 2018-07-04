package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.*

class MapAPI<T1, T2, T3, T4, T5, T6> :
        IMapAPI, IMapAPI1<T1>, IMapAPI2<T1, T2>,
        IMapAPI3<T1, T2, T3>, IMapAPI4<T1, T2, T3, T4>,
        IMapAPI5<T1, T2, T3, T4, T5>, IMapAPI6<T1, T2, T3, T4, T5, T6> {
    override fun <TMapped> mapTo(mapper: () -> TMapped): IProcessorAPI<TMapped> {
        return ProcessorAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1) -> TMapped): IProcessorAPI<TMapped> {
        return ProcessorAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2) -> TMapped): IProcessorAPI<TMapped> {
        return ProcessorAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3) -> TMapped): IProcessorAPI<TMapped> {
        return ProcessorAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4) -> TMapped): IProcessorAPI<TMapped> {
        return ProcessorAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): IProcessorAPI<TMapped> {
        return ProcessorAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): IProcessorAPI<TMapped> {
        return ProcessorAPI()
    }

}