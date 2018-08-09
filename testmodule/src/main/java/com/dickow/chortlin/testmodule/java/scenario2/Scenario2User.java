package com.dickow.chortlin.testmodule.java.scenario2;

public class Scenario2User {
    private final Long id;
    private final String name;
    private final Boolean isAdmin;

    Scenario2User(Long id, String name, Boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }
}
