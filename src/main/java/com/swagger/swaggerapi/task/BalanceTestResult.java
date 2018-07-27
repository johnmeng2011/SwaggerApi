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
public class BalanceTestResult implements SwaggerTaskResult<BalanceTestResult> {
    
    private String input;
    private String isBalanced;

    public String getInput() {
        return input;
    }

    public String getIsBalanced() {
        return isBalanced;
    }

    public BalanceTestResult(String input, String isBalanced){
        input= input;
        isBalanced = isBalanced;
    }
}
