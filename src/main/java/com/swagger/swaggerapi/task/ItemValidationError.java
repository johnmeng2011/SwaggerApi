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
public class ItemValidationError  {

    private  ErrorDetails[] details;
    private  String name;

    public ItemValidationError(ErrorDetails[] details, String name) {
        this.details = details;
        this.name = name;
    }

    public ItemValidationError() {
    }

    public ErrorDetails[] getDetails() {
        return details;
    }

    public void setDetails(ErrorDetails[] details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

}
