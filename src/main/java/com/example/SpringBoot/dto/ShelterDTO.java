package com.example.SpringBoot.dto;

import com.example.SpringBoot.model.Animal;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShelterDTO {
    private int id;
    private String name;
    private String address;
    private List<Animal> animalList;
    private String nameAndAddress;
    private int animalCount;
}
