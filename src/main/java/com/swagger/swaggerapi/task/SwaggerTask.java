/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jun.Meng
 */
@RestController
@ResponseBody
public class SwaggerTask {

    private SwaggerTaskResult result;

    @RequestMapping(value = "/tasks/validateBrackets")
    public ResponseEntity validateBrackets(@RequestParam(name = "input", required = true, defaultValue = "{[()]}") String input) {
        
        char[] tmp = input.toCharArray();
        
        if(tmp.length==0 || tmp.length >50){
            result = new ItemValidationError(new ErrorDetails("params", "text", "Must be between 1 and 50 chars long", input));
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        boolean isBalanced = true;
        Paren p = new Paren(0);
        Bracket b = new Bracket(0);
        Curly c = new Curly(0);

        for (int i = 0; i < tmp.length; i++) {
            switch (tmp[i]) {
                case '{':
                    c.increase();
                    break;
                case '}':
                    c.decrease();
                    if (c.getCount() < 0) {
                        isBalanced = false;
                        break;
                    }
                    if(b.getCount()!=0 || p.getCount()!=0){
                        isBalanced = false;
                    }
                    break;
                case '[':
                    b.increase();
                    break;
                case ']':
                    b.decrease();
                    if (b.getCount() < 0) {
                        isBalanced = false;
                        break;
                    }
                    if(c.getCount()!=0 || p.getCount() != 0 ){
                        isBalanced = false;
                    }
                    break;
                case '(':
                    p.increase();
                    break;
                case ')':
                    p.decrease();
                    if (p.getCount() < 0) {
                        isBalanced = false;
                        break;
                    }
                    if( b.getCount()!=0 || c.getCount()!=0){
                        isBalanced= false;
                    }
                    break;
                default:
                    break;
            }
        }

        if (p.getCount() != 0 || b.getCount() != 0 || c.getCount() != 0) {
            isBalanced = false;
        }
        if (isBalanced) {
            return new ResponseEntity(new BalanceTestResult(input,"true"),HttpStatus.OK);
             
        } else {
            ResponseEntity entity =  new ResponseEntity(new Temp("false"),HttpStatus.OK);
            return entity;
        }
    }
}

class Paren {

    private int count;
    public Paren(int count) {
        count = count;
    }

    public int getCount() {
        return count;
    }

    public void increase() {
        count++;
    }

    public void decrease() {
        count--;
    }

}

class Bracket {

    private int count;

    public Bracket(int count) {
        count = count;
    }

    public int getCount() {
        return count;
    }

    public void increase() {
        count++;
    }

    public void decrease() {
        count--;
    }
}

class Curly {

    private int count;

    public Curly(int count) {
        count = count;
    }

    public int getCount() {
        return count;
    }

    public void increase() {
        count++;
    }

    public void decrease() {
        count--;
    }
}

class Temp{
    private String attr;
    public Temp(String attr){
        attr = attr;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
