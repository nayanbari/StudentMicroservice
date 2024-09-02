package com.hsbc.dao;

import com.hsbc.model.Student;

import java.util.List;

public interface StudentDAO {
    public boolean createStudent(Student student) throws Exception;
    public Student readStudentById(int studentId) throws Exception;
    public Student updateStudent(int studentId, Student student) throws Exception;
    public boolean deleteStudent(int studentId) throws Exception;
    public List<Student> readAllStudents();
}
