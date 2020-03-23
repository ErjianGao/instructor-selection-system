package com.erjiangao.tutorselectiontool.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    // the maximum of students the teacher can choose
    private int maxStudentNumber;
    // the minimum ranking of student who choose the teacher
    private int minRanking;

    @OneToMany(mappedBy = "teacher")
    private List<Student> students;
}