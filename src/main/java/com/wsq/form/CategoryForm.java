package com.wsq.form;

import lombok.Data;

/**
 * @author wsq
 * @date 2019/6/29 23:26
 */
@Data
public class CategoryForm {

    private Integer categoryId;
    /*
    类目名字
     */
    private String categoryName;
    /*
    类目编号
     */
    private Integer categoryType;
}
