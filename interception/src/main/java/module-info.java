module chortlin.interception {

    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;
    requires java.instrument;

    requires chortlin.shared;

    exports com.dickow.chortlin.interception.instrumentation;
    exports com.dickow.chortlin.interception.instrumentation.advice;
    exports com.dickow.chortlin.interception.instrumentation.strategy;
    exports com.dickow.chortlin.interception.instrumentation.strategy.factory;
}