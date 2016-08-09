package com.example.reactivemvp.model;

/**
 * Implements the Info interface.
 */
public class InfoImpl implements Info {
    private String firstName;
    private String lastName;
    private int salary;
    private @SexType int sexType;

    public InfoImpl(String firstName, String lastName, int salary, @SexType int sexType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.sexType = sexType;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public @SexType int getSex() {
        return sexType;
    }

    @Override
    public int getSalary() {
        return salary;
    }
}
