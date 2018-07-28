/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swagger.swaggerapi;

import com.swagger.swaggerapi.task.BalanceTestResult;
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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ValidateBracketsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void DefaultTest() {
        BalanceTestResult result = this.restTemplate.getForObject("/tasks/validateBrackets", BalanceTestResult.class);
        assertThat(result.getInput()).isEqualTo("{[()]}");
        assertThat(result.getIsBalanced()).isEqualTo("true");
    }

    @Test

    public void BalancedTest() throws UnsupportedEncodingException {
        String underTest = "{{{[]()}[]}}";
        String request = URLEncoder.encode(underTest, "UTF-8");
        BalanceTestResult result = this.restTemplate.getForObject("/tasks/validateBrackets?input=" + request, BalanceTestResult.class);
        assertThat(result.getInput()).isEqualTo(underTest);
        assertThat(result.getIsBalanced()).isEqualTo("true");
    }

    @Test
    public void UnBalancedTest() throws UnsupportedEncodingException {
        String underTest = "{{{[]()}}}]";
        String request = URLEncoder.encode(underTest, "UTF-8");
        BalanceTestResult result = this.restTemplate.getForObject("/tasks/validateBrackets?input=" + request, BalanceTestResult.class);

        assertThat(result.getInput()).isEqualTo(underTest);
        assertThat(result.getIsBalanced()).isEqualTo("false");
    }

    @Test
    public void UnBalancedTestStartWithClosingChar() throws UnsupportedEncodingException {
        String underTest = "]{{{[]()}}}]";
        String request = URLEncoder.encode(underTest, "UTF-8");
        BalanceTestResult result = this.restTemplate.getForObject("/tasks/validateBrackets?input=" + request, BalanceTestResult.class);
        assertThat(result.getInput()).isEqualTo(underTest);
        assertThat(result.getIsBalanced()).isEqualTo("false");
    }
}
