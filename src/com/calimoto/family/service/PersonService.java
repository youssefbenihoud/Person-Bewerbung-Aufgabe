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
     *
     * @param parent
     * @param child
     */
    public static void createRelationship(Person parent, Person child) {
        if (parent != null && child != null) {

            Relationship relationship = new Relationship(parent, child);

            parent.getRelationships().add(relationship);
            child.getRelationships().add(relationship);
        }
    }

    /**
     * Method to link multiple Parents
     * with potentially multiple Children ( at least one child )
     * with each other
     *
     * @param parents
     * @param children
     */
    public static void buildFamily(List<Person> parents, Person... children) {
        if (!parents.isEmpty() && !Arrays.stream(children).toList().isEmpty()) {
            for (Person p : parents)
                for (Person c : children)
                    createRelationship(p, c);
        }
    }

    /**
     * Method to find relationships between Persons
     * based on "level"
     * e. g. : if we are looking for Grandchildren of a Person, level should be "GRANDCHILD.getLevel()"
     *
     * @param person
     * @param level
     * @return
     */
    public static List<Person> findRelationship(Person person, int level) {
        List<Person> foundPersons = new ArrayList<>(); // instantiate an empty list
        List<Relationship> relationships = person.getRelationships(); // getting all the relationships of @param person

        for (Relationship r : relationships) {
            if (level > 1) { // Grandchildren +++
                if (r.getChild() != person)
                    foundPersons = Stream.concat(foundPersons.stream(), findRelationship(r.getChild(), level - 1).stream())
                            .collect(Collectors.toList());
            } else if (level < 0) { // Grandparents +++
                if (r.getParent() != person)
                    foundPersons = Stream.concat(foundPersons.stream(), findRelationship(r.getParent(), level + 1).stream())
                            .collect(Collectors.toList());
            } else if (level == 0) { // Parents
                if (r.getParent() != person)
                    foundPersons.add(r.getParent());
            } else { // Children
                if (r.getChild() != person)
                    foundPersons.add(r.getChild());
            }
        }

        return foundPersons;
    }

    /**
     * print all the Persons including their relationships
     * with the @param person.
     *
     * @param person
     * @param level
     * @return Persons and their Role in @param person 's life as type String
     */
    public static String printAllRelationships(Person person, int level) {
        StringBuilder result = new StringBuilder();
        List<Person> relationship = findRelationship(person, level);

        if (!relationship.isEmpty()) {
            if (level > 1) { //Grandchildren +++
                result.append("My Grandchildren ");
                if (level > 2)
                    result.append("g.").append(level);
                result.append("are: ");
            } else if (level < 0) { // Grandparent +++
                result.append("My Grandparents ");
                if (level < -1)
                    result.append("g.").append(level);
                result.append("are: ");
            } else if (level == 0) { // Parents
                result.append("My Parent are: ");
            } else // Children
                result.append("My Children are: ");
            result.append(relationship
                    .stream()
                    .map(Person::getInfo)
                    .collect(Collectors.joining(", ")))
                    .append(" \n");
        }


        return result.toString();
    }

    /**
     * Method to sort the given List of Persons by Age
     * in the given Asc
     *
     * @param persons
     * @param asc:    True is Ascending, False is Descending
     */
    public static void sortByAge(List<Person> persons, boolean asc) {
        if (persons != null) {
            Comparator<Person> byAge = Comparator.comparing(Person::getAge);
            if (asc)
                persons.sort(byAge); // ascending sorting by Age
            else
                persons.sort(byAge.reversed()); // descending sorting by Age
        }
    }

    /**
     * Method to sort the given List of Persons by Height
     * in the given Asc
     *
     * @param persons
     * @param asc:    True is Ascending, False is Descending
     */
    public static void sortByHeight(List<Person> persons, boolean asc) {
        if (persons != null) {
            Comparator<Person> byHeight = Comparator.comparing(Person::getHeight);
            if (asc)
                persons.sort(byHeight); // ascending sorting by Height
            else
                persons.sort(byHeight.reversed()); // descending sorting by Height
        }
    }


    /**
     * Method to sort the given List of Persons by Height
     * Then by Age ( if Heights are equal )
     * in the given Asc
     *
     * @param persons
     * @param asc:    True is Ascending, False is Descending
     */
    public static void sortByHeightThenAge(List<Person> persons, boolean asc) {
        if (persons != null) {
            Comparator<Person> byAge = Comparator.comparing(Person::getAge);
            Comparator<Person> byHeight = Comparator.comparing(Person::getHeight);
            if (asc)
                persons.sort(byHeight.thenComparing(byAge)); // ascending sorting by Height and then sorting by Age if Heights are equal
            else
                persons.sort(byHeight.reversed().thenComparing(byAge).reversed()); // descending sorting by Height and then sorting by Age if Heights are equal.
        }
    }

    /**
     * Method to check if the given Age is valid
     * and meets the requirements given in Enum ValidAge
     *
     * @param age
     * @return True is Valid, False is Invalid
     */

    public static boolean isAgeValid(int age) {
        return !(age < EValidAge.MIN.getAge() ||
                age > EValidAge.MAX.getAge()); // true if age is not less than min and not more than max. ( min and max can be modified in EValidAge )
    }

    /**
     * Method to check if the given Name is valid
     * and meets the requirements given in Enum ValidName
     *
     * @param name
     * @return True is Valid, False is Invalid
     */

    public static boolean isNameValid(String name) {
        return !(name.trim().length() < EValidName.MIN.getNumber()
                || name.trim().length() > EValidName.MAX.getNumber()); // true, if name is not less than min chars, and not more than max chars ( max and min can be modified in EValidName )
    }

    /**
     * Method to check if the given Height is valid
     * and meets the requirements given in Enum ValidHeight
     *
     * @param height
     * @return True is Valid, False is Invalid
     */

    public static boolean isHeightValid(int height) {
        return !(height > EValidHeight.MAX.getHeight()
                || height < EValidHeight.MIN.getHeight()); // true, if Height is not less than min cm, and not more than max cm ( max and min can be modifed in EValidHeight )
    }
}
