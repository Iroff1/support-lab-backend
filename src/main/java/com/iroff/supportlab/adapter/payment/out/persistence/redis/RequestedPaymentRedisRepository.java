package com.iroff.supportlab.adapter.payment.out.persistence.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestedPaymentRedisRepository extends CrudRepository<RequestedPaymentRedisEntity, String> {
    Optional<RequestedPaymentRedisEntity> findByOrderId(String orderId);
    void deleteByOrderId(String orderId);
}