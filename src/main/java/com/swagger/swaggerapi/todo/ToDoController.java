/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.todo;

import com.google.gson.Gson;
import com.swagger.swaggerapi.task.ErrorDetails;
import com.swagger.swaggerapi.task.ItemValidationError;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jun.Meng
 */
@RestController
@RequestMapping(path = "/todo")
public class ToDoController {

    @Autowired

    private MessageRepo msgRepo;

    @RequestMapping(method = POST)
    public ResponseEntity CreateMessage(@RequestBody String map) throws SQLException {

        Gson g = new Gson();
        CreateRequest body = g.fromJson(map, CreateRequest.class);

        if (body.getText().length() < 1 || body.getText().length() > 50) {
            ItemValidationError error = new ItemValidationError(new ErrorDetails[]{new ErrorDetails("params", "text", "Must be between 1 and 50 chars long", body.getText())}, "ValidationError");
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity response;
        try {
            
            Message msg = new Message();
            msg.setMsg(body.getText());
            msgRepo.save(msg);
            MessageOperationResponse msgResp = new MessageOperationResponse(msg.getId(), msg.getMsg(), false, LocalDateTime.now());
            return new ResponseEntity(msgResp, HttpStatus.OK);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

}
