package com.example.SpringBoot.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Dog extends Animal{
    public Dog(int id, String name, String breed, int age) {
        super(id, name, "Dog", breed, age);
    }
}
