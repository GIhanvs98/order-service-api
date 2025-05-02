package com.gihanvs.quickcart.order_service_api.service;

import com.gihanvs.quickcart.order_service_api.dto.request.CustomerOrderRequestDto;
import com.gihanvs.quickcart.order_service_api.dto.response.CustomerOrderResponsetDto;

public interface CustomerOrderService {
    public void createOrder(CustomerOrderRequestDto requestDto);
    public CustomerOrderResponsetDto findOrderById(String orderId);
}
