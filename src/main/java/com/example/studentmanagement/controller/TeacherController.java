package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.enums.UserType;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class TeacherController {
    @Value("${picture.upload.directory}")
    private String uploadDirectory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/teachers")
    public String TeachersPage(ModelMap modelMap) {
        modelMap.addAttribute("teachers", userRepository.findByUserType(UserType.TEACHER));
        return "teachers";
    }

    @GetMapping("/teachers/add")
    public String addTeacherPage(ModelMap modelMap) {
        modelMap.addAttribute("teachers", userRepository.findAll());
        return "addTeacher";
    }

    @PostMapping("/teachers/add")
    public String addTeacher(@ModelAttribute User user, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
            userRepository.save(user);}

        return "redirect:/teachers";
    }

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byid = userRepository.findById(id);
        if (byid.isPresent()) {
            modelMap.addAttribute("user", byid.get());
            modelMap.addAttribute("lesson", lessonRepository.findAll());
        } else {
            return "redirect:/teachers";
        }
        return "updateTeacher";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@ModelAttribute User user, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
            ;
        } else {
            Optional<User> fromdb = userRepository.findById(user.getId());
            user.setPicName(fromdb.get().getPicName());
        }
        userRepository.save(user);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/image/delete")
    public String deleteTeacherImage(@RequestParam("id") int id) {
        Optional<User> byid = userRepository.findById(id);
        if (byid.isEmpty()) {
            return "redirect:/teachers";
        } else {
            User user = byid.get();
            String picName = user.getPicName();
            if (picName != null) {
                user.setPicName(null);
                userRepository.save(user);
                File file = new File(uploadDirectory, picName);
                if (file.exists()) {
                    file.delete();
                }
            }
            return "redirect:/teachers/update/" + user.getId();

        }
    }
}

