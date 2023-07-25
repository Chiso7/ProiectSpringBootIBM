package com.example.SpringBoot.mapper;

import com.example.SpringBoot.dto.AnimalDTO;
import com.example.SpringBoot.model.Animal;
import com.example.SpringBoot.model.Shelter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimalMapper {
    public List<AnimalDTO> getAnimalDTOList(List<Animal> animalList) {
        return animalList.stream()
                .map(this::mapAnimalDTO)
                .collect(Collectors.toList());
    }

    public AnimalDTO mapAnimalDTO(Animal animal) {
        return AnimalDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .type(animal.getType())
                .breed(animal.getBreed())
                .age(animal.getAge())
                .shelterName(animal.getShelter().getName())
                .shelterID(animal.getShelterID())
                .build();
    }

    public Animal mapToAnimal(AnimalDTO animalDTO) {
        return Animal.builder()
                .id(animalDTO.getId())
                .name(animalDTO.getName())
                .type(animalDTO.getType())
                .breed(animalDTO.getBreed())
                .age(animalDTO.getAge())
                .shelter(Shelter.builder().id(animalDTO.getShelterID()).build())
                .build();
    }
}
