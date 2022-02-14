package org.jeecg.modules.demo.zmexpress.vo;

import lombok.Data;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmCasePack.java
 * @Description TODO
 * @createTime 2022年01月06日 00:45:00
 */
@Data
public class ZmCasePack {
    /** 货箱编号*/
    private String caseId;
    /** 商品sku*/
    private String sku;
    /** PO Number*/
    private String po;
    /** 中文品名*/
    private String cnName;
    /** 英文品名*/
    private String enName;
    /** 申报单价*/
    private String declaredPrice;
    /** 数量*/
    private int declaredNumber;
    /** 材质*/
    private String material;
    /** 用途*/
    private String application;
    /** 品牌*/
    private String brand;
    /** 品牌类型*/
    private String type;
    /** 型号*/
    private String model;
    /** 图片链接*/
    private String picture;
    /** 海关编码*/
    private String hscode;
    /** 产品带电*/
    private String electric;
    /** 产品带磁*/
    private String magnetic;
}
