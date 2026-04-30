package com.example.studentapp.service;

import com.example.studentapp.entity.Student;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public List<Student> getAll() {
        return repo.findAll();
    }

    public void save(Student student) {
        repo.save(student);
    }

    public Student get(Long id) {
        Student student = repo.findById(id).orElse(null);

        if (student != null && student.getCourse() != null) {
            student.getCourse().getId(); // force load
        }

        return student;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}