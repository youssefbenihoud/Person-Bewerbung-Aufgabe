package com.calimoto.family.enums;

public enum EValidName {
    MAX(12),MIN(3);

    int number;

    EValidName(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
