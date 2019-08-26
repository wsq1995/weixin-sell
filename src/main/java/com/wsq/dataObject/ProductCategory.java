package com.wsq.dataObject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**类目
 * @author wsq
 *         Created by Tp on 2019/5/10.
 */
@Entity
@Table(name = "product_category")
@Data
@DynamicUpdate  //自动更新时间
public class ProductCategory {
    /*
    类目id
     */
    @Id
    @GeneratedValue
    private Integer categoryId;
    /*
    类目名字
     */
    private String categoryName;
    /*
    类目编号
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(Integer categoryId, String categoryName, Integer categoryType) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
