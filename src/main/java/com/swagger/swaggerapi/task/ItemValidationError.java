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

    private final ErrorDetails details;
    private final String name;

    public ItemValidationError(ErrorDetails details, String name) {
        this.details = details;
        this.name = name;
    }

    public ErrorDetails getDetails() {
        return details;
    }

    public String getName() {
        return name;
    }

}
