package com.hsbc.service;

import com.hsbc.dao.StudentDAO;
import com.hsbc.exception.DuplicateStudentEntry;
import com.hsbc.exception.StudentNotFound;
import com.hsbc.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("service")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO dao;

    public void setDao(StudentDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean addStudent(Student student) throws DuplicateStudentEntry {
        try {
            return dao.createStudent(student);
        } catch (Exception e) {
            throw new DuplicateStudentEntry(e.getMessage(), e);
        }
    }

    @Override
    public Student getStudentById(int studentId) throws StudentNotFound {
        try {
            return dao.readStudentById(studentId);
        } catch (Exception e) {
            throw new StudentNotFound(e.getMessage(), e);
        }
    }

    @Override
    public Student editStudent(int studentId, Student student) throws StudentNotFound {
        try {
            return dao.updateStudent(studentId, student);
        } catch (Exception e) {
            throw new StudentNotFound(e.getMessage(), e);
        }
    }

    @Override
    public boolean removeStudent(int studentId) throws StudentNotFound {

        try {
            return dao.deleteStudent(studentId);
        } catch (Exception e) {
            throw new StudentNotFound(e.getMessage(), e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return dao.readAllStudents();
    }
}
