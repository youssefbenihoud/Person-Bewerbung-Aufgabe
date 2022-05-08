package com.calimoto.family.enums;

public enum EValidAge {
    MAX(100), MIN(18);

    int age;

    EValidAge(int age){
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
