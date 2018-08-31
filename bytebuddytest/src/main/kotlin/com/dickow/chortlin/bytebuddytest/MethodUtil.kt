package com.dickow.chortlin.bytebuddytest

class MethodUtil {
    companion object {

        inline fun <reified T, reified T1, reified T2> getMethodName(noinline method: (T, T1) -> T2): String {
            return getMethodName(T::class.java, method, T1::class.java, T2::class.java)
        }

        fun <T, T1, T2> getMethodName(clazz: Class<T>, method: (T, T1) -> T2, clazzT1: Class<T1>, clazzT2: Class<T2>): String {
            val types = arrayOf(clazzT1)
            val validMethods = clazz.methods.filter { m ->
                m.parameterCount == 1
                        && allEquals(types, m.parameterTypes)
                        && m.returnType == clazzT2
            }

            if (validMethods.size == 1) {
                return validMethods[0].name
            }

            throw IllegalArgumentException("More than one method with the same type signature for class ${clazz.name}")
        }

        private fun allEquals(list1: Array<*>, list2: Array<*>): Boolean {
            for (i in 0..(list1.size - 1)) {
                if (list1[i] != list2[i]) {
                    return false
                }
            }

            return true
        }
    }
}