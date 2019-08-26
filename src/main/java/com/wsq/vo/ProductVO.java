package com.wsq.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品(包含类目)
 * @author wsq
 *         Created by Tp on 2019/5/17.
 */
@Data
public class ProductVO implements Serializable {


    private static final long serialVersionUID = -2404783773863680025L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOS;
}
