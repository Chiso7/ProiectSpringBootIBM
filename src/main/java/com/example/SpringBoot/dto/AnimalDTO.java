package com.example.SpringBoot.dto;

import com.example.SpringBoot.model.Type;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalDTO {
    private int id;

    @Size(min=2, max=30, message = "Name must be between 2 and 30 characters long!")
    @Pattern(regexp = "^([a-zA-Z\\s]{2,30})?$", message = "Name must contain only letters!")
    private String name;

    private Type type;

    @Size(max=30, message = "Breed must be under 30 characters long!")
    @Pattern(regexp = "^([a-zA-Z\\s]*)?$", message = "Breed must contain only letters!")
    private String breed;

    @NotEmpty(message = "Age cannot be null!")
    @Pattern(regexp = "^(?:[0-9]|[1-2][0-9]|30)?$", message = "Age must be between 0 and 30!")
    private String age;

    private String shelterName;

    private int shelterID;
}
