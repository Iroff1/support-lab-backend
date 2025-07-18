package com.iroff.supportlab.adapter.payment.out.persistence.redis;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("requested_payments")
public class RequestedPaymentRedisEntity implements Serializable {

	@Id
	private String orderId;

	private String orderName;
	private Integer amount;
	private LocalDateTime createdAt;
	private Long userId;

	@TimeToLive
	private Long expiration;
}