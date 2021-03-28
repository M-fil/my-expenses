package com.company.core.interfaces;

public class ExpenseCategory {
    public int id;
    public String name;
    public int userId;

    public ExpenseCategory(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }
}
