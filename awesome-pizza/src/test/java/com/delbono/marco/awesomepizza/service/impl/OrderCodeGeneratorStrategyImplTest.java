package com.delbono.marco.awesomepizza.service.impl;

import com.delbono.marco.awesomepizza.AwesomePizzaApplication;
import com.delbono.marco.awesomepizza.service.OrderCodeGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AwesomePizzaApplication.class)
@AutoConfigureMockMvc
class OrderCodeGeneratorStrategyImplTest {
    @Autowired
    private OrderCodeGeneratorStrategyImpl strategy;

    @Test
    void generateOrderCode() {
        long orderId = 1L;
        String orderIdString = Long.toString(orderId);
        String code = strategy.generateOrderCode(orderId);
        Assertions.assertNotNull(code);
        int codeLength = strategy.getPrefix().length() + strategy.getFormat().length() + orderIdString.length() + 2;
        Assertions.assertEquals(code.length(), codeLength);
        int firstDash = code.indexOf("-");
        int lastDash = code.lastIndexOf("-");
        Assertions.assertEquals(code.substring(0, firstDash), strategy.getPrefix());
        Assertions.assertEquals(code.substring(lastDash + 1), orderIdString);
    }
}