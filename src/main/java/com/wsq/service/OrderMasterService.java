package com.wsq.service;

import com.wsq.dto.OrderMasterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author wsq
 *         Created by Tp on 2019/5/18.
 */

public interface OrderMasterService {

    /**
     * 创建订单
     */
    OrderMasterDto create(OrderMasterDto orderMasterDto);

    /**
     * 查询单个订单
     */
    OrderMasterDto findOne(String orderId);

    /**
     * 查询订单列表
     */
    Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     */
    OrderMasterDto cancel(OrderMasterDto orderMasterDto);

    /**
     * 完结订单
     */
    OrderMasterDto finished(OrderMasterDto orderMasterDto);

    /**
     * 支付订单
     */
    OrderMasterDto paid(OrderMasterDto orderMasterDto);

    /**
     * 查询订单列表
     */
    Page<OrderMasterDto> findList(Pageable pageable);

}
