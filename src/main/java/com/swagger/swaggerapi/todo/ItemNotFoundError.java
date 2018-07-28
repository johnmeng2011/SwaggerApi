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
public class ItemNotFoundError {
    
    private ResponseMessage[] details;
    private String name;

    public ItemNotFoundError() {
    }

    
    public ItemNotFoundError(ResponseMessage[] details, String name) {
        this.details = details;
        this.name = name;
    }

    
    public ResponseMessage[] getDetails() {
        return details;
    }

    public void setDetails(ResponseMessage[] details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
