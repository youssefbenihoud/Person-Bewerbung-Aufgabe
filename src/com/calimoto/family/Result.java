package com.calimoto.family;

import com.calimoto.family.enums.ERole;
import com.calimoto.family.model.*;
import com.calimoto.family.service.PersonService;
import com.calimoto.family.util.PersonUtil;

import java.lang.reflect.AnnotatedArrayType;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Result {

    public static void main(String[] args) {

        // Task 1
        System.out.println("#### Task 1 ####");
        PersonUtil.generateFamily();



        // Task 2
        System.out.println("#### Task 2 ####");
        PersonUtil.readInput();

    }

}
