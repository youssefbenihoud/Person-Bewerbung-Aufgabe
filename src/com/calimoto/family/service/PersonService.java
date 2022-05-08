package com.calimoto.family.service;


import com.calimoto.family.enums.ERole;
import com.calimoto.family.enums.EValidAge;
import com.calimoto.family.enums.EValidHeight;
import com.calimoto.family.enums.EValidName;
import com.calimoto.family.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonService {

    /**
     * Method to link two Persons
     * Setting the Parent Person and Child Person
     * @param parent
     * @param child
     */
    public static void createRelationship(Person parent, Person child){
        if( parent != null && child != null ){

            Relationship relationship = new Relationship(parent,child);

            parent.getRelationships().add(relationship);
            child.getRelationships().add(relationship);
        }
    }

    /**
     * Method to link multiple Parents
     * with potentially multiple Children
     * with each other
     * @param parents
     * @param children
     */
    public static void buildFamily(List<Person> parents, Person... children){
        if(!parents.isEmpty() && !Arrays.stream(children).toList().isEmpty() ){
            for(Person p: parents)
                for (Person c: children)
                    createRelationship(p,c);
        }
    }

    /**
     * Method to find relationships between Persons
     * based on "role"
     * e. g. : if we are looking for Grandchildren of a Person, role should be "GRANDCHILD"
     * @param person
     * @param role
     * @return
     */
    public static List<Person> findRelationship(Person person, ERole role){
        List<Person> foundPersons = new ArrayList<>(); // instantiate an empty list
        List<Relationship> relationships = person.getRelationships(); // getting all the relationships of @param person

        for(Relationship r: relationships){
            if(role.equals(ERole.CHILD)
                    || role.equals(ERole.GRANDCHILD)){ // if we are looking for next generations of the person
                if(r.getChild() != person) // as Class Relationship have 2 Persons (Parent & Child), the @param person must only be a parent, if we are searching children/grandchildren
                foundPersons.add(r.getChild());
            }else{
                if(r.getParent() != person) // as Class Relationship have 2 Persons (Parent & Child), the @param person must only be a child, if we are searching parents/grandparents
                foundPersons.add(r.getParent());
            }
        }


        List<Person> tmpPersons = new ArrayList<>(); // instantiate an empty temporary list to save the over past / next generation
       if(role.equals(ERole.GRANDCHILD)){
           for(Person fp: foundPersons){ // looping the found persons in the first loop, foundPersons are children of @param person but parents of the next generation.
               tmpPersons = Stream.concat(tmpPersons.stream(),
                       findRelationship(fp, ERole.CHILD).stream()) // recursive method that find the list of Children of Children of @param person.
                       .collect(Collectors.toList()); // save them on temporary list
           }
           foundPersons = tmpPersons; // found persons are now the grandparents ( no more parents )
       }else if (role.equals(ERole.GRANDPARENT)){
           for(Person fp: foundPersons){ // looping the found persons in the first loop, foundPersons are parents of @param person.
               tmpPersons = Stream.concat(tmpPersons.stream(),
                       findRelationship(fp, ERole.PARENT).stream())  // recursive method that find the list of Parents of Parents of @param person.
                       .collect(Collectors.toList()); // save them on temporary list
           }
           foundPersons = tmpPersons; // found persons are now the grandchildren ( no more children )
       }

       return foundPersons;
    }

    /**
     * Method to sort the given List of Persons by Age
     * in the given Asc
     * @param persons
     * @param asc: True is Ascending, False is Descending
     */
    public static void sortByAge(List<Person> persons, boolean asc) {
        if(persons != null) {
                Comparator<Person> byAge = Comparator.comparing(Person::getAge);
            if(asc)
                persons.sort(byAge); // ascending sorting by Age
            else
                persons.sort(byAge.reversed()); // descending sorting by Age
        }
    }

    /**
     * Method to sort the given List of Persons by Height
     * in the given Asc
     * @param persons
     * @param asc: True is Ascending, False is Descending
     */
    public static void sortByHeight(List<Person> persons, boolean asc) {
        if(persons != null) {
            Comparator<Person> byHeight = Comparator.comparing(Person::getHeight);
            if(asc)
                persons.sort(byHeight); // ascending sorting by Height
            else
                persons.sort(byHeight.reversed()); // descending sorting by Height
        }
    }


    /**
     * Method to sort the given List of Persons by Age
     * Then by Height ( if ages are equal )
     * in the given Asc
     * @param persons
     * @param asc: True is Ascending, False is Descending
     */
    public static void sortByHeightThenAge(List<Person> persons, boolean asc) {
        if(persons != null) {
            Comparator<Person> byAge = Comparator.comparing(Person::getAge);
            Comparator<Person> byHeight = Comparator.comparing(Person::getHeight);
            if(asc)
                persons.sort(byHeight.thenComparing(byAge)); // ascending sorting by Height and then sorting by Age if Heights are equal
            else
                persons.sort(byHeight.reversed().thenComparing(byAge).reversed()); // descending sorting by Height and then sorting by Age if Heights are equal.
        }
    }

    /**
     * Method to check if the given Age is valid
     * and meets the requirements given in Enum ValidAge
     * @param age
     * @return True is Valid, False is Invalid
     */

    public static boolean isAgeValid(int age) {
        return !(age < EValidAge.MIN.getAge() ||
                age > EValidAge.MAX.getAge() ); // true if age is not less than min and not more than max. ( min and max can be modified in EValidAge )
    }

    /**
     * Method to check if the given Name is valid
     * and meets the requirements given in Enum ValidName
     * @param name
     * @return True is Valid, False is Invalid
     */

    public static boolean isNameValid(String name) {
        return !( name.trim().length() < EValidName.MIN.getNumber()
                || name.trim().length() > EValidName.MAX.getNumber()); // true, if name is not less than min chars, and not more than max chars ( max and min can be modified in EValidName )
    }

    /**
     * Method to check if the given Height is valid
     * and meets the requirements given in Enum ValidHeight
     * @param height
     * @return True is Valid, False is Invalid
     */

    public static boolean isHeightValid(int height) {
        return !(height > EValidHeight.MAX.getHeight()
                || height < EValidHeight.MIN.getHeight()); // true, if Height is not less than min cm, and not more than max cm ( max and min can be modifed in EValidHeight )
    }
}
