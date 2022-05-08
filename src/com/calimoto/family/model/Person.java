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



    public int getAge(){
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birthday, now);
    }

    public String getName() {
        if( name == null )
            return "";
        return name;
    }

    public int getHeight() {
        return height;
    }

    public List<Relationship> getRelationships() {
        if(relationships == null)
            relationships = new ArrayList<>();
        return relationships;
    }


    public String getParents(){
        List<Person> parents = PersonService.findRelationship(this, ERole.PARENT);
        if ( parents == null || parents.isEmpty())
            return "";
        return parents.stream().
                map(Person::getName).
                collect(Collectors.joining(", "));
    }


    public String getGParents(){
        List<Person> gParents = PersonService.findRelationship(this, ERole.GRANDPARENT);
        if (gParents == null || gParents.isEmpty())
            return "";
        return gParents.stream().
                map(Person::getName).
                collect(Collectors.joining(", "));
    }

    public String getChildren(){
        List<Person> children = PersonService.findRelationship(this,ERole.CHILD);
        if (children == null || children.isEmpty())
            return "";
        return children.stream().
                map(Person::getName).
                collect(Collectors.joining(", "));
    }

    public String getGChildren(){
        List<Person> gChildren = PersonService.findRelationship(this,ERole.GRANDCHILD);
        if(gChildren == null || gChildren.isEmpty())
            return "";
        return gChildren.stream().
                map(Person::getName).
                collect(Collectors.joining(", "));
    }


    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return  birthday.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                + " "
                + name + " "
                + height+ "cm "
                + getAge() + " y.o \n"
                +(!getGParents().isEmpty()? "My Grandparents are:" + getGParents() +"; \n": "")
                +(!getParents().isEmpty()? "My Parents are:" + getParents() +"; \n" : "")
                +(!getChildren().isEmpty()? "My Children are:" +  getChildren() +"; \n" : "")
                +(!getGChildren().isEmpty()? "My Grandchildren are:" +  getGChildren() +"; \n": "")
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
