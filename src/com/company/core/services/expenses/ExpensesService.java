package com.company.core.services.expenses;

import com.company.core.interfaces.Expense;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.database.DatabaseHandler;
import com.company.modules.Expenses.Expenses;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class ExpensesService {
    private DatabaseHandler dbHandler;

    public ExpensesService() {
        dbHandler = DatabaseHandler.getInstance();
    }

    public ArrayList<ExpenseCategory> getAllCategories() {
        try {
            ResultSet result = dbHandler.get("SELECT * FROM expense_categrories");
            ArrayList<ExpenseCategory> categories = new ArrayList<ExpenseCategory>();

            while (result.next()) {
                ExpenseCategory category = new ExpenseCategory(
                        result.getInt("id"),
                        result.getString("name")
                );
                categories.add(category);
            }

            return categories;
        } catch (Exception error) {
            return null;
        }
    }

    public ExpenseCategory getCategoryById(int categoryId) {
        try {
            ResultSet result = dbHandler.get("SELECT * FROM expense_categrories WHERE `id`=" + categoryId);

             if (result.next()) {
                ExpenseCategory category = new ExpenseCategory(
                        result.getInt("id"),
                        result.getString("name")
                );
                return category;
            }

            return null;
        } catch (Exception error) {
            return null;
        }
    }

    public ArrayList<Expense> getAllExpenses() {
        try {
            ResultSet result = dbHandler.get("SELECT * FROM sys.expenses WHERE userId=" + Expenses.getAuthedUserId());
            ArrayList<Expense> expenses = new ArrayList<Expense>();
            while (result.next()) {
                ExpenseCategory expenseCategory = getCategoryById(result.getInt("category"));
                Date date = result.getDate("date");
                LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(date));

                Expense expense = new Expense(
                        result.getFloat("amount"),
                        result.getString("currency"),
                        result.getString("description"),
                        localDate
                )
                        .setExpenseId(result.getInt("userId"))
                        .setExpenseCategory(expenseCategory)
                        .setExpenseId(result.getInt("id"));
                expenses.add(expense);
            }
            Collections.sort(expenses, (expense1, expense2) -> expense2.getDate().compareTo(expense1.getDate()));

            return expenses;
        } catch (Exception error) {
            return null;
        }
    }

    static public float calculateTotalExpenses(ArrayList<Expense> expenses) {
        float sum = 0;
        for (Expense expense: expenses) {
            sum += expense.getAmount();
        }

        return sum;
    }

    public HashMap<String, Integer> createNewExpense(
            int userId, float amount, String currency,
            String description, int categoryId, LocalDate date
    ) {
        String listOfParams = "(userId, amount, currency, date, description, category)";
        String repeatedParams = ("'%s',").repeat(6);
        String listOfParamsToInsert = "(" + repeatedParams.substring(0, repeatedParams.length() - 1) + ")";
        String queryString = listOfParams + " VALUES " + listOfParamsToInsert;
        String query = String.format(
                "INSERT INTO expenses " + queryString,
                userId, amount, currency, date, description, categoryId
        );
        return dbHandler.insert(query);
    }

    public HashMap<String, Integer> updateExpense(
            int expenseId, int userId, float amount, String currency,
            String description, int categoryId, LocalDate date
    ) {
        String queryString = "userId='%s', amount='%s', currency='%s', date='%s', description='%s', category='%s'";
        String query = String.format(
                "UPDATE expenses SET " + queryString + " WHERE id='" + expenseId + "'",
                userId, amount, currency, date, description, categoryId
        );

        return dbHandler.update(query);
    }

    public HashMap<String, Integer> deleteExpense(int expenseId) {
        String query = "DELETE FROM expenses WHERE id='" + expenseId + "'";
        return dbHandler.delete(query);
    }
}
