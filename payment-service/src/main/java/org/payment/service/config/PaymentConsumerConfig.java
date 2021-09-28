package org.payment.service.config;

import java.util.function.Function;

import org.payment.service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fahrul.saga.commons.event.OrderEvent;
import com.fahrul.saga.commons.event.OrderStatus;
import com.fahrul.saga.commons.event.PaymentEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class PaymentConsumerConfig {

	@Autowired
	private PaymentService paymentService;

	@Bean
	public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcosser() {
		return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
	}

	private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
		if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
			return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
		} else {
			return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
		}
	}
}
