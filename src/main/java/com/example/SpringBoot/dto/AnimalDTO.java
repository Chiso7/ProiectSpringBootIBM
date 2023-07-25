package com.example.SpringBoot.dto;

import com.example.SpringBoot.model.Type;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalDTO {
    private int id;
    private String name;
    private Type type;
    private String breed;
    private int age;
    private String shelterName;
    private int shelterID;
}
