module chortlin.shared {
    requires kotlin.stdlib;

    exports com.dickow.chortlin.shared.exceptions;
    exports com.dickow.chortlin.shared.exceptions.factory;
    exports com.dickow.chortlin.shared.observation;
    exports com.dickow.chortlin.shared.scope;
    exports com.dickow.chortlin.shared.trace;
    exports com.dickow.chortlin.shared.annotations;

    exports com.dickow.chortlin.shared.trace.dto;
    opens com.dickow.chortlin.shared.trace.dto;
}