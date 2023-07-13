package com.example.SpringBoot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Animal {
    private int id;
    private String name;
    private String animal;
    private String breed;
    private int age;

    public Animal(int id, String name, String animal, String breed, int age) {
        this.id = id;
        this.name = name;
        this.animal = animal;
        this.breed = breed;
        this.age = age;
    }
}
