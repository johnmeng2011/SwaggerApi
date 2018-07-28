/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.todo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Jun.Meng
 */
@Entity
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer Id;
    private String text;

    public Message() {
    }

    public Integer getId() {
        return Id;
    }

    public String getMsg() {
        return text;
    }

    public void setMsg(String msg) {
        this.text = msg;
    }
}
