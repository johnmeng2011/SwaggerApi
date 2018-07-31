/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi;

import com.swagger.swaggerapi.task.BalanceTestResult;
import com.swagger.swaggerapi.task.ItemValidationError;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Jun.Meng
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidateBracketsTest{

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void DefaultTest() {
        ItemValidationError result = this.restTemplate.getForObject("/tasks/validateBrackets", ItemValidationError.class);
        assertThat(result.getName()).isEqualTo("ValidationError");
    }

    @Test

    public void BalancedTest() throws UnsupportedEncodingException {
        String underTest = "{{{[]()}[]}}";
        String request = URLEncoder.encode(underTest, "UTF-8");
        BalanceTestResult result = this.restTemplate.getForObject("/tasks/validateBrackets?input=" + request, BalanceTestResult.class);
        assertThat(result.getInput()).isEqualTo(underTest);
        assertThat(result.isIsBalanced()).isEqualTo(true);
    }

    @Test
    public void UnBalancedTest() throws UnsupportedEncodingException {
        String underTest = "{{{[]()}}}]";
        String request = URLEncoder.encode(underTest, "UTF-8");
        BalanceTestResult result = this.restTemplate.getForObject("/tasks/validateBrackets?input=" + request, BalanceTestResult.class);

        assertThat(result.getInput()).isEqualTo(underTest);
        assertThat(result.isIsBalanced()).isEqualTo(false);
    }

    @Test
    public void UnBalancedTestStartWithClosingChar() throws UnsupportedEncodingException {
        String underTest = "]{{{[]()}}}]";
        String request = URLEncoder.encode(underTest, "UTF-8");
        BalanceTestResult result = this.restTemplate.getForObject("/tasks/validateBrackets?input=" + request, BalanceTestResult.class);
        assertThat(result.getInput()).isEqualTo(underTest);
        assertThat(result.isIsBalanced()).isEqualTo(false);
    }
}
