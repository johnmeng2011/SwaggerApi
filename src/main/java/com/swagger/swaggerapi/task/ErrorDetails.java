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
public class ErrorDetails{

    public String getLocation() {
        return location;
    }

    public String getParam() {
        return param;
    }

    public String getMsg() {
        return msg;
    }

    public String getValue() {
        return value;
    }
    
    private String location;
    private String param;
    private String msg;
    private String value;

    public ErrorDetails(String location, String param, String msg, String value){
        location=location;
        param = param;
        msg=msg;
        value = value;
    }
    
}