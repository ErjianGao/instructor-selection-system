package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.*;
import com.erjiangao.tutorselectiontool.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ElectiveRepository electiveRepository;

    // ----------------Student CURD----------------
    public Student getStudent(int sid) {
        return studentRepository.findById(sid).orElse(null);
    }

    public Student getStudent(String sname) {
        return studentRepository.findStudentByName(sname).orElse(null);
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public List<Student> listStudents(int tid) {
        return studentRepository.findStudentsByTeacher_Id(tid)
                .orElse(List.of());
    }

    public List<Student> listStudentsByCourse(int cid) {
        List<Elective> electives = electiveRepository.findElectivesByCourse_Id(cid)
                .orElse(List.of());
        List<Student> students = new ArrayList<>();
        electives.forEach(e -> {
            Student student = e.getStudent();
            students.add(student);
        });
        return students;
    }

    public int countStudents(int tid) {
        return (int)studentRepository.count();
    }

    public Student updateStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    // ----------------Direction CURD----------------
    public Direction addDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    public Direction getDirection(int id) {
        return directionRepository.findById(id)
                .orElse(null);
    }

    public List<Direction> listDirections() {
        return directionRepository.findAll();
    }

    public List<Direction> listDirections(int sid) {
        return directionRepository.findAll();
    }

    public Direction updateDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    // ----------------Teacher CURD----------------
    public Teacher selectTeacher(int sid, int tid) {
        Teacher teacher = teacherRepository.findById(tid).orElse(null);
        Student student = studentRepository.findById(sid).orElse(null);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "抱歉，学生信息不存在");
        }
        student.setTeacher(teacher);
        studentRepository.save(student);
        return teacher;
    }
}
