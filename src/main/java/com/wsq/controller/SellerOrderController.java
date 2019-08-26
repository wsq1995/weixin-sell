package com.wsq.controller;

import com.wsq.dto.OrderMasterDto;
import com.wsq.enums.ResultEnum;
import com.wsq.exception.SellException;
import com.wsq.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**卖家端订单
 * @author wsq
 * @date 2019/6/5 18:26
 */
@Controller
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderMasterService masterService;

    /**
     * 订单列表
     * @param page 第几页，从1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "1") Integer size, Map<String, Object>map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderMasterDto> dtos = masterService.findList(request);
        map.put("dtos" , dtos);
        map.put("currentPage", page);
        map.put("size", size);
//        list.getContent()
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    public ModelAndView cancel(@RequestParam("orderId") String orderId, Map<String, Object>map){
        try {
            OrderMasterDto masterDto = masterService.findOne(orderId);
            masterService.cancel(masterDto);
        }catch (SellException e){
            log.error("买家端取消订单 查询不到订单");
            map.put("msg", ResultEnum.ORDER_NOT_EXIT.getMsg());
            map.put("url", "/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("order/success");
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView orderDetail(@RequestParam("orderId") String orderId, Map<String, Object>map){
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        try {
            masterService.findOne(orderId);
        }catch (SellException e){
            log.error("卖家查询订单详情发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/list");
            return new ModelAndView("commom/error", map);
        }
        map.put("orderDTO", orderMasterDto);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId, Map<String, Object>map){
        try {
            OrderMasterDto masterDto = masterService.findOne(orderId);
            masterService.finished(masterDto);
        }catch (SellException e){
            log.error("卖家完结订单详情发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/list");
            return new ModelAndView("commom/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/list");
        return new ModelAndView("common/success", map);
    }

}
