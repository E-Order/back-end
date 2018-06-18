package com.eorder.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息product_info
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {
    //@ID代表主键
    @Id
    private String productId;
    /** 名字. */
    private String productName;
    /** 单价. */
    private BigDecimal productPrice;
    /** 描述. */
    private String productDescription;
    /** 小图. */
    private String productIcon;
    /** 库存. */
    private Integer productStock;
    /**状态 0正常1下架. */
    private Integer productStatus;
    /** 类目编号. */
    private Integer categoryType;

    /** 商家Id. */
    private String sellerId;

    /*创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

}
