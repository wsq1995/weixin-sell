package com.wsq.controller;

import com.wsq.dataObject.ProductCategory;
import com.wsq.dataObject.ProductInfo;
import com.wsq.service.ProductCategoryService;
import com.wsq.service.ProductInfoService;
import com.wsq.util.ResultVOUtil;
import com.wsq.vo.ProductInfoVO;
import com.wsq.vo.ProductVO;
import com.wsq.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**买家商品
 * @author wsq
 *         Created by Tp on 2019/5/15.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {


    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "123")
    public ResultVO list(){
        // 查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        // 查询类目
        // List<Integer> categoryTypeList = new ArrayList<>();
        // 传统方法
        // for (ProductInfo productInfo : productInfoList){
        //    categoryTypeList.add(productInfo.getCategoryType());
        // }
        // 精简方法 Lambda表达式
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        // 数据拼接
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOS(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

}
