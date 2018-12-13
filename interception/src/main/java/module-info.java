module chortlin.interception {

    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires kotlinx.coroutines.core;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;
    requires java.instrument;

    requires transitive chortlin.shared;
    requires gson;

    exports com.dickow.chortlin.interception;
    exports com.dickow.chortlin.interception.configuration;
    exports com.dickow.chortlin.interception.defaults;
    exports com.dickow.chortlin.interception.instrumentation;
    exports com.dickow.chortlin.interception.instrumentation.advice;
    exports com.dickow.chortlin.interception.sending;
}