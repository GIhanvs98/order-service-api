package com.gihanvs.quickcart.order_service_api.service;

import com.gihanvs.quickcart.order_service_api.dto.request.CustomerOrderRequestDto;
import com.gihanvs.quickcart.order_service_api.dto.response.CustomerOrderResponsetDto;
import com.gihanvs.quickcart.order_service_api.dto.response.paginate.CustomerOrderPaginateDto;

public interface CustomerOrderService {
    public void createOrder(CustomerOrderRequestDto requestDto);
    public void updateOrder(String orderId , CustomerOrderRequestDto requestDto);
    public void manageRemark(String orderId , String remark);
    public void manageStatus(String orderId , String status);
    public CustomerOrderResponsetDto findOrderById(String orderId);
    public void deleteById(String orderId);
    public CustomerOrderPaginateDto searchAll(String searchText, int page, int size);
}
