module chortlin.shared {
    requires kotlin.stdlib;
    requires transitive protobuf.java;
    requires transitive gson;

    exports com.dickow.chortlin.shared.exceptions;
    exports com.dickow.chortlin.shared.exceptions.factory;
    exports com.dickow.chortlin.shared.observation;
    exports com.dickow.chortlin.shared.scope;
    exports com.dickow.chortlin.shared.trace;
    exports com.dickow.chortlin.shared.annotations;
    exports com.dickow.chortlin.shared.annotations.util;
    exports com.dickow.chortlin.shared.trace.protobuf;
    exports com.dickow.chortlin.shared.transformation;
}