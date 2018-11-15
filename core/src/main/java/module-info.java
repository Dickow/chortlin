module chortlin.core {
    requires kotlin.stdlib;
    requires kotlin.test;
    requires kotlin.test.junit5;
    requires org.junit.jupiter.api;
    requires net.bytebuddy.agent;
    requires net.bytebuddy;
    requires java.instrument;
    requires kotlinx.coroutines.core;

    exports com.dickow.chortlin.core.choreography;
    exports com.dickow.chortlin.core.choreography.participant;
    exports com.dickow.chortlin.core.exceptions;
    exports com.dickow.chortlin.core.instrumentation;
    exports com.dickow.chortlin.core.instrumentation.strategy;
    exports com.dickow.chortlin.core.instrumentation.strategy.factory;
    exports com.dickow.chortlin.core.trace;
}