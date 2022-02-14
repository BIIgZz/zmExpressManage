package org.jeecg.modules.demo.zmexpress.vo;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybills;
import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 运单全表
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@Data
@ApiModel(value="zm_waybillsPage对象", description="运单全表")
public class ZmWaybillsPage {

	/**主键*/
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
	/**柜号*/
	@Excel(name = "柜号", width = 15)
	@ApiModelProperty(value = "柜号")
    private java.lang.String caseId;
	/**参考号一*/
	@Excel(name = "参考号一", width = 15)
	@ApiModelProperty(value = "参考号一")
    private java.lang.String referenceNumberOne;
	/**参考号二*/
	@Excel(name = "参考号二", width = 15)
	@ApiModelProperty(value = "参考号二")
    private java.lang.String referenceNumberTwo;
	/**用户编号*/
	@Excel(name = "用户编号", width = 15)
	@ApiModelProperty(value = "用户编号")
    private java.lang.String userId;
	/**用户名*/
	@Excel(name = "用户名", width = 15, dictTable = "zm_client_main", dicText = "username", dicCode = "username")
    @Dict(dictTable = "zm_client_main", dicText = "username", dicCode = "username")
	@ApiModelProperty(value = "用户名")
    private java.lang.String username;
	/**服务*/
	@Excel(name = "服务", width = 15, dictTable = "zm_service", dicText = "name", dicCode = "name")
    @Dict(dictTable = "zm_service", dicText = "name", dicCode = "name")
	@ApiModelProperty(value = "服务")
    private java.lang.String service;
	/**供应商*/
	@Excel(name = "供应商", width = 15)
	@ApiModelProperty(value = "供应商")
    private java.lang.String supplier;
	/**供应商服务*/
	@Excel(name = "供应商服务", width = 15)
	@ApiModelProperty(value = "供应商服务")
    private java.lang.String supplierService;
	/**公布价*/
	@Excel(name = "公布价", width = 15)
	@ApiModelProperty(value = "公布价")
    private java.lang.String publishedPrice;
	/**店铺*/
	@Excel(name = "店铺", width = 15)
	@ApiModelProperty(value = "店铺")
    private java.lang.String shop;
	/**Amazon Reference ID*/
	@Excel(name = "Amazon Reference ID", width = 15)
	@ApiModelProperty(value = "Amazon Reference ID")
    private java.lang.String amazonReferenceId;
	/**收件人*/
	@Excel(name = "收件人", width = 15)
	@ApiModelProperty(value = "收件人")
    private java.lang.String recipient;
	/**公司名*/
	@Excel(name = "公司名", width = 15)
	@ApiModelProperty(value = "公司名")
    private java.lang.String company;
	/**仓库代码*/
	@Excel(name = "仓库代码", width = 15, dictTable = "zm_fba_warehouse_detail", dicText = "code", dicCode = "code")
    @Dict(dictTable = "zm_fba_warehouse_detail", dicText = "code", dicCode = "code")
	@ApiModelProperty(value = "仓库代码")
    private java.lang.String warehouseId;
	/**收件人地址一*/
	@Excel(name = "收件人地址一", width = 15)
	@ApiModelProperty(value = "收件人地址一")
    private java.lang.String recipientAddressOne;
	/**收件人地址二*/
	@Excel(name = "收件人地址二", width = 15)
	@ApiModelProperty(value = "收件人地址二")
    private java.lang.String recipientAddressTwo;
	/**收件人地址三*/
	@Excel(name = "收件人地址三", width = 15)
	@ApiModelProperty(value = "收件人地址三")
    private java.lang.String recipientAddressTri;
	/**收件人城市*/
	@Excel(name = "收件人城市", width = 15)
	@ApiModelProperty(value = "收件人城市")
    private java.lang.String recipientCity;
	/**收件人省/州*/
	@Excel(name = "收件人省/州", width = 15)
	@ApiModelProperty(value = "收件人省/州")
    private java.lang.String recipientState;
	/**收件人邮编*/
	@Excel(name = "收件人邮编", width = 15)
	@ApiModelProperty(value = "收件人邮编")
    private java.lang.String recipientZipOde;
	/**收件人国家*/
	@Excel(name = "收件人国家", width = 15)
	@ApiModelProperty(value = "收件人国家")
    private java.lang.String recipientCountry;
	/**收件人电话*/
	@Excel(name = "收件人电话", width = 15)
	@ApiModelProperty(value = "收件人电话")
    private java.lang.String recipientTel;
	/**收件人邮箱*/
	@Excel(name = "收件人邮箱", width = 15)
	@ApiModelProperty(value = "收件人邮箱")
    private java.lang.String recipientEmail;
	/**件数*/
	@Excel(name = "件数", width = 15)
	@ApiModelProperty(value = "件数")
    private java.lang.String pieces;
	/**主品名*/
	@Excel(name = "主品名", width = 15)
	@ApiModelProperty(value = "主品名")
    private java.lang.String mainProductName;
	/**收费重量(KG)*/
	@Excel(name = "收费重量(KG)", width = 15)
	@ApiModelProperty(value = "收费重量(KG)")
    private java.lang.String weightCharge;
	/**实际重量(KG)*/
	@Excel(name = "实际重量(KG)", width = 15)
	@ApiModelProperty(value = "实际重量(KG)")
    private java.lang.String weightActual;
	/**材积重(KG)*/
	@Excel(name = "材积重(KG)", width = 15)
	@ApiModelProperty(value = "材积重(KG)")
    private java.lang.String weightVolume;
	/**体积(M³)*/
	@Excel(name = "体积(M³)", width = 15)
	@ApiModelProperty(value = "体积(M³)")
    private java.lang.String volume;
	/**客户重量(KG)*/
	@Excel(name = "客户重量(KG)", width = 15)
	@ApiModelProperty(value = "客户重量(KG)")
    private java.lang.String weightCustomer;
	/**客户长度(CM)*/
	@Excel(name = "客户长度(CM)", width = 15)
	@ApiModelProperty(value = "客户长度(CM)")
    private java.lang.String lengthCustomer;
	/**客户宽度(CM)*/
	@Excel(name = "客户宽度(CM)", width = 15)
	@ApiModelProperty(value = "客户宽度(CM)")
    private java.lang.String widthCustomer;
	/**客户高度(CM)*/
	@Excel(name = "客户高度(CM)", width = 15)
	@ApiModelProperty(value = "客户高度(CM)")
    private java.lang.String heightCustomer;
	/**计泡系数*/
	@Excel(name = "计泡系数", width = 15)
	@ApiModelProperty(value = "计泡系数")
    private java.lang.String foamingFactor;
	/**申报价值*/
	@Excel(name = "申报价值", width = 15)
	@ApiModelProperty(value = "申报价值")
    private java.lang.String declaredValue;
	/**报关方式*/
	@Excel(name = "报关方式", width = 15, dicCode = "customs_eclaration")
    @Dict(dicCode = "customs_eclaration")
	@ApiModelProperty(value = "报关方式")
    private java.lang.String customsDeclaration;
	/**清关方式*/
	@Excel(name = "清关方式", width = 15, dicCode = "customs_clearance")
    @Dict(dicCode = "customs_clearance")
	@ApiModelProperty(value = "清关方式")
    private java.lang.String customsClearance;
	/**交货条款*/
	@Excel(name = "交货条款", width = 15, dicCode = "terms_of_delivery")
    @Dict(dicCode = "terms_of_delivery")
	@ApiModelProperty(value = "交货条款")
    private java.lang.String termsDelivery;
	/**交税方式*/
	@Excel(name = "交税方式", width = 15, dicCode = "tax_payment")
    @Dict(dicCode = "tax_payment")
	@ApiModelProperty(value = "交税方式")
    private java.lang.String taxPayment;
	/**VAT号*/
	@Excel(name = "VAT号", width = 15)
	@ApiModelProperty(value = "VAT号")
    private java.lang.String vat;
	/**属性*/
	@Excel(name = "属性", width = 15, dicCode = "item_properties")
    @Dict(dicCode = "item_properties")
	@ApiModelProperty(value = "属性")
    private java.lang.String attributes;
	/**应收运费单价*/
	@Excel(name = "应收运费单价", width = 15)
	@ApiModelProperty(value = "应收运费单价")
    private java.lang.String unitPriceFreightReceivable;
	/**已付款*/
	@Excel(name = "已付款", width = 15)
	@ApiModelProperty(value = "已付款")
    private java.lang.String paid;
	/**应收总运费*/
	@Excel(name = "应收总运费", width = 15)
	@ApiModelProperty(value = "应收总运费")
    private java.lang.String totalFreightReceivable;
	/**固定应收总运费*/
	@Excel(name = "固定应收总运费", width = 15)
	@ApiModelProperty(value = "固定应收总运费")
    private java.lang.String fixedTotalFreightReceivable;
	/**费用*/
	@Excel(name = "费用", width = 15)
	@ApiModelProperty(value = "费用")
    private java.lang.String cost;
	/**销售单价*/
	@Excel(name = "销售单价", width = 15)
	@ApiModelProperty(value = "销售单价")
    private java.lang.String unitPrice;
	/**销售总运费*/
	@Excel(name = "销售总运费", width = 15)
	@ApiModelProperty(value = "销售总运费")
    private java.lang.String totalSalesFreight;
	/**固定销售总运费*/
	@Excel(name = "固定销售总运费", width = 15)
	@ApiModelProperty(value = "固定销售总运费")
    private java.lang.String fixedSalesTotalFreight;
	/**销售价总费用*/
	@Excel(name = "销售价总费用", width = 15)
	@ApiModelProperty(value = "销售价总费用")
    private java.lang.String totalSellingPrice;
	/**成本运费单价*/
	@Excel(name = "成本运费单价", width = 15)
	@ApiModelProperty(value = "成本运费单价")
    private java.lang.String costFreightUnitPrice;
	/**成本总运费*/
	@Excel(name = "成本总运费", width = 15)
	@ApiModelProperty(value = "成本总运费")
    private java.lang.String costTotalFreight;
	/**固定成本总运费*/
	@Excel(name = "固定成本总运费", width = 15)
	@ApiModelProperty(value = "固定成本总运费")
    private java.lang.String fixedCostTotalFreight;
	/**成本总费用*/
	@Excel(name = "成本总费用", width = 15)
	@ApiModelProperty(value = "成本总费用")
    private java.lang.String totalCost;
	/**总利润*/
	@Excel(name = "总利润", width = 15)
	@ApiModelProperty(value = "总利润")
    private java.lang.String totalProfit;
	/**销售员利润*/
	@Excel(name = "销售员利润", width = 15)
	@ApiModelProperty(value = "销售员利润")
    private java.lang.String salerProfit;
	/**销售员提成*/
	@Excel(name = "销售员提成", width = 15)
	@ApiModelProperty(value = "销售员提成")
    private java.lang.String salerCommission;
	/**纯利润*/
	@Excel(name = "纯利润", width = 15)
	@ApiModelProperty(value = "纯利润")
    private java.lang.String profit;
	/**状态*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**问题件*/
	@Excel(name = "问题件", width = 15)
	@ApiModelProperty(value = "问题件")
    private java.lang.String problem;
	/**客服代表*/
	@Excel(name = "客服代表", width = 15)
	@ApiModelProperty(value = "客服代表")
    private java.lang.String deputyCustomer;
	/**销售代表*/
	@Excel(name = "销售代表", width = 15)
	@ApiModelProperty(value = "销售代表")
    private java.lang.String deputySale;
	/**财务代表*/
	@Excel(name = "财务代表", width = 15)
	@ApiModelProperty(value = "财务代表")
    private java.lang.String deputyFinancial;
	/**所在站点*/
	@Excel(name = "所在站点", width = 15)
	@ApiModelProperty(value = "所在站点")
    private java.lang.String site;
	/**拣货站点*/
	@Excel(name = "拣货站点", width = 15)
	@ApiModelProperty(value = "拣货站点")
    private java.lang.String pickingSite;
	/**自定义标识*/
	@Excel(name = "自定义标识", width = 15)
	@ApiModelProperty(value = "自定义标识")
    private java.lang.String customLogo;
	/**物品属性*/
	@Excel(name = "物品属性", width = 15)
	@ApiModelProperty(value = "物品属性")
    private java.lang.String itemAttributes;
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
	/**承运商*/
	@Excel(name = "承运商", width = 15)
	@ApiModelProperty(value = "承运商")
    private java.lang.String carrier;
	/**跟踪号*/
	@Excel(name = "跟踪号", width = 15)
	@ApiModelProperty(value = "跟踪号")
    private java.lang.String trackingNumber;
	/**最后路由*/
	@Excel(name = "最后路由", width = 15)
	@ApiModelProperty(value = "最后路由")
    private java.lang.String finalRoute;
	/**计费时间*/
	@Excel(name = "计费时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "计费时间")
    private java.util.Date timeBillable;
	/**签收时间*/
	@Excel(name = "签收时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "签收时间")
    private java.util.Date timeSubmission;
	/**拣货时间*/
	@Excel(name = "拣货时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "拣货时间")
    private java.util.Date timePicking;
	/**出货时间*/
	@Excel(name = "出货时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "出货时间")
    private java.util.Date timeDelivery;
	/**下单时间*/
	@Excel(name = "下单时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "下单时间")
    private java.util.Date timeOrder;
	/**出货单号*/
	@Excel(name = "出货单号", width = 15)
	@ApiModelProperty(value = "出货单号")
    private java.lang.String deliveryOrderId;
	/**提单号*/
	@Excel(name = "提单号", width = 15)
	@ApiModelProperty(value = "提单号")
    private java.lang.String billId;
	
	@ExcelCollection(name="货柜详情")
	@ApiModelProperty(value = "货柜详情")
	private List<ZmGoodCase> zmGoodCaseList;


	/**poNumber*/
	@Excel(name = "poNumber", width = 15)
	@ApiModelProperty(value = "poNumber")
	private java.lang.String poNumber;

	/**发件人地址编码*/
	@Excel(name = "senderAddressCode", width = 15)
	@ApiModelProperty(value = "senderAddressCode")
	private java.lang.String senderAddressCode;
	/**发件人姓名*/
	@Excel(name = "senderName", width = 15)
	@ApiModelProperty(value = "senderName")
	private java.lang.String senderName;
	/**发件人公司*/
	@Excel(name = "senderCompany", width = 15)
	@ApiModelProperty(value = "senderCompany")
	private java.lang.String senderCompany;
	/**发件人地址一*/
	@Excel(name = "senderAddressOne", width = 15)
	@ApiModelProperty(value = "senderAddressOne")
	private java.lang.String senderAddressOne;
	/**发件人地址二*/
	@Excel(name = "senderAddressTwo", width = 15)
	@ApiModelProperty(value = "senderAddressTwo")
	private java.lang.String senderAddressTwo;
	/**发件人地址三*/
	@Excel(name = "senderAddressTri", width = 15)
	@ApiModelProperty(value = "senderAddressTri")
	private java.lang.String senderAddressTri;
	/**发件人城市*/
	@Excel(name = "senderCity", width = 15)
	@ApiModelProperty(value = "senderCity")
	private java.lang.String senderCity;
	/**发件人省份*/
	@Excel(name = "senderState", width = 15)
	@ApiModelProperty(value = "senderState")
	private java.lang.String senderState;
	/**发件人邮编*/
	@Excel(name = "senderZipCode", width = 15)
	@ApiModelProperty(value = "senderZipCode")
	private java.lang.String senderZipCode;
	/**发件人国家*/
	@Excel(name = "senderCountry", width = 15)
	@ApiModelProperty(value = "senderCountry")
	private java.lang.String senderCountry;
	/**发件人电话*/
	@Excel(name = "senderTel", width = 15)
	@ApiModelProperty(value = "senderTel")
	private java.lang.String senderTel;
	/**发件人邮箱*/
	@Excel(name = "senderEmail", width = 15)
	@ApiModelProperty(value = "senderEmail")
	private java.lang.String senderEmail;
}
