/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.task;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Stack;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/tasks/validateBrackets")
    public ResponseEntity validateBrackets(@RequestParam(name = "input", required = true, defaultValue = "{[()]}") String input) throws UnsupportedEncodingException {

        input = URLDecoder.decode(input, "UTF-8");
        char[] tmp = input.toCharArray();

        if (tmp.length == 0 || tmp.length > 50) {
            ItemValidationError result = new ItemValidationError(new ErrorDetails[]{new ErrorDetails("params", "text", "Must be between 1 and 50 chars long", input)}, "ValidationError");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        boolean isBalanced = true;
        Stack symbolStack = new Stack();

        for (int i = 0; i < tmp.length; i++) {
            switch (tmp[i]) {
                case '{':
                    symbolStack.push('{');
                    break;
                case '}':
                    if(symbolStack.empty()) {
                        isBalanced= false;
                    }
                    else if ((char) symbolStack.peek() != '{') {
                        isBalanced = false;
                    } else {
                        symbolStack.pop();
                    }
                    break;
                case '[':
                    symbolStack.push('[');
                    break;
                case ']':
                     if(symbolStack.empty()) {
                        isBalanced= false;
                    }
                     else if ((char) symbolStack.peek() != '[') {
                        isBalanced = false;
                    } else {
                        symbolStack.pop();
                    }
                    break;
                case '(':
                    symbolStack.push('(');
                    break;
                case ')':
                    if(symbolStack.empty()) {
                        isBalanced= false;
                    }
                     else if ((char) symbolStack.peek() != '(') {
                        isBalanced = false;
                    } else {
                        symbolStack.pop();
                    }
                    break;
                default:
                    break;
            }
        }

        if (!symbolStack.empty()) {
            isBalanced = false;
        }
        if (isBalanced) {
            return new ResponseEntity(new BalanceTestResult(input, "true"), HttpStatus.OK);

        } else {
            return new ResponseEntity(new BalanceTestResult(input, "false"), HttpStatus.OK);

        }
    }
}
