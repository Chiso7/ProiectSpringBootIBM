package com.example.SpringBoot.controller;

import com.example.SpringBoot.model.Animal;
import com.example.SpringBoot.model.Shelter;
import com.example.SpringBoot.model.Type;
import com.example.SpringBoot.repository.AnimalRepository;
import com.example.SpringBoot.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class AnimalsController {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    @GetMapping(value = "/animals")
    public String animalOverview(Model model) {
        List<Animal> animalList = animalRepository.findAll();
        model.addAttribute("animalList", animalList);
        return "animals";
    }

    @GetMapping(value = "/animals/{animal}")
    public String printAnimalType(@PathVariable String animal, Model model) {
        List<Animal> animalList = animalRepository.findAll();
        List<String> animalTypes = Arrays.stream(Type.values()).map(Enum::toString).toList();

        String animalString = Character.toUpperCase(animal.charAt(0)) + animal.substring(1, animal.length() - 1);
        for(String type : animalTypes) {
            if (type.equals(animalString)) {
                List<Animal> typeList = animalList.stream()
                        .filter(a -> a.getType().toString().equals(animalString))
                        .toList();
                model.addAttribute("animalTypeList", typeList);
                animal = Character.toUpperCase(animal.charAt(0)) + animal.substring(1).toLowerCase() + ":";
                model.addAttribute("animalType", animal);
                return "animal-type";
            }
        }
        return "redirect:/animals";
    }

    @GetMapping(value = "/animal-form")
    public String addAnimals (Model model) {
        model.addAttribute("animal", new Animal());
        List<Shelter> shelterList = shelterRepository.findAll();
        model.addAttribute("shelterList", shelterList);

        List<String> typeList = Arrays.stream(Type.values()).map(Enum::toString).toList();
        model.addAttribute("typeList", typeList);
        return "animal-form";
    }

    @PostMapping(value = "/submit-animal")
    public String submitAnimal(@ModelAttribute("animal") Animal animal) {
        if(animal.getAge() < 0 || animal.getName().isBlank())
            return "redirect:/animal-form";

        if(animal.getBreed().isBlank())
            animal.setBreed("-");

        animalRepository.save(animal);
        return "redirect:/animals";
    }

    @GetMapping(value = "/delete-animal/{id}")
    public String deleteAnimal(@PathVariable("id") int animalID) {
        animalRepository.deleteById(animalID);
        return "redirect:/animals";
    }

    @GetMapping(value = "/edit-animal/{id}")
    public ModelAndView editAnimal(@PathVariable("id") int animalID, Model model) {
        ModelAndView editView = new ModelAndView("edit-animal");
        if(animalRepository.findById(animalID).isPresent()) {
            Animal animal = animalRepository.findById(animalID).get();
            editView.addObject("animal", animal);

            List<String> typeList = Arrays.stream(Type.values()).map(Enum::toString).toList();
            model.addAttribute("typeList", typeList);
            List<Shelter> shelterList = shelterRepository.findAll();
            model.addAttribute("shelterList", shelterList);
        }
        return editView;
    }

    @PostMapping(value = "/save-animal")
    public String saveEditedAnimal(@ModelAttribute("animal") Animal animal) {
        animalRepository.save(animal);
        return "redirect:/animals";
    }

//    @GetMapping(value = "/find-animal/{id}")
//    public String findAnimal(@PathVariable("id") int animalID, Model model) {
//        if(animalRepository.findById(animalID).isPresent()) {
//            Animal animal = animalRepository.findById(animalID).get();
//            model.addAttribute("animalTypeList", animal);
//            return "animal-type";
//        }
//        return "redirect:/animals";
//    }
}
