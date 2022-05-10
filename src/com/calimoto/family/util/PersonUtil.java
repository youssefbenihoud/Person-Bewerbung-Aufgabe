package com.calimoto.family.util;


import com.calimoto.family.model.Person;
import com.calimoto.family.service.PersonService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PersonUtil {

    // Task 1 : Generate a Family and display for every person, his grandparents, parents, children, grandchildren if available.

    /**
     * Method to Generate a Family for Test Purposes
     */
    public static void generateFamily() {

        Person gFather1 = new Person("David Heim", 170, LocalDate.of(1950, 1, 1));
        Person gMother1 = new Person("Marie Heim", 165, LocalDate.of(1951, 2, 2));

        Person gFather2 = new Person("Brian Keil", 171, LocalDate.of(1952, 5, 5));
        Person gMother2 = new Person("Hannah Keil", 168, LocalDate.of(1953, 6, 6));

        Person father = new Person("Adam Heim", 164, LocalDate.of(1971, 4, 4));
        Person mother = new Person("Laura Keil", 164, LocalDate.of(1970, 3, 3));

        Person c1 = new Person("Sara Heim", 159, LocalDate.of(1990, 7, 7));
        Person c2 = new Person("Luisa Heim", 158, LocalDate.of(1991, 8, 8));

        List<Person> gp1 = new ArrayList<>();
        gp1.add(gFather1);
        gp1.add(gMother1);

        List<Person> gp2 = new ArrayList<>();
        gp2.add(gFather2);
        gp2.add(gMother2);

        List<Person> p1 = new ArrayList<>();
        p1.add(father);
        p1.add(mother);

        PersonService.buildFamily(gp1, father);
        PersonService.buildFamily(gp2, mother);
        PersonService.buildFamily(p1, c1, c2);


        System.out.println(mother.toString());
    }


    // Task 2: Read the Data written in the Console

    public static void readInput() {
        Scanner sc = new Scanner(System.in);
        String again = "";

        do {
            System.out.println("Enter Persons: ");
            String str = sc.nextLine();

            List<Person> persons = saveInput(str);
            System.out.println(printSortedPersonsName(persons, true));

            System.out.println("Möchten Sie wiederholen?");
            again = sc.nextLine();

        } while (again.equalsIgnoreCase("j")
                || again.equalsIgnoreCase("Y"));
    }

    /**
     * Method to save the Input written from the User
     * and save them on the list
     * according to the demanded Validation
     *
     * @param input
     * @return List of Persons written from the User
     */
    public static List<Person> saveInput(String input) {

        List<Person> persons = new ArrayList<>();
        List<Integer> heights = RegexUtil.getHeights(input);
        List<String> names = RegexUtil.getNames(input);
        List<LocalDate> localDates = RegexUtil.getLocalDates(input);

        int size = heights.size();
        if(size == names.size()
                && names.size() == localDates.size()
                && names.size() > 0 )
            for(int i = 0 ; i < size ; i++){
                Person person = new Person();
                person.setHeight(heights.get(i));
                person.setName(names.get(i));
                person.setBirthday(localDates.get(i));
                persons.add(person);
            }

        return persons;

    }


    /**
     * method to print Persons Names and sort them
     * by Age and Height
     * on the console
     *
     * @param persons
     * @param asc
     */
    public static String printSortedPersonsName(List<Person> persons, boolean asc) {
        String result = "No Data given! Try again";
        if (persons != null) {

            persons.removeIf(p -> !PersonService.isAgeValid(p.getAge())
                    || !PersonService.isNameValid(p.getName())
                    || !PersonService.isHeightValid(p.getHeight()));

            PersonService.sortByHeightThenAge(persons, asc);

            result = persons.stream().map(Person::getInfo)
                    .collect(Collectors.joining(", "));
        }
        return result;
    }



}
