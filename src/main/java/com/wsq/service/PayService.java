package com.wsq.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.wsq.dto.OrderMasterDto;

public interface PayService {

    PayResponse create(OrderMasterDto orderMasterDto);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderMasterDto masterDto);
}
