package com.calimoto.family.model;


import com.calimoto.family.enums.ERole;
import com.calimoto.family.service.PersonService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Person {

    private String name;
    private int height;
    private LocalDate birthday;
    private List<Relationship> relationships;


    public Person() {
    }

    public Person(String name, int height, LocalDate birthdate) {
        this.name = name;
        this.height = height;
        this.birthday = birthdate;
    }


    public int getAge() {
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birthday, now);
    }

    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public int getHeight() {
        return height;
    }

    public List<Relationship> getRelationships() {
        if (relationships == null)
            relationships = new ArrayList<>();
        return relationships;
    }


    public String getParents() {
        return PersonService.printAllRelationships(this, ERole.PARENT.getLevel());
    }


    public String getGParents() {
        return PersonService.printAllRelationships(this, ERole.GRANDPARENT.getLevel());

    }

    public String getChildren() {
        return PersonService.printAllRelationships(this, ERole.CHILD.getLevel());
    }

    public String getGChildren() {
        return PersonService.printAllRelationships(this, ERole.GRANDCHILD.getLevel());

    }


    public String getInfo() {
        return name
                +" (" + getAge() + ")"
                +" ("+ getHeight() +"cm) ";
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return birthday.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                + " "
                + getInfo()
                + "\n"
                + getGParents()
                + getParents()
                + getGChildren()
                + getChildren()
                ;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }


}
