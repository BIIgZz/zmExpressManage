package org.jeecg.modules.demo.zmexpress.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 出货单
 * @Author: jeecg-boot
 * @Date:   2022-01-01
 * @Version: V1.0
 */
@Data
@TableName("zm_delivery_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_delivery_order对象", description="出货单")
public class ZmDeliveryOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**出货单号*/
	@Excel(name = "出货单号", width = 15)
    @ApiModelProperty(value = "出货单号")
    private java.lang.String deliveryOrderNo;
	/**发往站点*/
	@Excel(name = "发往站点", width = 15, dictTable = "zm_airport", dicText = "name", dicCode = "name")
	@Dict(dictTable = "zm_airport", dicText = "name", dicCode = "name")
    @ApiModelProperty(value = "发往站点")
    private java.lang.String destination;
	/**主品名*/
	@Excel(name = "主品名", width = 15)
    @ApiModelProperty(value = "主品名")
    private java.lang.String mainName;
	/**供应商*/
	@Excel(name = "供应商", width = 15, dictTable = "zm_supplier", dicText = "company", dicCode = "company")
	@Dict(dictTable = "zm_supplier", dicText = "company", dicCode = "company")
    @ApiModelProperty(value = "供应商")
    private java.lang.String supplier;
	/**出货时间*/
	@Excel(name = "出货时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出货时间")
    private java.util.Date pickingTime;
	/**发出站点*/
	@Excel(name = "发出站点", width = 15, dictTable = "zm_airport", dicText = "name", dicCode = "name")
	@Dict(dictTable = "zm_airport", dicText = "name", dicCode = "name")
    @ApiModelProperty(value = "发出站点")
    private java.lang.String departure;
	/**参考号*/
	@Excel(name = "参考号", width = 15)
    @ApiModelProperty(value = "参考号")
    private java.lang.String referenceNo;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**属性*/
	@Excel(name = "属性", width = 15, dicCode = "delivery_order_item")
	@Dict(dicCode = "delivery_order_item")
    @ApiModelProperty(value = "属性")
    private java.lang.String attributes;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**箱数*/
	@Excel(name = "箱数", width = 15)
    @ApiModelProperty(value = "箱数")
    private java.lang.String caseNum;
	/**重量*/
	@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private java.lang.String weight;
	/**价值*/
	@Excel(name = "价值", width = 15)
    @ApiModelProperty(value = "价值")
    private java.lang.String value;
	/**体积/泡比*/
	@Excel(name = "体积/泡比", width = 15)
    @ApiModelProperty(value = "体积/泡比")
    private java.lang.String volumeBubbleRatio;
	/**体积比*/
	@Excel(name = "体积比", width = 15)
    @ApiModelProperty(value = "体积比")
    private java.lang.String volumeRatio;

    /**提单表id*/
    @ApiModelProperty(value = "提单表id")
    private java.lang.String ladingBillId;
}
