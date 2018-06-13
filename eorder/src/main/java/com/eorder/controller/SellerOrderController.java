package com.eorder.controller;

import com.eorder.VO.OrderDetailVO;
import com.eorder.VO.ResultVO;
import com.eorder.converter.OrderDTO2OrderDetailConverter;
import com.eorder.dataobject.OrderDetail;
import com.eorder.dataobject.ProductInfo;
import com.eorder.dto.OrderDTO;
import com.eorder.dto.OrderDetailDTO;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.service.OrderService;
import com.eorder.service.ProductService;
import com.eorder.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 卖家端订单
 */

@RestController
@RequestMapping("seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    /**
     * 订单列表
     * @param page 第几页,从第0页开始
     * @param size 一冶有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        try{
            PageRequest request = new PageRequest(page, size);
            Page<OrderDTO> orderDTOPage = orderService.findList(request);

            return ResultVOUtil.success(orderDTOPage.getContent());
        } catch (SellException e) {
            log.error("【卖家端查看订单】 查询失败 e={}");
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }

    }

    /**
     * 查看订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO<OrderDetailDTO> detail(@RequestParam("orderId") String orderId) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            OrderDetailDTO orderDetailDTO = OrderDTO2OrderDetailConverter.convert(orderDTO);
            return ResultVOUtil.success(orderDetailDTO);
        } catch (SellException e) {
            log.error("【卖家端查看订单详情】 查询失败");
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }

    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("orderId") String orderId) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端取消订单】 取消订单失败");
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
        return ResultVOUtil.success();
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    public ResultVO finish(@RequestParam("orderId") String orderId) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端完结订单】 完结订单失败");
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
        return ResultVOUtil.success();
    }

}
