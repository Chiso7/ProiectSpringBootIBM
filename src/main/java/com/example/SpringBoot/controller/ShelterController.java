package com.example.SpringBoot.controller;

import com.example.SpringBoot.dto.AnimalDTO;
import com.example.SpringBoot.dto.ShelterDTO;
import com.example.SpringBoot.service.AnimalService;
import com.example.SpringBoot.service.ShelterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ShelterController {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private ShelterService shelterService;

    @GetMapping(value = "/shelters")
    public String shelterOverview(Model model) {
        List<ShelterDTO> shelterList = shelterService.getAllShelters();
        model.addAttribute("shelterList", shelterList);
        return "shelters";
    }

    @GetMapping(value = "/shelter-form")
    public String addShelter (Model model) {
        model.addAttribute("shelter", new ShelterDTO());
        return "shelter-form";
    }

    @PostMapping(value = "/submit-shelter")
    public String submitShelter(@Valid @ModelAttribute("shelter") ShelterDTO shelter, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "shelter-form";

        shelterService.saveShelter(shelter);
        return "redirect:/shelters";
    }

    @GetMapping(value = "/shelters/{shelterID}")
    public String findShelter(@PathVariable int shelterID, Model model) {
        ShelterDTO shelter = shelterService.findShelterByID(shelterID);
        if(shelter == null)
            return "redirect:/shelters";

        String shelterName = shelter.getName() + " (" + shelter.getAddress() + ")" + ":";
        List<AnimalDTO> animalList = animalService.getAllAnimals()
                .stream()
                .filter(a -> a.getShelterID() == shelterID)
                .toList();
        model.addAttribute("animalType", shelterName);
        model.addAttribute("animalTypeList", animalList);
        return "animal-type";
    }

    @GetMapping(value = "/delete-shelter/{id}")
    public String deleteShelter(@PathVariable("id") int shelterID) {
        List<AnimalDTO> animalList = animalService.getAllAnimals()
                .stream().filter(a -> a.getShelterID() == shelterID)
                .toList();
        List<Integer> animalIDList = animalList.stream()
                .map(AnimalDTO::getId)
                .toList();
        for(Integer animalID : animalIDList)
            animalService.deleteAnimalByID(animalID);

        shelterService.deleteShelterByID(shelterID);
        return "redirect:/shelters";
    }

    @GetMapping(value = "/edit-shelter/{id}")
    public ModelAndView editShelter(@PathVariable("id") int shelterID, Model model) {
        ModelAndView editView = new ModelAndView("edit-shelter");

        ShelterDTO shelter = shelterService.findShelterByID(shelterID);
        ModelAndView shelterOverview = new ModelAndView("redirect:/shelters");
        if(shelter == null)
            return shelterOverview;

        editView.addObject("shelter", shelter);
        List<ShelterDTO> shelterList = shelterService.getAllShelters();
        model.addAttribute("shelterList", shelterList);
        return editView;
    }

    @PostMapping(value = "/save-shelter")
    public String saveEditedShelter(@Valid @ModelAttribute("shelter") ShelterDTO shelter, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "edit-shelter";

        shelterService.saveShelter(shelter);
        return "redirect:/shelters";
    }
}
