package com.fahrul.saga.commons.event;

import java.util.Date;
import java.util.UUID;

import com.fahrul.saga.commons.dto.OrderRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEvent implements Event {

	private UUID eventId = UUID.randomUUID();
	private Date eventDate = new Date();
	private OrderRequestDto orderRequestDto;
	private OrderStatus orderStatus;

	@Override
	public UUID getEventId() {
		// TODO Auto-generated method stub
		return eventId;
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return eventDate;
	}

	public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {

		this.orderRequestDto = orderRequestDto;
		this.orderStatus = orderStatus;
	}

}
