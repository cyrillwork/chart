package com.cyrillwork.chart;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private Integer age;

    public Message() {
    }

    public Message(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
