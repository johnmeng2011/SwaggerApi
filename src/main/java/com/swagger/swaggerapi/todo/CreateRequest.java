/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.todo;

import java.io.Serializable;

/**
 *
 * @author Jun.Meng
 */
public class CreateRequest implements Serializable{
    private String text;

    public CreateRequest(String text) {
        this.text = text;
    }
    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
