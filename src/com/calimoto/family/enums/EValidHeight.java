package com.calimoto.family.enums;

public enum EValidHeight {
    MAX(200), MIN(150);

    int height;

    EValidHeight(int height){
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
