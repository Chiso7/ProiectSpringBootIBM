package com.example.SpringBoot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "shelter")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "shelter")
    private List<Animal> animalList;


    public Shelter(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int animalCount() {
        return (int) animalList.stream().count();
    }

    public String getNameAndAddress() {
        return name + " - " + address;
    }
}
