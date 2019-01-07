module chortlin.test.application {
    requires kotlin.stdlib;
    requires net.bytebuddy.agent;
    requires net.bytebuddy;
    requires java.instrument;
    requires kotlinx.coroutines.core;

    requires transitive chortlin.shared;
    requires transitive chortlin.checker;
    requires transitive chortlin.interception;
}