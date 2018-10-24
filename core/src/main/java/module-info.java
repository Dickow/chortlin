module com.dickow.chortlin.core {
    requires kotlin.stdlib;
    requires kotlin.test;
    requires kotlin.test.junit5;
    requires org.junit.jupiter.api;
    requires net.bytebuddy.agent;
    requires net.bytebuddy;
    requires java.instrument;

    exports com.dickow.chortlin.core.choreography;
    exports com.dickow.chortlin.core.choreography.participant;
    exports com.dickow.chortlin.core.ast.exception;
    exports com.dickow.chortlin.core.api.exceptions;
}