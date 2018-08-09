package com.dickow.chortlin.core.api.interfaces.trigger

interface TriggerEndpointAPI {
    fun <TClass, R> onTrigger(clazz: Class<TClass>, name: String, method: (TClass) -> R): ITriggerMapAPI
    fun <TClass, T1, R> onTrigger(clazz: Class<TClass>, name: String, method: (TClass, T1) -> R): ITriggerMapAPI1<T1>
    fun <TClass, T1, T2, R> onTrigger(clazz: Class<TClass>, name: String, method: (TClass, T1, T2) -> R): ITriggerMapAPI2<T1, T2>
    fun <TClass, T1, T2, T3, R> onTrigger(clazz: Class<TClass>, name: String, method: (TClass, T1, T2, T3) -> R): ITriggerMapAPI3<T1, T2, T3>
    fun <TClass, T1, T2, T3, T4, R> onTrigger(clazz: Class<TClass>, name: String, method: (TClass, T1, T2, T3, T4) -> R): ITriggerMapAPI4<T1, T2, T3, T4>
    fun <TClass, T1, T2, T3, T4, T5, R> onTrigger(clazz: Class<TClass>, name: String, method: (TClass, T1, T2, T3, T4, T5) -> R): ITriggerMapAPI5<T1, T2, T3, T4, T5>
    fun <TClass, T1, T2, T3, T4, T5, T6, R> onTrigger(clazz: Class<TClass>, name: String, method: (TClass, T1, T2, T3, T4, T5, T6) -> R): ITriggerMapAPI6<T1, T2, T3, T4, T5, T6>
}