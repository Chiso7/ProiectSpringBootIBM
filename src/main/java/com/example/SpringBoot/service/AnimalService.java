package com.example.SpringBoot.service;

import com.example.SpringBoot.dto.AnimalDTO;
import com.example.SpringBoot.mapper.AnimalMapper;
import com.example.SpringBoot.model.Animal;
import com.example.SpringBoot.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    AnimalMapper animalMapper;

    public List<AnimalDTO> getAllAnimals() {
        return animalMapper.getAnimalDTOList(animalRepository.findAll());
    }

    public void saveAnimal(AnimalDTO animalDTO) {
        Animal animal = animalMapper.mapToAnimal(animalDTO);
        animalRepository.save(animal);
    }

    public void deleteAnimalByID(int id) {
        animalRepository.deleteById(id);
    }

    private boolean existsByID(int id) {
        return animalRepository.findById(id).isPresent();
    }

    public AnimalDTO findAnimalByID(int id) {
        if(existsByID(id))
            return animalMapper.mapAnimalDTO(animalRepository.findById(id).get());
        return null;
    }
}
