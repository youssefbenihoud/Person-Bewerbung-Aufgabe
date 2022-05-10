package com.calimoto.family.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtil {

    public static List<String> getNames(String input) {
        return getValueByRegex(input,
                "\\b([a-zA-Zöüä]+[a-zA-Zöüä .,'-]+[a-zA-Zöüä]+)\\b"); // Names can have as many names as possible, including special characters
    }

    /**
     * get all the Heights values mentioned in the input
     * the height can be written in unit of Meter or Centimeter
     * the height will be saved in unit of Centimeter
     * @param input
     * @return
     */
    public static List<Integer> getHeights(String input) {
        List<String> findHeightValues = getValueByRegex(input, "((\\d{3})|(\\d+)(\\.|,)(\\d{1,2}))(?=([cm]))");
        List<Integer> results = new ArrayList<>();
        for (String s : findHeightValues)
            results.add(Integer.parseInt(s));
        return results;
    }

    /**
     * Method to extract Localdates from Input and return them a list
     * @param input
     * @return list of extracted Localdates from Input
     */
    public static List<LocalDate> getLocalDates(String input) {
        List<LocalDate> results = new ArrayList<>();
        List<String> allDates = findDates(input);

        for (String s : allDates) {
            int year = 0, month = 0, day = 0;
            for (String y : getValueByRegex(s, "(?:\\d{4})"))
                year = Integer.parseInt(y);
            for (String m : getValueByRegex(s, "(?<=(\\.|-|\\/))(\\d{1,2})(?=(\\.|-|\\/))"))
                month = Integer.parseInt(m);
            for (String d : getValueByRegex(s, "((?<=(\\.|-|\\/))(\\d{1,2})$)|(^(\\d{1,2})(?=(\\.|-|\\/)))"))
                day = Integer.parseInt(d);

            LocalDate date = LocalDate.of(year, month, day);
            results.add(date);
        }
        return results;
    }



    /**
     * Method to find all the dates mentioned in user input
     * the accepted dates are in the following form dd.MM.yyyy yyyy.MM.dd dd/MM/yyyy yyyy/MM/dd dd-MM-yyyy yyyy-MM-dd
     * @param input
     * @return list of dates as String type
     */
    static List<String> findDates(String input) {
        return getValueByRegex(input,
                "((19\\d{2}|20\\d{2})(-|.|/)(0?[1-9]|1[0-2])(-|.|/)(0?[1-9]|[12][0-9]|3[01]))" +
                        "|((0?[1-9]|[12][1-9]|3[01])(.|-|/)(0?[1-9]|1[0-2])(.|-|/)(19\\d{2}|20\\d{2}))");
        // actual date form should be either dd./-MM./-yyyy or yyyy./-MM./-dd
    }


    /**
     * Method to get from Input a list of String by specific regex Pattern
     * @param input
     * @param pattern
     * @return list of extracted String Values from Input
     */
    private static List<String> getValueByRegex(String input, String pattern) {
        List<String> results = new ArrayList<>();
        Pattern p = Pattern
                .compile(pattern);
        Matcher m = p
                .matcher(input);
        while (m.find()) {
            results.add(m.group());
        }
        return results;
    }


}
