package com.example.SpringBoot.controller;

import com.example.SpringBoot.dto.AnimalDTO;
import com.example.SpringBoot.dto.ShelterDTO;
import com.example.SpringBoot.model.Type;
import com.example.SpringBoot.service.AnimalService;
import com.example.SpringBoot.service.ShelterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.List;

@Controller
public class AnimalController extends BaseController {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private ShelterService shelterService;

    @GetMapping(value = "/animals")
    public String animalOverview(Model model, Authentication authentication) {
        List<AnimalDTO> animalList = animalService.getAllAnimals();
        model.addAttribute("animalList", animalList);

        List<String> animalTypes = Arrays.stream(Type.values())
                                        .map(Enum::toString)
                                        .toList();
        model.addAttribute("animalTypes", animalTypes);
        authentication.getPrincipal();
        addUserToModel(model, authentication);
        return "animals";
    }

    @GetMapping(value = "/animals/{animal}")
    public String printAnimalType(@PathVariable String animal, Model model) {
        List<AnimalDTO> animalList = animalService.getAllAnimals();
        List<String> animalTypes = Arrays.stream(Type.values())
                .map(Enum::toString)
                .toList();

        if (animal != null && animal.length() > 1) {
            String animalString = Character.toUpperCase(animal.charAt(0))
                    + animal.substring(1, animal.length() - 1);

            for (String type : animalTypes)
                if (type.equals(animalString)) {
                    List<AnimalDTO> typeList = animalList.stream()
                            .filter(a -> a.getType().toString().equals(animalString))
                            .toList();
                    model.addAttribute("animalTypeList", typeList);
                    animal = Character.toUpperCase(animal.charAt(0))
                            + animal.substring(1).toLowerCase() + ":";
                    model.addAttribute("animalType", animal);
                    return "animal-type";
                }
        }
        return "redirect:/animals";
    }

    private String addSheltersAndTypesAttributes(Model model) {
        List<ShelterDTO> shelterList = shelterService.getAllShelters();
        model.addAttribute("shelterList", shelterList);

        List<String> typeList = Arrays.stream(Type.values())
                .map(Enum::toString)
                .toList();
        model.addAttribute("typeList", typeList);
        return "animal-form";
    }

    @GetMapping(value = "/animal-form")
    public String addAnimals (Model model) {
        model.addAttribute("animal", new AnimalDTO());
        return addSheltersAndTypesAttributes(model);
    }

    @PostMapping(value = "/submit-animal")
    public String submitAnimal(@Valid @ModelAttribute("animal") AnimalDTO animal, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return addSheltersAndTypesAttributes(model);

        if(animal.getBreed().isBlank())
            animal.setBreed("-");

        animalService.saveAnimal(animal);
        return "redirect:/animals";
    }

    @GetMapping(value = "/delete-animal/{id}")
    public String deleteAnimal(@PathVariable("id") int animalID) {
        animalService.deleteAnimalByID(animalID);
        return "redirect:/animals";
    }

    @GetMapping(value = "/edit-animal/{id}")
    public ModelAndView editAnimal(@PathVariable("id") int animalID, Model model) {
        ModelAndView editView = new ModelAndView("edit-animal");
        AnimalDTO animal = animalService.findAnimalByID(animalID);
        ModelAndView animalOverview = new ModelAndView("redirect:/animals");
        if(animal == null)
            return animalOverview;
        editView.addObject("animal", animal);

        List<String> typeList = Arrays.stream(Type.values())
                .map(Enum::toString)
                .toList();
        model.addAttribute("typeList", typeList);
        List<ShelterDTO> shelterList = shelterService.getAllShelters();
        model.addAttribute("shelterList", shelterList);

        return editView;
    }

    @PostMapping(value = "/save-animal")
    public String saveEditedAnimal(@Valid @ModelAttribute("animal") AnimalDTO animal, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<String> typeList = Arrays.stream(Type.values())
                    .map(Enum::toString)
                    .toList();
            model.addAttribute("typeList", typeList);
            List<ShelterDTO> shelterList = shelterService.getAllShelters();
            model.addAttribute("shelterList", shelterList);
            return "edit-animal";
        }

        animalService.saveAnimal(animal);
        return "redirect:/animals";
    }
}
