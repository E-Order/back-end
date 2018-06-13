package com.project.eorder.dataobject;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目表
 * 2018-5-15 18：14
 */
@Entity
@DynamicUpdate
public class ProductCategory {
    /**类目ID*/
    @Id
    @GeneratedValue
    private Integer categoryId;

    /**类目名字*/
    private String categoryName;

    /**类目编号*/
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getCategoryType() { return categoryType; }
    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }
    public Date getCreateTime() {return createTime;}
    public void setCreateTime(Date createTime) {this.createTime = createTime;}
    public Date getUpdateTime() {return updateTime;}
    public void setUpdateTime(Date updateTime) {this.updateTime = updateTime;}
    public  ProductCategory(){}
    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName=categoryName;
        this.categoryType=categoryType;
    }
    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
