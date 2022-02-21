package org.jeecg.modules.demo.zmexpress.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@ApiModel(value="zm_good_case对象", description="货柜详情")
@Data
@TableName("zm_good_case")
public class ZmGoodCase implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**fbaid*/
    @ApiModelProperty(value = "fbaid")
    private java.lang.String fbaid;
	/**货箱编号*/
	@Excel(name = "货箱编号*", width = 15)
    @ApiModelProperty(value = "货箱编号")
    private java.lang.String caseid;
	/**货箱重量*/
	@Excel(name = "货箱重量(KG)*", width = 15)
    @ApiModelProperty(value = "货箱重量")
    private java.lang.Double weight;
	/**实际重量*/
	@Excel(name = "实际重量", width = 15)
    @ApiModelProperty(value = "实际重量")
    private java.lang.String weightActually;
	/**实际长度*/
	@Excel(name = "实际长度", width = 15)
    @ApiModelProperty(value = "实际长度")
    private java.lang.String lengthActually;
	/**实际重量*/
	@Excel(name = "实际重量", width = 15)
    @ApiModelProperty(value = "实际重量")
    private java.lang.String heightActually;
	/**货箱长度*/
	@Excel(name = "货箱长度(CM)*", width = 15)
    @ApiModelProperty(value = "货箱长度")
    private java.lang.Double length;
	/**货箱高度*/
	@Excel(name = "货箱高度(CM)*", width = 15)
    @ApiModelProperty(value = "货箱高度")
    private java.lang.Double height;
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
	/**货箱宽度*/
	@Excel(name = "货箱宽度(CM)*", width = 15)
    @ApiModelProperty(value = "货箱宽度")
    private java.lang.Double width;
	/**实际宽度*/
	@Excel(name = "实际宽度", width = 15)
    @ApiModelProperty(value = "实际宽度")
    private java.lang.String widthActually;
    @ApiModelProperty(value = "出货单号")
    private java.lang.String deliveryOrderId;

    /**收费重*/
    @Excel(name = "收费重", width = 15)
    @ApiModelProperty(value = "收费重")
    private BigDecimal weightCharge;

    /**材积重*/
    @Excel(name = "材积重", width = 15)
    @ApiModelProperty(value = "材积重")
    private java.lang.String weightVolume;

    /**运单号*/
    @Excel(name = "运单号", width = 15)
    @ApiModelProperty(value = "运单号")
    private java.lang.String waybillId;
    /**提单单号*/
    @Excel(name = "提单单号", width = 15)
    @ApiModelProperty(value = "运单号")
    private java.lang.String billId;
}
