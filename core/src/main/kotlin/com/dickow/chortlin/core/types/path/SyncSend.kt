package com.dickow.chortlin.core.types.path

import com.dickow.chortlin.core.ast.participant.Participant

class SyncSend<T>(private val participant: Participant<T>) : Path