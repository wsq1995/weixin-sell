package com.wsq.service;

import com.wsq.dataObject.ProductInfo;
import com.wsq.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wsq
 *         Created by Tp on 2019/5/15.
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /*
    查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDto> cartDtos);

    // 减库存
    void decreaseStock(List<CartDto> cartDtos);

    // 上架
    ProductInfo onSale(String productId);

    // 下架
    ProductInfo offSale(String productId);
}
