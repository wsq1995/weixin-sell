package com.wsq.service;

import com.wsq.dataObject.SellerInfo;

public interface SellerService {

    /**
     * 通过openid查询
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenId(String openid);
}
