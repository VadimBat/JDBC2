package com.goit.jdbc.dto;

import java.sql.Date;
import java.time.LocalDate;

public class Worker {
    private final int id;
    private final String name;
    private final LocalDate birthday;
    private final String level;
    private final int salary;

    public Worker(int id, String name, LocalDate birthday, String level, int salary) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.level = level;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getLevel() {
        return level;
    }

    public int getSalary() {
        return salary;
    }
}
