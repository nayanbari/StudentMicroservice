package com.hsbc.controller;

import com.hsbc.exception.DuplicateStudentEntry;
import com.hsbc.exception.StudentNotFound;
import com.hsbc.model.Course;
import com.hsbc.model.Student;
import com.hsbc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin("*")
public class StudentRESTController {
    @Autowired
    private StudentService service;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        List<Student> students = service.getAllStudents();
        for (Student student : students) {
            List<Course> courses = restTemplate.getForObject("http://localhost:9092/course/student?id=" + student.getStudentId(), List.class);
            student.setCourse(courses);
        }
        return students;
    }

    // Get student by id
    @GetMapping("")
    public ResponseEntity<Student> getStudentById(@RequestParam("id") Integer studentId) throws StudentNotFound {
        if(studentId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Student student = service.getStudentById(studentId);
        if (student == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<Course> courses = restTemplate.getForObject("http://localhost:9092/course/student?id=" + studentId, List.class);
        student.setCourse(courses);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    // Add student by post
    @PostMapping("")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        ResponseEntity<String> response = null;
        try {
            Boolean result = service.addStudent(student);
            if (!result) {
                response = new ResponseEntity<>("Student not added", HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }
            response = new ResponseEntity<>("Student added successfully", HttpStatus.CREATED);
            return response;
        } catch (DuplicateStudentEntry e) {
            e.printStackTrace();
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    // Update student by put
    @PutMapping("")
    public ResponseEntity<String> updateStudent(@RequestParam("id") int studentId, @RequestBody Student student) {
        try {
            ResponseEntity<String> response = null;
            Student result = service.editStudent(studentId, student);
            if (result == null) {
                response = new ResponseEntity<>("Student not updated", HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }
            response = new ResponseEntity<>("Student updated successfully", HttpStatus.OK);
            return response;
        } catch (StudentNotFound e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete student by delete
    @DeleteMapping("")
    public ResponseEntity<String> deleteStudent(@RequestParam("id") int studentId) {
        System.out.println("Student id: " + studentId);
        try {
            Boolean result = service.removeStudent(studentId);
            if (!result) {
                return new ResponseEntity<>("Student not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        } catch (StudentNotFound e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
