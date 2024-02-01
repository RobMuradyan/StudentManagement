package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;




@Entity
@Data
@Table(name = "lesson")
public class Lesson {

    @Id
    private int id;

    private String title;

    private Date duration;

    private double price;

    private Date startDate;

    @ManyToOne
    private User teacher;
}
