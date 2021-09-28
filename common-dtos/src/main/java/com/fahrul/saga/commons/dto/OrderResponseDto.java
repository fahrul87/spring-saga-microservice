package com.fahrul.saga.commons.dto;

import com.fahrul.saga.commons.event.OrderStatus;

public class OrderResponseDto {

	private Integer userId;
	private Integer productId;
	private Integer amount;
	private Integer orderId;
	private OrderStatus orderStatus;

}
