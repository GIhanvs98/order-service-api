package com.gihanvs.quickcart.order_service_api.service.impl;

import com.gihanvs.quickcart.order_service_api.dto.request.CustomerOrderRequestDto;
import com.gihanvs.quickcart.order_service_api.dto.request.OrderDetailRequestDto;
import com.gihanvs.quickcart.order_service_api.dto.response.CustomerOrderResponsetDto;
import com.gihanvs.quickcart.order_service_api.dto.response.OrderDetailResponseDto;
import com.gihanvs.quickcart.order_service_api.dto.response.paginate.CustomerOrderPaginateDto;
import com.gihanvs.quickcart.order_service_api.entity.CustomerOrder;
import com.gihanvs.quickcart.order_service_api.entity.OrderDetail;
import com.gihanvs.quickcart.order_service_api.entity.OrderStatus;
import com.gihanvs.quickcart.order_service_api.repo.CustomerOrderRepo;
import com.gihanvs.quickcart.order_service_api.repo.OrderStatusRepo;
import com.gihanvs.quickcart.order_service_api.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final CustomerOrderRepo customerOrderRepo;
    private final OrderStatusRepo orderStatusRepo;
    @Override
    public void createOrder(CustomerOrderRequestDto requestDto) {
        OrderStatus orderStatus =
                orderStatusRepo.findByStaus("PENDING").orElseThrow(()-> new RuntimeException("Order status is not found."));
          CustomerOrder customerOrder = new CustomerOrder();
          customerOrder.setOrderId(UUID.randomUUID().toString());
          customerOrder.setOrderDate(requestDto.getOrderDate());
          customerOrder.setRemark("");
          customerOrder.setTotalAmount(requestDto.getTotalAmount());
          customerOrder.setUserId(requestDto.getUserId());
          customerOrder.setProducts(requestDto.getOrderDetails().stream().map(e -> createOrderDetail(e, customerOrder)).collect(Collectors.toSet()));
          customerOrder.setOrderStatus(orderStatus);

          customerOrderRepo.save(customerOrder);
    }

    @Override
    public CustomerOrderResponsetDto findOrderById(String orderId) {
      CustomerOrder customerOrder= customerOrderRepo.findById(orderId).orElseThrow(()->new RuntimeException(String.format("Order not Found with %s",orderId)));
        return toCustomerOrderResponsetDto(customerOrder);
    }

    @Override
    public void deleteById(String orderId) {
        CustomerOrder customerOrder= customerOrderRepo.findById(orderId).orElseThrow(()->new RuntimeException(String.format("Order not Found with %s",orderId)));
        customerOrderRepo.delete(customerOrder);
    }

    @Override
    public CustomerOrderPaginateDto searchAll(String searchText, int page, int size) {
       return CustomerOrderPaginateDto.builder()
               .count(
                       customerOrderRepo.count()
               )
               .dataList(
                       customerOrderRepo.searchAll(searchText, PageRequest.of(page,size))
                               .stream().map(this::toCustomerOrderResponsetDto).collect(Collectors.toList())
               )
               .build();
    }

    private CustomerOrderResponsetDto toCustomerOrderResponsetDto(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            return null;
        }
        return CustomerOrderResponsetDto.builder()
                .orderId(customerOrder.getOrderId())
                .orderDate(customerOrder.getOrderDate())
                .userId(customerOrder.getUserId())
                .totalAmount(customerOrder.getTotalAmount())
                .remark(customerOrder.getRemark())
                .status(customerOrder.getOrderStatus().getStatus())
                .orderDetails(
                        customerOrder.getProducts().stream().map(this::toOrderDetailResponseDto).collect(Collectors.toList())
                )
                .build();
    }
    private OrderDetailResponseDto toOrderDetailResponseDto (OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }
        return OrderDetailResponseDto.builder()
                .productId(orderDetail.getProductId())
                .detailId(orderDetail.getDetailId())
                .discount(orderDetail.getDiscount())
                .qty(orderDetail.getQty())
                .unitPrice(orderDetail.getUnitPrice())
                .build();
    }
    private OrderDetail createOrderDetail(OrderDetailRequestDto requestDto, CustomerOrder order) {
        if (requestDto==null) {
            return null;
        }
        return OrderDetail.builder()
                .detailId(UUID.randomUUID().toString())
                .unitPrice(requestDto.getUnitPrice())
                .discount(requestDto.getDiscount())
                .customerOrder(order)
                .qty(requestDto.getQty())

                .build();
    }
}
