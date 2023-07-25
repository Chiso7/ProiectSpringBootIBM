package com.example.SpringBoot.service;

import com.example.SpringBoot.dto.ShelterDTO;
import com.example.SpringBoot.mapper.ShelterMapper;
import com.example.SpringBoot.model.Shelter;
import com.example.SpringBoot.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShelterService {
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private ShelterMapper shelterMapper;

    public List<ShelterDTO> getAllShelters() {
        return shelterMapper.getShelterDTOList(shelterRepository.findAll());
    }

    public void deleteShelterByID(int id) {
        shelterRepository.deleteById(id);
    }

    public void saveShelter(ShelterDTO shelterDTO) {
        Shelter shelter = shelterMapper.mapToShelter(shelterDTO);
        shelterRepository.save(shelter);
    }

    private boolean existsByID(int id) {
        return shelterRepository.findById(id).isPresent();
    }

    public ShelterDTO findShelterByID(int id) {
        if(existsByID(id))
            return shelterMapper.mapShelterDTO(shelterRepository.findById(id).get());
        return null;
    }
}
