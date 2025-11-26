package com.example.studentmgmt.enums;


public enum Role {

    ROLE_ADMIN("ADMIN"),
    ROLE_STUDENT("STUDENT");

    private final String simpleName;

    Role(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getFullName() {
        return this.name();
    }
}