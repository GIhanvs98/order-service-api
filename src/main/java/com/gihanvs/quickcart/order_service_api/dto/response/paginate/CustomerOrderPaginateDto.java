package com.gihanvs.quickcart.order_service_api.dto.response.paginate;

import com.gihanvs.quickcart.order_service_api.dto.response.CustomerOrderResponsetDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderPaginateDto {
    private long count;
    private List<CustomerOrderResponsetDto> dataList;
}