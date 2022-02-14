package org.jeecg.modules.demo.zmexpress.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName Zm.java
 * @Description TODO
 * @createTime 2022年01月09日 11:41:00
 */
@Data
public class ZmExportCustoms {

    /**唛头入仓号*/
    @Excel(name = "唛头入仓号", width = 15)
    @ApiModelProperty(value = "唛头入仓号")
    private java.lang.String waybillId;
    /**件数CTNS*/
    @Excel(name = "件数CTNS", width = 15)
    @ApiModelProperty(value = "件数CTNS")
    private java.lang.Integer piece;

    /**体积CBM*/
    @Excel(name = "体积CBM", width = 15)
    @ApiModelProperty(value = "体积CBM")
    private java.lang.Double volumn;
    /**总毛重KGS*/
    @Excel(name = "总毛重KGS", width = 15)
    @ApiModelProperty(value = "总毛重KGS")
    private java.lang.Double grossWeight;
    /**净重KGS*/
    @Excel(name = "净重KGS", width = 15)
    @ApiModelProperty(value = "净重KGS")
    private java.lang.Double netWeight;
    /**总价(USD)*/
    @Excel(name = "总价(USD)", width = 15)
    @ApiModelProperty(value = "总价(USD)")
    private java.lang.Double totalPrice;
    /**中文名称*/
    @Excel(name = "产品中文品名*", width = 15)
    @ApiModelProperty(value = "中文名称")
    private java.lang.String cnName;
    /**英文名称*/
    @Excel(name = "产品英文品名*", width = 15)
    @ApiModelProperty(value = "英文名称")
    private java.lang.String enName;
    /**申报单价*/
    @Excel(name = "产品申报单价*", width = 15)
    @ApiModelProperty(value = "申报单价")
    private java.lang.String declaredPrice;
    /**产品海关编码*/
    @Excel(name = "产品海关编码*", width = 15)
    @ApiModelProperty(value = "产品海关编码")
    private java.lang.String hscode;
    /**产品材料*/
    @Excel(name = "产品材质*", width = 15)
    @ApiModelProperty(value = "产品材料")
    private java.lang.String material;
    /**产品用途*/
    @Excel(name = "产品用途*", width = 15)
    @ApiModelProperty(value = "产品用途")
    private java.lang.String application;
    /**品牌*/
    @Excel(name = "产品品牌*", width = 15)
    @ApiModelProperty(value = "品牌")
    private java.lang.String brand;
    /**品牌类型*/
    @Excel(name = "品牌类型*", width = 15)
    @ApiModelProperty(value = "品牌类型")
    private java.lang.String type;
    /**产品型号*/
    @Excel(name = "产品型号*", width = 15)
    @ApiModelProperty(value = "产品型号")
    private java.lang.String model;
    /**销售链接*/
    @Excel(name = "产品销售链接", width = 15)
    @ApiModelProperty(value = "销售链接")
    private java.lang.String link;
    /**产品售价*/
    @Excel(name = "产品销售价格", width = 15)
    @ApiModelProperty(value = "产品售价")
    private java.lang.String price;
    /**图片链接*/
    @Excel(name = "产品图片链接", width = 15)
    @ApiModelProperty(value = "图片链接")
    private java.lang.String picture;
    /**申报数量*/
    @Excel(name = "产品申报数量*", width = 15)
    @ApiModelProperty(value = "申报数量")
    private java.lang.Integer declaredNumber;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.Integer remark;

}
