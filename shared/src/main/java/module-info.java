module chortlin.shared {
    requires kotlin.stdlib;
    requires transitive protobuf.java;

    exports com.dickow.chortlin.shared.exceptions;
    exports com.dickow.chortlin.shared.observation;
    exports com.dickow.chortlin.shared.scope;
    exports com.dickow.chortlin.shared.trace.protobuf;
    exports com.dickow.chortlin.shared.constants;
}