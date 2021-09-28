package org.order.service.config;

import java.util.function.Consumer;

import org.order.service.entity.PurchaseOrder;
import org.order.service.repository.PurchaseOrderRepository;
import org.order.service.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.fahrul.saga.commons.dto.OrderRequestDto;
import com.fahrul.saga.commons.event.OrderStatus;
import com.fahrul.saga.commons.event.PaymentStatus;

@Configuration
public class OrderStatusUpdateHandler {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private OrderStatusPublisher publisher;

	@Transactional
	public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
		purchaseOrderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
	}

	public void updateOrder(PurchaseOrder purchaseOrder) {
		boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
		OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETE : OrderStatus.ORDER_CANCELLED;
		purchaseOrder.setOrderStatus(orderStatus);
		if (!isPaymentComplete) {
			publisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
		}
	}

	public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setOrderId(purchaseOrder.getId());
		orderRequestDto.setUserId(purchaseOrder.getUserId());
		orderRequestDto.setAmount(purchaseOrder.getPrice());
		orderRequestDto.setProductId(purchaseOrder.getProductId());
		return orderRequestDto;
	}

}
