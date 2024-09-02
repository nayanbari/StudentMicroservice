package com.hsbc.service;

import com.hsbc.exception.DuplicateStudentEntry;
import com.hsbc.exception.StudentNotFound;
import com.hsbc.model.Student;

import java.util.List;

public interface StudentService {
    public boolean addStudent(Student student) throws DuplicateStudentEntry;
    public Student getStudentById(int studentId) throws StudentNotFound;
    public Student editStudent(int studentId, Student student) throws StudentNotFound;
    public boolean removeStudent(int studentId) throws StudentNotFound;
    public List<Student> getAllStudents();
}
