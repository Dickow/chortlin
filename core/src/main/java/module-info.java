module com.dickow.chortlin.core {
    requires kotlin.stdlib;
    requires kotlin.test;

    exports com.dickow.chortlin.core.choreography;
    exports com.dickow.chortlin.core.choreography.participant;
    exports com.dickow.chortlin.core.ast.exception;
    exports com.dickow.chortlin.core.api.exceptions;
}