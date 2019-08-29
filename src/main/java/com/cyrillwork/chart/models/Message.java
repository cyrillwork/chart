package com.cyrillwork.chart.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
//@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Entity
@Table(name = "user_messages")
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=3, max=20, message = "Пароль должен быть от 3 до 20 символов")
    private String text;


    @ManyToOne(targetEntity = User.class)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    private Date createAt;

    @PrePersist
    void createAt(){
        this.createAt = new Date();
    }

}
