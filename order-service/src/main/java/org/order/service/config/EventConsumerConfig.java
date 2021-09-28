package org.order.service.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fahrul.saga.commons.event.PaymentEvent;

@Configuration
public class EventConsumerConfig {

	@Autowired
	private OrderStatusUpdateHandler handler;

	@Bean
	public Consumer<PaymentEvent> paymentEventConsumer() {
		return (payment) -> handler.updateOrder(payment.getPaymentRequestDto().getOrderId(), po -> {
			po.setPaymentStatus(payment.getPaymentStatus());
		});
	}

}
