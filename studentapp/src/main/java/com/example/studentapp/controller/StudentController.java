package com.example.studentapp.controller;

import com.example.studentapp.entity.Student;
import com.example.studentapp.entity.Course;
import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private CourseRepository courseRepo;

    // SHOW LIST
    @GetMapping("/students")
    public String list(Model model) {
        model.addAttribute("students", service.getAll());
        return "student-list";
    }

    // SHOW FORM
    @GetMapping("/add")
    public String form(Model model) {
        Student student = new Student();
        student.setCourse(new Course());   // 🔥 IMPORTANT LINE
        model.addAttribute("student", student);
        model.addAttribute("courses", courseRepo.findAll());
        return "student-form";
    }

    // SAVE DATA
    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {

        if (student.getCourse() != null && student.getCourse().getId() != null) {
            student.setCourse(
                    courseRepo.findById(student.getCourse().getId()).orElse(null)
            );
        } else {
            student.setCourse(null);
        }

        service.save(student);
        return "redirect:/students";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.get(id));
        model.addAttribute("courses", courseRepo.findAll());
        return "student-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "WORKING";
    }
}