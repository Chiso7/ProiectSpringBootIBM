package com.example.SpringBoot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "breed")
    private String breed;
    @Column(name = "age")
    private int age;

    @ManyToOne
    @JoinColumn(name = "id_shelter")
    private Shelter shelter;
    public Animal(int id, String name, Type type, String breed, int age) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
    }

    public int getShelterID() {
        return shelter.getId();
    }
}

