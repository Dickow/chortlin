package com.dickow.chortlin.shared.annotations.util

import java.lang.reflect.Method
import kotlin.reflect.KClass

object AnnotationUtil {
    @JvmStatic
    fun isAnnotationPresent(annotation: KClass<out Annotation>, method: Method): Boolean {
        return method.getAnnotation(annotation.java) != null
    }
}