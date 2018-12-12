package com.dickow.chortlin.checker.receiver

import com.dickow.chortlin.checker.checker.OnlineChecker
import com.dickow.chortlin.checker.checker.session.InMemorySessionManager
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.receiver.implementations.AsynchronousReceiver
import com.dickow.chortlin.checker.receiver.implementations.SynchronousReceiver

object ChortlinReceiverFactory {
    @JvmStatic
    fun setupAsyncReceiver(choreographies: List<Choreography>, errorCallback: ChortlinResultCallback) : ChortlinReceiver {
        return AsynchronousReceiver(OnlineChecker(InMemorySessionManager(choreographies)), errorCallback)
    }

    @JvmStatic
    fun setupSynchronousReceiver(choreographies: List<Choreography>, errorCallback: ChortlinResultCallback) : ChortlinReceiver {
        return SynchronousReceiver(OnlineChecker(InMemorySessionManager(choreographies)), errorCallback)
    }
}