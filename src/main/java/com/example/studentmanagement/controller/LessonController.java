package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LessonController {
    @Autowired
    private LessonRepository lessonRepository;
    @GetMapping("/lessons")
    public  String LessonPage(ModelMap modelMap){
        modelMap.addAttribute("lessons",lessonRepository.findAll());
        return "lessons";
    }
    @GetMapping("/lessons/add")
    public String addLessonPage(ModelMap modelMap){
        return "addLesson";
    }
    @PostMapping("/lesson/add")
    public String addLesson(@ModelAttribute Lesson lesson){
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }
    @GetMapping("/lessons/delete/{id}")
    public String deleteLessons(@PathVariable("id")int id){
     lessonRepository.deleteById(id);
     return "redirect:/lessons";
    }
    @GetMapping("/lessons/update/{id}")
    public String updateLessonPage(@PathVariable("id")int id,ModelMap modelMap){
        modelMap.addAttribute("lesson",lessonRepository.getById(id));
        return "updateLesson";
    }
    @PostMapping("/lessons/update")
    public String updateLesson(@ModelAttribute Lesson lesson){
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }

}
