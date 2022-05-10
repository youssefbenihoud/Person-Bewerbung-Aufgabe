package com.calimoto.family.util;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RegexUtilTest {

    @Test
    public void testGetNames() {
        String testInput = "01.01.1999 John Gold Smith 180cm 1999-12-1 Robert M. White 190cm 01.01.1999 John-Gold-Smith 180cm 1999-12-1 Robert M'White Christoph 190cm";
        List<String> results = new ArrayList<>();
        results.add("John Gold Smith");
        results.add("Robert M. White");
        results.add("John-Gold-Smith");
        results.add("Robert M'White Christoph");

        assertEquals(results,RegexUtil.getNames(testInput));
    }

    @Test
    public void testFindDates() {
        List<String> results = new ArrayList<>();
        results.add("01.01.1999");
        results.add("1999-12-1");

        String testInput = "01.01.1999 John Smith 180cm 1999-12-1 Robert White 190cm";
        assertEquals(results,RegexUtil.findDates(testInput));
    }

    @Test
    public void testGetLocalDates() {
        String testInput = "01.01.1999 John Gold Smith 180cm 1999-12-2 Robert M. White 190cm 03.01.1999 John-Gold-Smith 180cm 4.12.1999 Robert M'White Christoph 190cm";
        List<LocalDate> results = new ArrayList<>();
        LocalDate date1 = LocalDate.of(1999,1,1);
        LocalDate date2 = LocalDate.of(1999,12,2);
        LocalDate date3 = LocalDate.of(1999,1,3);
        LocalDate date4 = LocalDate.of(1999,12,4);
        results.add(date1);
        results.add(date2);
        results.add(date3);
        results.add(date4);

        assertEquals(results,RegexUtil.getLocalDates(testInput));
    }

    @Test
    public void testGetHeights() {
        String testInput = "01.01.1999 John Gold Smith 180cm 1999-12-2 Robert M. White 1,90m 03.01.1999 John-Gold-Smith 1.81m 4.12.1999 Robert M'White Christoph 170cm";
        List<Integer> results = new ArrayList<>();
        int h1 = 180;
        int h2 = 190;
        int h3 = 181;
        int h4 = 170;
        results.add(h1);
        results.add(h2);
        results.add(h3);
        results.add(h4);

        assertEquals(results,RegexUtil.getHeights(testInput));
    }
}