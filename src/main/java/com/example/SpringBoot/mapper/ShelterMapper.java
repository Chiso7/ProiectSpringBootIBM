package com.example.SpringBoot.mapper;

import com.example.SpringBoot.dto.ShelterDTO;
import com.example.SpringBoot.model.Shelter;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShelterMapper {
    public List<ShelterDTO> getShelterDTOList(List<Shelter> shelterList) {
        return shelterList.stream()
                .map(this::mapShelterDTO)
                .collect(Collectors.toList());
    }

    public ShelterDTO mapShelterDTO(Shelter shelter) {
        return ShelterDTO.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .address(shelter.getAddress())
                .nameAndAddress(shelter.getNameAndAddress())
                .animalList(shelter.getAnimalList())
                .animalCount(shelter.animalCount())
                .build();
    }

    public Shelter mapToShelter(ShelterDTO shelterDTO) {
        return Shelter.builder()
                .id(shelterDTO.getId())
                .name(shelterDTO.getName())
                .address(shelterDTO.getAddress())
                .animalList(shelterDTO.getAnimalList())
                .build();
    }
}
