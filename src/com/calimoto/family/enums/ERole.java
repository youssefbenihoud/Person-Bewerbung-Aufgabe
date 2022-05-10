package com.calimoto.family.enums;

public enum ERole {

    GRANDGRANDPARENT(-2), // optional
    GRANDPARENT(-1),
    PARENT(0),
    CHILD(1),
    GRANDCHILD(2);

    int level;

    ERole(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
