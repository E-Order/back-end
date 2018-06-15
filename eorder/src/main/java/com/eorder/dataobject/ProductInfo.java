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

<<<<<<< HEAD
    /*创建时间. */
=======
>>>>>>> b56afd1175b8bc74c43da2427a0091841cd3a43f
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

}
