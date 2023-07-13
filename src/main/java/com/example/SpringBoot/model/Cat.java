package com.example.SpringBoot.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cat extends Animal {
    public Cat(int id, String name, String breed, int age) {
        super(id, name, "Cat", breed, age);
    }
}
