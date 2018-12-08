package com.dickow.chortlin.shared.observation

import java.lang.reflect.Method

class ObservableParticipant(clazz: Class<*>, method: Method) : Observable(clazz, method)