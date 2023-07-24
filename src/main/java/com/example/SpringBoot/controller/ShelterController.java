package com.example.SpringBoot.controller;

import com.example.SpringBoot.model.Animal;
import com.example.SpringBoot.model.Shelter;
import com.example.SpringBoot.repository.AnimalRepository;
import com.example.SpringBoot.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ShelterController {
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping(value = "/shelters")
    public String shelterOverview(Model model) {
        List<Shelter> shelterList = shelterRepository.findAll();
        model.addAttribute("shelterList", shelterList);
        return "shelters";
    }

    @GetMapping(value = "/shelter-form")
    public String addShelter (Model model) {
        model.addAttribute("shelter", new Shelter());
        return "shelter-form";
    }

    @PostMapping(value = "/submit-shelter")
    public String submitShelter(@ModelAttribute Shelter shelter) {
        if(shelter.getName().isBlank() || shelter.getAddress().isBlank())
            return "redirect:/shelter-form";

        shelterRepository.save(shelter);
        return "redirect:/shelters";
    }

    @GetMapping(value = "/shelters/{shelterID}")
    public String findShelter(@PathVariable int shelterID, Model model) {
        if(shelterRepository.findById(shelterID).isPresent()) {
            Shelter shelter = shelterRepository.findById(shelterID).get();
            String shelterName = shelter.getName() + " (" + shelter.getAddress() + ")" + ":";
            List<Animal> animalList = animalRepository.findAll()
                                                     .stream()
                                                     .filter(a -> a.getShelterID() == shelterID)
                                                     .toList();
            model.addAttribute("animalType", shelterName);
            model.addAttribute("animalTypeList", animalList);
            return "animal-type";
        }
        return "redirect:/shelters";
    }

    @GetMapping(value = "/delete-shelter/{id}")
    public String deleteShelter(@PathVariable("id") int shelterID) {
        List<Animal> animalList = animalRepository.findAll()
                                                .stream().filter(a -> a.getShelter().getId() == shelterID)
                                                .toList();
        List<Integer> animalIDList = animalList.stream()
                                                .map(Animal::getId)
                                                .toList();
        for(Integer animalID : animalIDList)
            animalRepository.deleteById(animalID);

        shelterRepository.deleteById(shelterID);
        return "redirect:/shelters";
    }

    @GetMapping(value = "/edit-shelter/{id}")
    public ModelAndView editShelter(@PathVariable("id") int shelterID, Model model) {
        ModelAndView editView = new ModelAndView("edit-shelter");
        if(shelterRepository.findById(shelterID).isPresent()) {
            Shelter shelter = shelterRepository.findById(shelterID).get();
            editView.addObject("shelter", shelter);

            List<Shelter> shelterList = shelterRepository.findAll();
            model.addAttribute("shelterList", shelterList);
        }
        return editView;
    }

    @PostMapping(value = "/save-shelter")
    public String saveEditedShelter(@ModelAttribute("shelter") Shelter shelter) {
        shelterRepository.save(shelter);
        return "redirect:/shelters";
    }
}
