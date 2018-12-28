package com.dickow.chortlin.interception.observation

import com.dickow.chortlin.shared.observation.Observable
import java.lang.reflect.Method

class Observation(val clazz: Class<*>, val jvmMethod: Method) : Observable(clazz.canonicalName, jvmMethod.name)