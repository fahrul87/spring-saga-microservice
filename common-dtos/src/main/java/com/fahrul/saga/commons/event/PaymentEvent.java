package com.fahrul.saga.commons.event;

import java.util.Date;
import java.util.UUID;

import com.fahrul.saga.commons.dto.PaymentRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentEvent implements Event {

	private UUID eventId = UUID.randomUUID();
	private Date eventDate = new Date();
	private PaymentRequestDto paymentRequestDto;
	private PaymentStatus paymentStatus;

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

	public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
		super();
		this.paymentRequestDto = paymentRequestDto;
		this.paymentStatus = paymentStatus;
	}

}
