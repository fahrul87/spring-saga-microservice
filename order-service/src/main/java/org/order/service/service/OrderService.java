package org.order.service.service;

import java.util.List;

import org.order.service.entity.PurchaseOrder;
import org.order.service.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fahrul.saga.commons.dto.OrderRequestDto;
import com.fahrul.saga.commons.event.OrderStatus;

@Service
public class OrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private OrderStatusPublisher orderStatusPublisher;

	@Transactional
	public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
		PurchaseOrder order = purchaseOrderRepository.save(convertDtoToEntity(orderRequestDto));
		orderRequestDto.setOrderId(order.getId());
		orderStatusPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
		return order;
	}

	public List<PurchaseOrder> getAllOrders() {
		return purchaseOrderRepository.findAll();
	}

	private PurchaseOrder convertDtoToEntity(OrderRequestDto dto) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setProductId(dto.getProductId());
		purchaseOrder.setUserId(dto.getUserId());
		purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
		purchaseOrder.setPrice(dto.getAmount());
		return purchaseOrder;
	}

}
