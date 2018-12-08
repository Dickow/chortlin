module chortlin.checker {
    requires transitive kotlin.stdlib;
    requires kotlinx.coroutines.core;

    requires chortlin.shared;

    exports com.dickow.chortlin.checker.correlation;
    exports com.dickow.chortlin.checker.correlation.factory;
    exports com.dickow.chortlin.checker.correlation.builder;
    exports com.dickow.chortlin.checker.correlation.functiondefinitions;
    exports com.dickow.chortlin.checker.choreography;
    exports com.dickow.chortlin.checker.choreography.participant;
    exports com.dickow.chortlin.checker.choreography.method;
    exports com.dickow.chortlin.checker.checker;
    exports com.dickow.chortlin.checker.checker.factory;
    exports com.dickow.chortlin.checker.checker.session;
    exports com.dickow.chortlin.checker.checker.result;
    exports com.dickow.chortlin.checker.ast;
    exports com.dickow.chortlin.checker.ast.validation;
    exports com.dickow.chortlin.checker.ast.types;
}