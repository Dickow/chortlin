module chortlin.core {
    requires transitive kotlin.stdlib;
    requires net.bytebuddy.agent;
    requires net.bytebuddy;
    requires java.instrument;
    requires kotlinx.coroutines.core;
    requires gson;

    requires transitive chortlin.shared;
    requires transitive chortlin.checker;
    requires transitive chortlin.interception;
}