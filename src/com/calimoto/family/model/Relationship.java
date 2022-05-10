package com.calimoto.family.model;

public class Relationship {

    /**
     * Relationship is an object that is linked two Persons ( Parent & Child )
     * Persons could be a Parent in Relationship A, but Children in Relationship B
     * Persons can have a list of Relationships
     */
    private Person parent;
    private Person child;



    public Relationship(Person parent, Person child) {
        this.parent = parent;
        this.child = child;
    }



    public Person getParent() {
        return parent;
    }

    public void setParent(Person parent) {
        this.parent = parent;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(Person child) {
        this.child = child;
    }


}
