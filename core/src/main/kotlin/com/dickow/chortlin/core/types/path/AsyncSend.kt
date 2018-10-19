package com.dickow.chortlin.core.types.path

import com.dickow.chortlin.core.ast.participant.Participant

class AsyncSend<T>(private val participant: Participant<T>) : Path
