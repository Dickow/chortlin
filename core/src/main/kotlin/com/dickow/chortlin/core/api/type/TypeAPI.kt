package com.dickow.chortlin.core.api.type

import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI
import com.dickow.chortlin.core.api.type.send.ISendTypeAPI
import com.dickow.chortlin.core.api.type.start.IStartTypeAPI

interface TypeAPI : IParticipantTypeAPI, IStartTypeAPI, ISendTypeAPI