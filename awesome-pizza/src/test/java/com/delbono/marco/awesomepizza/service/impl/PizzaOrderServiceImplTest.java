package com.delbono.marco.awesomepizza.service.impl;

import com.delbono.marco.awesomepizza.AwesomePizzaApplication;
import com.delbono.marco.awesomepizza.dto.PizzaOrderDto;
import com.delbono.marco.awesomepizza.dto.PizzaOrderStatusDto;
import com.delbono.marco.awesomepizza.dto.PizzaOrderedDto;
import com.delbono.marco.awesomepizza.entity.OrderOwner;
import com.delbono.marco.awesomepizza.entity.OrderedPizza;
import com.delbono.marco.awesomepizza.entity.PizzaOrder;
import com.delbono.marco.awesomepizza.enums.OrderStatus;
import com.delbono.marco.awesomepizza.enums.PizzaSize;
import com.delbono.marco.awesomepizza.repository.OrderOwnerRepository;
import com.delbono.marco.awesomepizza.repository.OrderedPizzaRepository;
import com.delbono.marco.awesomepizza.repository.PizzaOrderRepository;
import com.delbono.marco.awesomepizza.service.PizzaOrderService;
import com.delbono.marco.awesomepizza.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AwesomePizzaApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PizzaOrderServiceImplTest {
    @Autowired
    private PizzaOrderService pizzaOrderService;
    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;
    @Autowired
    private OrderOwnerRepository orderOwnerRepository;
    @Autowired
    private OrderedPizzaRepository orderedPizzaRepository;
    @Autowired
    private QueueService queueService;

    @Test
    @Order(1)
    void placeNewPizzaOrder() {
        PizzaOrderStatusDto orderStatusDto = pizzaOrderService.placeNewPizzaOrder(getTestPizzaOrder());
        Assertions.assertEquals(OrderStatus.ACCEPTED, orderStatusDto.getStatus());

        List<OrderOwner> orderOwnerList = orderOwnerRepository.findAll();
        Assertions.assertEquals(1, orderOwnerList.size());
        Assertions.assertEquals("name", orderOwnerList.get(0).getName());
        Assertions.assertEquals("surname", orderOwnerList.get(0).getSurname());
        Assertions.assertEquals("address", orderOwnerList.get(0).getAddress());
        Assertions.assertEquals("city", orderOwnerList.get(0).getCity());
        Assertions.assertEquals("zipCode", orderOwnerList.get(0).getZipCode());
        Assertions.assertEquals("county", orderOwnerList.get(0).getCounty());

        List<PizzaOrder> pizzaOrderList = pizzaOrderRepository.findAll();
        Assertions.assertEquals(1, pizzaOrderList.size());
        Assertions.assertEquals(OrderStatus.ACCEPTED, pizzaOrderList.get(0).getStatus());

        List<OrderedPizza> orderedPizzaList = orderedPizzaRepository.findAll();
        Assertions.assertEquals(2, orderedPizzaList.size());
        Assertions.assertEquals(1L, orderedPizzaList.get(0).getPizza().getId());
        Assertions.assertEquals(2L, orderedPizzaList.get(1).getPizza().getId());
    }

    @Test
    @Order(2)
    void monitoring() {
        queueService.eraseQueue();
        PizzaOrderStatusDto order = pizzaOrderService.placeNewPizzaOrder(getTestPizzaOrder());
        Optional<PizzaOrderStatusDto> orderStatusDto = pizzaOrderService.monitoring(order.getCode());
        Assertions.assertEquals(OrderStatus.ACCEPTED, orderStatusDto.get().getStatus());

        pizzaOrderService.getNextPizzaToPrepare();
        orderStatusDto = pizzaOrderService.monitoring(order.getCode());
        Assertions.assertEquals(OrderStatus.WORK_IN_PROGRESS, orderStatusDto.get().getStatus());

        pizzaOrderService.getNextPizzaToPrepare();
        orderStatusDto = pizzaOrderService.monitoring(order.getCode());
        Assertions.assertEquals(OrderStatus.READY_TO_DELIVER, orderStatusDto.get().getStatus());
    }

    private static PizzaOrderDto getTestPizzaOrder() {
        PizzaOrderedDto pizzaOrderedDto1 = PizzaOrderedDto.builder()
                .id(1L)
                .size(PizzaSize.NORMAL)
                .build();
        PizzaOrderedDto pizzaOrderedDto2 = PizzaOrderedDto.builder()
                .id(2L)
                .size(PizzaSize.BABY)
                .build();
        List<PizzaOrderedDto> pizzaOrderedDtoList = Arrays.asList(pizzaOrderedDto1, pizzaOrderedDto2);

        return PizzaOrderDto.builder()
                .name("name")
                .surname("surname")
                .address("address")
                .city("city")
                .zipCode("zipCode")
                .county("county")
                .orderedPizzaIdSizeList(pizzaOrderedDtoList)
                .build();
    }
}