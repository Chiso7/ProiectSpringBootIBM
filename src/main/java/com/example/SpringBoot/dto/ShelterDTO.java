package com.example.SpringBoot.dto;

import com.example.SpringBoot.model.Animal;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShelterDTO {
    private int id;

    @Size(min=2, max=30, message = "Name must be between 2 and 30 characters long!")
    private String name;

    @Size(min=2, max=30, message = "Address must be between 2 and 30 characters long!")
    @Pattern(regexp = "^([a-zA-Z\\s]{2,30})?$", message = "Address must contain only letters!")
    private String address;

    private List<Animal> animalList;

    private String nameAndAddress;

    private int animalCount;
}
