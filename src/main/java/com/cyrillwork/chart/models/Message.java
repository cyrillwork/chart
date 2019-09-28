package com.cyrillwork.chart.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
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

    private String fileName;

    public String getFileNameCodeString()
    {
        if(fileName == null)
        {
            return "";
        }
        //String result = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.indexOf('.') );
        String result = fileName.substring(fileName.lastIndexOf(File.separator) + 1);

        return result;
    }

    public String getFullFileNameString()
    {
        if(fileName == null)
        {
            return "";
        }
        return fileName;
    }

    public boolean hasFile(){
        return !((fileName == null)||(fileName.equals("")));
    }

}
