package com.wsq.service.impl;

import com.wsq.dataObject.SellerInfo;
import com.wsq.repository.SellerInfoRepository;
import com.wsq.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wsq
 * @date 2019/6/30 22:55
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository infoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenId(String openid) {
        return infoRepository.findSellerInfoByOpenid(openid);
    }
}
