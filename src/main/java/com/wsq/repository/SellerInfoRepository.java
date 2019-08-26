package com.wsq.repository;

import com.wsq.dataObject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findSellerInfoByOpenid(String openid);

}
