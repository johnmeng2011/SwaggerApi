/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.todo;

/**
 *
 * @author Jun.Meng
 */
public class UpdateRequest {
    
    private String text;
    private boolean isCompleted;

    public UpdateRequest() {
    }

    public UpdateRequest(String text, boolean isCompleted) {
        this.text = text;
        this.isCompleted = isCompleted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    
}
