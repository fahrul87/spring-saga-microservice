package org.order.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fahrul.saga.commons.dto.OrderRequestDto;
import com.fahrul.saga.commons.event.OrderEvent;
import com.fahrul.saga.commons.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

	@Autowired
	private Sinks.Many<OrderEvent> orderSinks;

	public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
		OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
		orderSinks.tryEmitNext(orderEvent);
	}

}
