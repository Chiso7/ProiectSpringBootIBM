package com.example.SpringBoot.controller;

import com.example.SpringBoot.model.Animal;
import com.example.SpringBoot.model.Cat;
import com.example.SpringBoot.model.Dog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AnimalTypeController {
    @GetMapping(value = "/animals/{animal}")
    public String printDogs(@PathVariable String animal, Model model) {
        Dog d1 = new Dog(1, "Labus", "Golden Retriever", 2);
        Dog d2 = new Dog(2, "Quake", "Husky", 1);
        Dog d3 = new Dog(3, "Maya", "Pug", 4);
        Cat c1 = new Cat(4, "Alfie", "British Shorthair", 1);
        Cat c2 = new Cat(5, "Colin", "Maine Coon", 3);
        List<Animal> animalList = List.of(d1,d2,d3,c1,c2);

        if (animal.equals("dogs")) {
            List<Dog> dogList = animalList.stream()
                                        .filter(a -> a.getAnimal().equals("Dog"))
                                        .map(e -> (Dog) e)
                                        .toList();

            model.addAttribute("dogList", dogList);
            return "dog";
        }
        else if (animal.equals("cats")) {
            List<Cat> catList = animalList.stream()
                                        .filter(a -> a.getAnimal().equals("Cat"))
                                        .map(e -> (Cat) e)
                                        .toList();

            model.addAttribute("catList", catList);
            return "cat";
        }
        return null;
    }
}
