package com.delbono.marco.awesomepizza.service.impl;

import com.delbono.marco.awesomepizza.dto.PizzaOrderDto;
import com.delbono.marco.awesomepizza.dto.PizzaOrderStatusDto;
import com.delbono.marco.awesomepizza.dto.PizzaOrderedDto;
import com.delbono.marco.awesomepizza.dto.PizzaToPrepareDto;
import com.delbono.marco.awesomepizza.entity.OrderOwner;
import com.delbono.marco.awesomepizza.entity.OrderedPizza;
import com.delbono.marco.awesomepizza.entity.Pizza;
import com.delbono.marco.awesomepizza.entity.PizzaOrder;
import com.delbono.marco.awesomepizza.enums.OrderStatus;
import com.delbono.marco.awesomepizza.enums.OrderedPizzaStatus;
import com.delbono.marco.awesomepizza.mapper.PizzaStatusMapper;
import com.delbono.marco.awesomepizza.mapper.PizzaToPrepareMapper;
import com.delbono.marco.awesomepizza.repository.OrderedPizzaRepository;
import com.delbono.marco.awesomepizza.repository.PizzaOrderRepository;
import com.delbono.marco.awesomepizza.repository.PizzaRepository;
import com.delbono.marco.awesomepizza.service.OrderCodeGeneratorStrategy;
import com.delbono.marco.awesomepizza.service.PizzaOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PizzaOrderServiceImpl implements PizzaOrderService {
    private final QueueServiceImpl queueService;
    private final PizzaOrderRepository pizzaOrderRepository;
    private final PizzaRepository pizzaRepository;
    private final OrderCodeGeneratorStrategy orderCodeGeneratorStrategy;
    private final PizzaStatusMapper pizzaStatusMapper;
    private final PizzaToPrepareMapper pizzaToPrepareMapper;
    private final OrderedPizzaRepository orderedPizzaRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PizzaOrderStatusDto placeNewPizzaOrder(PizzaOrderDto pizzaOrderDto) {
        PizzaOrder pizzaOrder = createPizzaOrder(createOrderOwner(pizzaOrderDto), createOrderedPizzaSet(pizzaOrderDto));
        pizzaOrder = pizzaOrderRepository.save(pizzaOrder);
        pizzaOrder.setCode(orderCodeGeneratorStrategy.generateOrderCode(pizzaOrder.getId()));
        for (OrderedPizza orderedPizza : pizzaOrder.getPizzaSet()) {
            orderedPizza.setOrder(pizzaOrder);
        }
        pizzaOrderRepository.save(pizzaOrder);
        queueService.addAllToQueue(pizzaOrder.getPizzaSet());
        return pizzaStatusMapper.orderToStatus(pizzaOrder);
    }

    @Override
    public Optional<PizzaOrderStatusDto> monitoring(String orderCode) {
        Optional<PizzaOrder> pizzaOrder = pizzaOrderRepository.findByCode(orderCode);
        return pizzaOrder.map(pizzaStatusMapper::orderToStatus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<PizzaToPrepareDto> getNextPizzaToPrepare() {
        OrderedPizza orderedPizza = queueService.getFromQueue();
        if(orderedPizza != null) {
            Optional<OrderedPizza> fetchedOrderedPizza = orderedPizzaRepository.findById(orderedPizza.getId());
            if (fetchedOrderedPizza.isPresent()) {
                OrderedPizza orderedPizzaFromDB = fetchedOrderedPizza.get();
                orderedPizzaFromDB.setStatus(OrderedPizzaStatus.DONE);
                Set<OrderedPizza> pizzaSet = orderedPizzaFromDB.getOrder().getPizzaSet();
                PizzaOrder order = orderedPizzaFromDB.getOrder();
                order.setStatus(computeOrderStatus(pizzaSet, order.getCode()));
                orderedPizzaRepository.save(orderedPizzaFromDB);
                return Optional.of(pizzaToPrepareMapper.orderedPizzaToPizzaToPrepare(orderedPizzaFromDB));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private PizzaOrder createPizzaOrder(OrderOwner orderOwner, Set<OrderedPizza> pizzaSet) {
        return PizzaOrder.builder()
                .orderOwner(orderOwner)
                .pizzaSet(pizzaSet)
                .build();
    }

    private Set<OrderedPizza> createOrderedPizzaSet(PizzaOrderDto pizzaOrderDto) {
        Set<OrderedPizza> orderedPizzaSet = new LinkedHashSet<>();
        Set<Long> pizzaIdSet = pizzaOrderDto.getOrderedPizzaIdSizeList().stream()
                .map(PizzaOrderedDto::getId).collect(Collectors.toSet());
        List<Pizza> pizzaList = pizzaRepository.findByIdIn(pizzaIdSet);
        Map<Long, Pizza> pizzaIdPizzaMap = pizzaList.stream().collect(Collectors.toMap(Pizza::getId, Function.identity()));
        for (PizzaOrderedDto pizzaOrderedDto : pizzaOrderDto.getOrderedPizzaIdSizeList()) {
            orderedPizzaSet.add(OrderedPizza.builder()
                    .pizza(pizzaIdPizzaMap.get(pizzaOrderedDto.getId()))
                    .size(pizzaOrderedDto.getSize())
                    .build());
        }
        return orderedPizzaSet;
    }

    private OrderOwner createOrderOwner(PizzaOrderDto pizzaOrderDto) {
        return OrderOwner.builder()
                .name(pizzaOrderDto.getName())
                .surname(pizzaOrderDto.getSurname())
                .address(pizzaOrderDto.getAddress())
                .city(pizzaOrderDto.getCity())
                .zipCode(pizzaOrderDto.getZipCode())
                .county(pizzaOrderDto.getCounty())
                .build();
    }

    private OrderStatus computeOrderStatus(Set<OrderedPizza> orderedPizzaSet, String orderCode) {
        OrderStatus orderStatus = OrderStatus.READY_TO_DELIVER;
        for (OrderedPizza orderedPizza : orderedPizzaSet) {
            if (!OrderedPizzaStatus.DONE.equals(orderedPizza.getStatus())) {
                orderStatus = OrderStatus.WORK_IN_PROGRESS;
                break;
            }
        }
        log.info("All pizzas ready for order {}! Set order as ready to deliver!", orderCode);
        return orderStatus;
    }
}
