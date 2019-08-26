package com.wsq.repository;

import com.wsq.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wsq
 *         Created by Tp on 2019/5/18.
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**
     * 按照买家的openid查询
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
