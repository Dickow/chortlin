module chortlin.checker {
    requires kotlin.stdlib;

    requires transitive chortlin.shared;

    exports com.dickow.chortlin.checker.correlation;
    exports com.dickow.chortlin.checker.correlation.factory;
    exports com.dickow.chortlin.checker.correlation.builder;
    exports com.dickow.chortlin.checker.choreography;
    exports com.dickow.chortlin.checker.choreography.participant;
    exports com.dickow.chortlin.checker.checker;
    exports com.dickow.chortlin.checker.checker.factory;
    exports com.dickow.chortlin.checker.checker.session;
    exports com.dickow.chortlin.checker.checker.result;
    exports com.dickow.chortlin.checker.ast;
    exports com.dickow.chortlin.checker.ast.validation;
    exports com.dickow.chortlin.checker.ast.types;
    exports com.dickow.chortlin.checker.ast.types.factory;
    exports com.dickow.chortlin.checker.correlation.path;
    exports com.dickow.chortlin.checker.trace;
    exports com.dickow.chortlin.checker.trace.value;
}