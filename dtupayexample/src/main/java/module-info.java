module dtupayexample {
    requires spring.web;
    requires jdk.incubator.httpclient;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.beans;

    opens com.dickow.chortlin.dtupay.app to spring.core, spring.beans, spring.context, spring.web;
    opens com.dickow.chortlin.dtupay.bank to spring.core, spring.beans, spring.context, spring.web;
    opens com.dickow.chortlin.dtupay.dtu to spring.core, spring.beans, spring.context, spring.web;
    opens com.dickow.chortlin.dtupay.merchant to spring.core, spring.beans, spring.context, spring.web;
    opens com.dickow.chortlin.dtupay.shared.dto to com.fasterxml.jackson.databind;
}