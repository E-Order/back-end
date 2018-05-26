package com.eorder.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 商品类目product_catagory
 */
@Entity
@Data
@DynamicUpdate
public class ProductCategory {
     /* 类目id.主键自增*/
     @Id
     @GeneratedValue
    private Integer categoryId;
    /** 类目名字. */
    private String categoryName;
    /** 类目编号. */
    private Integer categoryType;

    public ProductCategory(){
    }
    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
