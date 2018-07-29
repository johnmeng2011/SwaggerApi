/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.task;

/**
 *
 * @author Jun.Meng
 */
public class BalanceTestResult  {
    
    private  String input;
    private  boolean isBalanced;

    public void setInput(String input) {
        this.input = input;
    }

    public void setIsBalanced(boolean isBalanced) {
        this.isBalanced = isBalanced;
    }

    public BalanceTestResult() {
    }

    public String getInput() {
        return input;
    }

    public boolean isIsBalanced() {
        return isBalanced;
    }

    public BalanceTestResult(String input, boolean isBalanced) {
        this.input = input;
        this.isBalanced = isBalanced;
    }
        
}
