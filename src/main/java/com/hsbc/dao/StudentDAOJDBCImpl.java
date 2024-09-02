package com.hsbc.dao;

import com.hsbc.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class StudentDAOJDBCImpl implements StudentDAO {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private JdbcClient client;

    @Override
    public boolean createStudent(Student student) throws Exception {
        boolean s = checkIfStudentAlreadyExists(student.getStudentId());
        if(s) {
            throw new Exception("Student already exists");
        }
        String query = "INSERT INTO Student VALUES(?, ?, ?)";
        // int result = template.update(query, student.getStudentId(), student.getStudentName(), student.getStudentScore(), student.getDept().getDeptId());
        int result = client.sql(query)
                .param(student.getStudentId())
                .param(student.getStudentName())
                .param(student.getStudentScore())
                .update();
        return result == 1;
    }

    @Override
    public Student readStudentById(int studentId) throws Exception {
        if(isStudentTableEmpty()) {
            return null;
        }
        String query = "SELECT * FROM Student WHERE student_id = ?";
        List<Student> students = client.sql(query)
                .param(studentId)
                .query(new StudentRowMapper())
                .list();

        if(students.isEmpty()) {
            throw new Exception("Student not found");
        } else if(!students.isEmpty()) {
            return students.get(0);
        }
        throw new Exception("Student not found");
    }

    @Override
    public Student updateStudent(int studentId, Student student) throws Exception {
        Student s = readStudentById(studentId);
        if(s == null) {
            throw new Exception("Student does not exist");
        }
        String query = "UPDATE Student SET student_name = ?, student_score = ? WHERE student_id = ?";
        // int result = template.update(query, student.getStudentName(), student.getStudentScore(), studentId);
        int result = client.sql(query)
                .param(student.getStudentName())
                .param(student.getStudentScore())
                .param(studentId)
                .update();
        if(result == 1) {
            return student;
        }
        return null;
    }

    @Override
    public boolean deleteStudent(int studentId) throws Exception {
        Student s = readStudentById(studentId);
        if(s == null) {
            throw new Exception("Student does not exist");
        }
        String query = "DELETE FROM Student WHERE student_id = ?";
        // int result = template.update(query, studentId);
        int result = client.sql(query)
                .param(studentId)
                .update();
        return result == 1;
    }

    @Override
    public List<Student> readAllStudents() {
        String query = "SELECT * FROM Student";
        // List<Student> students = template.query(query, new StudentRowMapper());
        List<Student> students = client.sql(query)
                .query(new StudentRowMapper())
                .list();
        return students;
    }

    private boolean isStudentTableEmpty() {
        String query = "SELECT * FROM Student";
        List<Student> students = client.sql(query)
                .query(new StudentRowMapper())
                .list();
        // int count = template.queryForObject(query, Integer.class);
        return students.isEmpty() ? true : false;
    }

    private boolean checkIfStudentAlreadyExists(int studentId) {
        if(isStudentTableEmpty()) {
            return false;
        }
        String query = "SELECT * FROM Student WHERE student_id = ?";
        List<Student> students = client.sql(query)
                .param(studentId)
                .query(new StudentRowMapper())
                .list();
        return !students.isEmpty();
    }
}
