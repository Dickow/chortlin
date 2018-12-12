package com.dickow.chortlin.shared.observation

import java.lang.reflect.Method

class Observation(clazz: Class<*>, method: Method) : Observable(clazz, method)