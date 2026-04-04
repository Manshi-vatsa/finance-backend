package com.manshi.financebackend.dto;
import com.manshi.financebackend.dto.SummaryResponse;
public class SummaryResponse {

    private double totalIncome;
    private double totalExpense;
    private double balance;

    // ✅ MANUAL CONSTRUCTOR (IMPORTANT)
    public SummaryResponse(double totalIncome, double totalExpense, double balance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }

    // ✅ GETTERS (IMPORTANT for response)
    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getBalance() {
        return balance;
    }
}