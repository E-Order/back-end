package com.eorder.controller;


import com.eorder.VO.OrderDetailVO;
import com.eorder.VO.ResultVO;
import com.eorder.converter.OrderForm2OrderDTOConverter;
import com.eorder.dataobject.OrderDetail;
import com.eorder.dataobject.ProductInfo;
import com.eorder.dto.OrderDTO;
import com.eorder.dto.OrderDetailDTO;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.OrderForm;
import com.eorder.service.BuyerService;
import com.eorder.service.OrderService;
import com.eorder.service.ProductService;
import com.eorder.utils.ResultVOUtil;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 买家商品
 */

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BuyerService buyerService;


    //创建订单
    @PostMapping("/create")

    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确， orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }


        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);

    }
    //查询订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    //查询订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                        @RequestParam("orderId") String orderId) {

         OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
         //返回前段的json数据
         OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
         List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
         BeanUtils.copyProperties(orderDTO,orderDetailDTO);
         for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
             OrderDetailVO orderDetailVO = new OrderDetailVO();

             ProductInfo productInfo = productService.findOne(orderDetail.getProductId());

             BeanUtils.copyProperties(productInfo, orderDetailVO);
             orderDetailVO.setOrderId(orderDetail.getOrderId());
             orderDetailVO.setDetailId(orderDetail.getDetailId());
             orderDetailVO.setProductQuantity(orderDetail.getProductQuantity());

             orderDetailVOList.add(orderDetailVO);
         }
         orderDetailDTO.setOrderDetailVOList(orderDetailVOList);
         return ResultVOUtil.success(orderDetailDTO);
    }

    //取消订单

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
