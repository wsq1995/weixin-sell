package com.wsq.controller;

import com.wsq.dataObject.ProductCategory;
import com.wsq.exception.SellException;
import com.wsq.form.CategoryForm;
import com.wsq.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author wsq
 * @date 2019/6/29 23:03
 */
@RestController
@RequestMapping("/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 列表
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(Map<String, Object>map){
        List<ProductCategory> categoryServiceAll = categoryService.findAll();
        map.put("categoryServiceAll",categoryServiceAll);
        return new ModelAndView("category/list", map);
    }

    /**
     * 修改
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categortId") Integer categoryId, Map<String, Object>map){
        if (categoryId != null){
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory", productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 保存/更新
     * @param form
     * @param map
     * @param bindingResult
     * @return
     */
    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm form, Map<String, Object>map, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/category/index");
            return new ModelAndView("common/error",map);
        }
        try {
            ProductCategory productCategory = new ProductCategory();
            if (form.getCategoryId() != null){
                productCategory = categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/category/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/category/list");
        return new ModelAndView("common/success", map);
    }
}
