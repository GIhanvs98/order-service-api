package com.gihanvs.quickcart.order_service_api.service;

import com.gihanvs.quickcart.order_service_api.dto.request.CustomerOrderRequestDto;

public interface CustomerOrderService {
    public void createOrder(CustomerOrderRequestDto requestDto);
}
