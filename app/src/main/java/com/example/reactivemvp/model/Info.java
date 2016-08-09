package com.example.reactivemvp.model;

/**
 * Represents how we deal with data internally within the application.
 */
public interface Info {
    String getFirstName();
    String getLastName();
    @SexType int getSex();
    int getSalary();
}
