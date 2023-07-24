package com.example.SpringBoot.model;

public enum Type {
    Dog("Dog"),
    Cat("Cat");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
