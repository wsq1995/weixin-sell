package com.wsq.controller;

import com.wsq.dataObject.ProductCategory;
import com.wsq.dataObject.ProductInfo;
import com.wsq.exception.SellException;
import com.wsq.form.ProductForm;
import com.wsq.service.ProductCategoryService;
import com.wsq.service.ProductInfoService;
import com.wsq.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author wsq
 * @date 2019/6/18 21:14
 */
@RestController
@RequestMapping("/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        Page<ProductInfo> productInfoPage = productInfoService.findAll(new PageRequest(page - 1, size));
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size",size);
        return new ModelAndView("product/list", map);
    }

    @RequestMapping("/onSale")
    public ModelAndView onsale(@RequestParam("productId") String productId, Map<String, Object> map){
        try {
            productInfoService.onSale(productId);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/list");
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/offSale")
    public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map){
        try {
            productInfoService.offSale(productId);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/list");
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId, Map<String, Object> map){
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("product/index", map);
    }

    @RequestMapping("/save")
    // 此#form是动态的
    @CachePut(cacheNames = "product", key = "#form") // 要想使用CachePut此注解，方法 的返回值类型必须相同，要不不能使用
//    @CacheEvict(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult, Map<String, Object> map){
        try {
            ProductInfo productInfo = new ProductInfo();
            if (bindingResult.hasErrors()){
                map.put("msg", bindingResult.getFieldError().getDefaultMessage());
                map.put("url", "/product/index");
                return new ModelAndView("common/error",map);
            }
            if (!StringUtils.isEmpty(form.getProductId())){
                productInfoService.findOne(form.getProductId());
            }else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productInfoService.save(productInfo);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/product/list");
        return new ModelAndView("common/success", map);
    }


}
