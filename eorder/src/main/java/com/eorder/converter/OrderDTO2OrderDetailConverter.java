package com.eorder.converter;

import com.eorder.VO.OrderDetailVO;
import com.eorder.dataobject.OrderDetail;
import com.eorder.dataobject.ProductInfo;
import com.eorder.dto.OrderDTO;
import com.eorder.dto.OrderDetailDTO;
import com.eorder.service.ProductService;
import com.eorder.service.impl.ProductServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDTO2OrderDetailConverter {

    @Autowired
    private ProductService productService;
    private static OrderDTO2OrderDetailConverter converter;

    @PostConstruct
    public void init() {
        converter = this;
        converter.productService = this.productService;
    }
    public static OrderDetailDTO convert(OrderDTO orderDTO) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
        BeanUtils.copyProperties(orderDTO,orderDetailDTO);
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            ProductInfo productInfo = converter.productService.findOne(orderDetail.getProductId());
            BeanUtils.copyProperties(productInfo, orderDetailVO);
            orderDetailVO.setOrderId(orderDetail.getOrderId());
            orderDetailVO.setDetailId(orderDetail.getDetailId());
            orderDetailVO.setProductQuantity(orderDetail.getProductQuantity());
            orderDetailVOList.add(orderDetailVO);
        }
        orderDetailDTO.setOrderDetailVOList(orderDetailVOList);
        return orderDetailDTO;
    }
}
