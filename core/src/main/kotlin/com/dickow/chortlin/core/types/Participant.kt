package com.dickow.chortlin.core.types

import java.lang.reflect.Method

class Participant<T>(val clazz: Class<T>, val method: Method)