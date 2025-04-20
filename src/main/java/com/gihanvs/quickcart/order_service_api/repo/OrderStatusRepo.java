package com.gihanvs.quickcart.order_service_api.repo;

import com.gihanvs.quickcart.order_service_api.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepo extends JpaRepository<OrderStatus, String> {
}
