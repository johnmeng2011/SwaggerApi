/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi.todo;

import com.swagger.swaggerapi.task.ErrorDetails;
import com.swagger.swaggerapi.task.ItemValidationError;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
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
    public ResponseEntity CreateMessage(@RequestBody CreateRequest req) throws SQLException {

        if (req.getText().length() < 1 || req.getText().length() > 50) {
            ItemValidationError error = new ItemValidationError(new ErrorDetails[]{new ErrorDetails("params", "text", "Must be between 1 and 50 chars long", req.getText())}, "ValidationError");
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity response;
        try {

            Message msg = new Message();
            msg.setMsg(req.getText());
            msgRepo.save(msg);
            MessageOperationResponse msgResp = new MessageOperationResponse(msg.getId(), msg.getMsg(), false, LocalDateTime.now());
            return new ResponseEntity(msgResp, HttpStatus.OK);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    @RequestMapping(method = GET, path = "/{id}")
    public ResponseEntity GetMessageById(@PathVariable("id") Integer id) {

        Optional<Message> msg = msgRepo.findById(id);
        if (msg.isPresent()) {
            Message presentMsg = msg.get();
            return new ResponseEntity(new MessageOperationResponse(presentMsg.getId(), presentMsg.getMsg(), false, LocalDateTime.now()), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ItemNotFoundError(new ResponseMessage[]{new ResponseMessage("Item with " + id.toString() + " not found")}, "NotFoundError"), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = PATCH, path = "/{id}")
    public ResponseEntity UpdateMessageById(@RequestBody UpdateRequest req, @PathVariable("id") Integer id) {

        Optional<Message> msg = msgRepo.findById(id);
        if (msg.isPresent()) {

            if (req.getText().length() < 1 || req.getText().length() > 50) {
                return new ResponseEntity(new ItemValidationError(new ErrorDetails[]{new ErrorDetails("params", "text", "Must be between 1 and 50 chars long", "")}, "ValidationError"), HttpStatus.BAD_REQUEST);
            } else {
                Message presentMsg = msg.get();
                presentMsg.setMsg(req.getText());
                msgRepo.save(presentMsg);
                return new ResponseEntity(new MessageOperationResponse(presentMsg.getId(), presentMsg.getMsg(), req.isIsCompleted(), LocalDateTime.now()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(new ItemNotFoundError(new ResponseMessage[]{new ResponseMessage("Item with " + id.toString() + " not found")}, "Not Found Error"), HttpStatus.NOT_FOUND);
        }

    }
}
