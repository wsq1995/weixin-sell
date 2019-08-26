package com.wsq.service;

import com.wsq.dataObject.ProductCategory;

import java.util.List;

/**
 * @author wsq
 *         Created by Tp on 2019/5/14.
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);


}
