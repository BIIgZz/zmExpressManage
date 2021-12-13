package org.jeecg.modules.demo.zmexpress.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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

/**
 * @Description: 运单表
 * @Author: jeecg-boot
 * @Date:   2021-12-13
 * @Version: V1.0
 */
@ApiModel(value="zm_waybill对象", description="运单表")
@Data
@TableName("zm_waybill")
public class ZmWaybill implements Serializable {
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
	/**运单号*/
	@Excel(name = "运单号", width = 15)
    @ApiModelProperty(value = "运单号")
    private java.lang.String waybillId;
	/**客户订单号*/
	@Excel(name = "客户订单号", width = 15)
    @ApiModelProperty(value = "客户订单号")
    private java.lang.String orderId;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15, dictTable = "zm_client_main", dicText = "username", dicCode = "username")
    @Dict(dictTable = "zm_client_main", dicText = "username", dicCode = "username")
    @ApiModelProperty(value = "客户名称")
    private java.lang.String name;
	/**公司名*/
	@Excel(name = "公司名", width = 15)
    @ApiModelProperty(value = "公司名")
    private java.lang.String company;
	/**柜号*/
	@Excel(name = "柜号", width = 15)
    @ApiModelProperty(value = "柜号")
    private java.lang.String caseId;
	/**FBA号*/
	@Excel(name = "FBA号", width = 15)
    @ApiModelProperty(value = "FBA号")
    private java.lang.String fbaId;
	/**工作号*/
	@Excel(name = "工作号", width = 15)
    @ApiModelProperty(value = "工作号")
    private java.lang.String jobId;
	/**服务*/
	@Excel(name = "服务", width = 15, dictTable = "zm_service", dicText = "name", dicCode = "name")
    @Dict(dictTable = "zm_service", dicText = "name", dicCode = "name")
    @ApiModelProperty(value = "服务")
    private java.lang.String service;
	/**派送方式*/
	@Excel(name = "派送方式", width = 15)
    @ApiModelProperty(value = "派送方式")
    private java.lang.String deliveryMethod;
	/**目的港*/
	@Excel(name = "目的港", width = 15)
    @ApiModelProperty(value = "目的港")
    private java.lang.String destination;
	/**仓库代码*/
	@Excel(name = "仓库代码", width = 15, dictTable = "zm_fba_warehouse_detail", dicText = "code", dicCode = "code")
    @Dict(dictTable = "zm_fba_warehouse_detail", dicText = "code", dicCode = "code")
    @ApiModelProperty(value = "仓库代码")
    private java.lang.String warehouseId;
	/**收件人*/
	@Excel(name = "收件人", width = 15)
    @ApiModelProperty(value = "收件人")
    private java.lang.String recipient;
	/**收件人地址*/
	@Excel(name = "收件人地址", width = 15)
    @ApiModelProperty(value = "收件人地址")
    private java.lang.String address;
	/**件数*/
	@Excel(name = "件数", width = 15)
    @ApiModelProperty(value = "件数")
    private java.lang.String number;
	/**中文名*/
	@Excel(name = "中文名", width = 15)
    @ApiModelProperty(value = "中文名")
    private java.lang.String cnname;
	/**英文名*/
	@Excel(name = "英文名", width = 15)
    @ApiModelProperty(value = "英文名")
    private java.lang.String enname;
	/**收费重量(KG)*/
	@Excel(name = "收费重量(KG)", width = 15)
    @ApiModelProperty(value = "收费重量(KG)")
    private java.lang.String chargedWeight;
	/**实际重量(KG)*/
	@Excel(name = "实际重量(KG)", width = 15)
    @ApiModelProperty(value = "实际重量(KG)")
    private java.lang.String actualWeight;
	/**材积重*/
	@Excel(name = "材积重", width = 15)
    @ApiModelProperty(value = "材积重")
    private java.lang.String volumeWeight;
	/**计泡系数*/
	@Excel(name = "计泡系数", width = 15)
    @ApiModelProperty(value = "计泡系数")
    private java.lang.String foamingFactor;
	/**体积(m³)*/
	@Excel(name = "体积(m³)", width = 15)
    @ApiModelProperty(value = "体积(m³)")
    private java.lang.String volume;
	/**申报价值*/
	@Excel(name = "申报价值", width = 15)
    @ApiModelProperty(value = "申报价值")
    private java.lang.String declaredValue;
	/**报关方式*/
	@Excel(name = "报关方式", width = 15, dicCode = "customs_eclaration")
    @Dict(dicCode = "customs_eclaration")
    @ApiModelProperty(value = "报关方式")
    private java.lang.String customsDeclarationMethod;
	/**交税方式*/
	@Excel(name = "交税方式", width = 15, dicCode = "tax_payment")
    @Dict(dicCode = "tax_payment")
    @ApiModelProperty(value = "交税方式")
    private java.lang.String taxPaymentMethod;
	/**VAT号*/
	@Excel(name = "VAT号", width = 15)
    @ApiModelProperty(value = "VAT号")
    private java.lang.String vat;
	/**承运商*/
	@Excel(name = "承运商", width = 15, dictTable = "zm_partner", dicText = "company", dicCode = "company")
    @Dict(dictTable = "zm_partner", dicText = "company", dicCode = "company")
    @ApiModelProperty(value = "承运商")
    private java.lang.String carrier;
	/**跟踪号*/
	@Excel(name = "跟踪号", width = 15)
    @ApiModelProperty(value = "跟踪号")
    private java.lang.String trackingNumber;
	/**问题件*/
	@Excel(name = "问题件", width = 15)
    @ApiModelProperty(value = "问题件")
    private java.lang.String problemPiece;
	/**内部备注*/
	@Excel(name = "内部备注", width = 15)
    @ApiModelProperty(value = "内部备注")
    private java.lang.String inRemark;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**退件原因*/
	@Excel(name = "退件原因", width = 15)
    @ApiModelProperty(value = "退件原因")
    private java.lang.String reasonReturn;
	/**应付*/
	@Excel(name = "应付", width = 15)
    @ApiModelProperty(value = "应付")
    private java.lang.String handle;
	/**已付*/
	@Excel(name = "已付", width = 15)
    @ApiModelProperty(value = "已付")
    private java.lang.String paid;
	/**未付*/
	@Excel(name = "未付", width = 15)
    @ApiModelProperty(value = "未付")
    private java.lang.String unpaid;
	/**计费方式*/
	@Excel(name = "计费方式", width = 15)
    @ApiModelProperty(value = "计费方式")
    private java.lang.String billingMethod;
	/**物品属性*/
	@Excel(name = "物品属性", width = 15, dicCode = "item_properties")
    @Dict(dicCode = "item_properties")
    @ApiModelProperty(value = "物品属性")
    private java.lang.String itemProperties;
	/**计费时间*/
	@Excel(name = "计费时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计费时间")
    private java.util.Date billableTime;
	/**出货时间*/
	@Excel(name = "出货时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出货时间")
    private java.util.Date deliveryTime;
	/**签收时间*/
	@Excel(name = "签收时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "签收时间")
    private java.util.Date submissionTime;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
}
