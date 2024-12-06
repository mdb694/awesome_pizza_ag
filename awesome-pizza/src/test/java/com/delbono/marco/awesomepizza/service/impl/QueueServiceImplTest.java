package com.delbono.marco.awesomepizza.service.impl;

import com.delbono.marco.awesomepizza.AwesomePizzaApplication;
import com.delbono.marco.awesomepizza.entity.OrderedPizza;
import com.delbono.marco.awesomepizza.enums.PizzaSize;
import com.delbono.marco.awesomepizza.service.QueueService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AwesomePizzaApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QueueServiceImplTest {
    @Autowired
    private QueueService queueService;

    @Test
    @Order(1)
    void addToQueue() {
        queueService.eraseQueue();
        OrderedPizza orderedPizza = OrderedPizza.builder()
                .size(PizzaSize.NORMAL)
                .build();
        queueService.addToQueue(orderedPizza);
        Assertions.assertEquals(1, queueService.getQueueSize());
    }

    @Test
    @Order(2)
    void getFromQueue() {
        OrderedPizza orderedPizza = queueService.getFromQueue();
        Assertions.assertEquals(PizzaSize.NORMAL, orderedPizza.getSize());
        Assertions.assertNotNull(orderedPizza);

        orderedPizza = queueService.getFromQueue();
        Assertions.assertNull(orderedPizza);
    }

    @Test
    @Order(3)
    void addAllToQueue() {
        OrderedPizza orderedPizza1 = OrderedPizza.builder().build();
        OrderedPizza orderedPizza2 = OrderedPizza.builder().build();
        List<OrderedPizza> orderedPizzaList = Arrays.asList(orderedPizza1, orderedPizza2);
        queueService.addAllToQueue(orderedPizzaList);
        Assertions.assertEquals(2, queueService.getQueueSize());
    }
}