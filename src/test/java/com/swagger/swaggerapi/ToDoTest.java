/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi;

import com.swagger.swaggerapi.task.ItemValidationError;
import com.swagger.swaggerapi.todo.CreateRequest;
import com.swagger.swaggerapi.todo.ItemNotFoundError;
import com.swagger.swaggerapi.todo.MessageOperationResponse;
import com.swagger.swaggerapi.todo.UpdateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Jun.Meng
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ToDoFlow() throws Exception {

        Integer entityId;
        MessageOperationResponse resp;
       
        
        //create entity with too long message will fail
        try {
            CreateRequest req = new CreateRequest("coweinfcowiefhnoqriewfoivhnoqiurehfoiqeruhfoqiuvnopqivhqpoeirvhvqeporv");
            ItemValidationError error = this.restTemplate.postForObject("/todo", req, ItemValidationError.class);            
            assertThat(error.getDetails()[0].getLocation()).isEqualTo("params");
            assertThat(error.getDetails()[0].getMsg()).isEqualTo("Must be between 1 and 50 chars long");
            assertThat(error.getName()).isEqualTo("ValidationError");

        } catch (Exception e) {
            throw new Exception(e);
        }
        
        
        //create entity should work
        try {
            CreateRequest req = new CreateRequest("Hello World!");
            resp = this.restTemplate.postForObject("/todo", req, MessageOperationResponse.class);
            entityId = resp.getId();
            assertThat(resp.getText()).isEqualTo("Hello World!");
            assertThat(resp.isIsCompleted()).isEqualTo(false);

        } catch (Exception e) {
            throw new Exception(e);
        }
        
        //get entity should return correct object
        try{
            resp = this.restTemplate.getForObject("/todo/"+entityId.toString(), MessageOperationResponse.class);
            assertThat(resp.getId()).isEqualByComparingTo(entityId);
            assertThat(resp.getText()).isEqualTo("Hello World!");
        }
        catch (Exception e) {
            throw new Exception(e);
        }
        
        //get id not exist should return NotFoundError
        try{
            
            ItemNotFoundError error = this.restTemplate.getForObject("/todo/100", ItemNotFoundError.class);
            assertThat(error.getDetails()[0].getMessage()).isEqualTo("Item with 100 not found");
            assertThat(error.getName()).isEqualTo("NotFoundError");
        }
        catch (Exception e) {
            throw new Exception(e);
        }
        
        //Parch entity should work, but restTemplate patchForObject has a bug that prevents this from running correctly
//        try{
//            UpdateRequest req = new UpdateRequest("Good Day!", true);
//            resp = this.restTemplate.patchForObject("/todo/"+entityId.toString(),req , MessageOperationResponse.class);
//            assertThat(resp.getId()).isEqualByComparingTo(entityId);
//            assertThat(resp.getText()).isEqualTo("Good Day!");
//            
//            resp = this.restTemplate.getForObject("/todo/"+entityId.toString(), MessageOperationResponse.class);
//            assertThat(resp.getId()).isEqualByComparingTo(entityId);
//            assertThat(resp.getText()).isEqualTo("Good Day!");
//            
//        }
//         catch (Exception e) {
//            throw new Exception(e);
//        }
//        
//        //patch entity id not existing should return NotFoundError
//         try{
//            UpdateRequest req = new UpdateRequest("Good Day!", true);
//            ItemNotFoundError error = this.restTemplate.patchForObject("/todo/100",req , ItemNotFoundError.class);
//            assertThat(error.getName()).isEqualTo("NotFoundError");
//            assertThat(error.getDetails()[0].getMessage()).isEqualTo("Item with 100 not found");
//            
//        }
//         catch (Exception e) {
//            throw new Exception(e);
//        }

    }

}
