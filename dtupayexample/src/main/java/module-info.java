module dtupayexample {
    requires jdk.incubator.httpclient;
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires com.fasterxml.jackson.databind;
    requires spring.beans;

    opens com.dickow.chortlin.dtupay.app to spring.core, spring.beans, spring.context;
    opens com.dickow.chortlin.dtupay.bank to spring.core, spring.beans, spring.context;
    opens com.dickow.chortlin.dtupay.dtu to spring.core, spring.beans, spring.context;
    opens com.dickow.chortlin.dtupay.merchant to spring.core, spring.beans, spring.context;
}