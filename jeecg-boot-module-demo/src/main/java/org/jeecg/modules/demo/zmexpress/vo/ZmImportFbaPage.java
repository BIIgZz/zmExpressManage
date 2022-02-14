package org.jeecg.modules.demo.zmexpress.vo;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportFba;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
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
 * @Description: 导入FBA表(已导出)
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@Data
@ApiModel(value="zm_import_fbaPage对象", description="导入FBA表(已导出)")
public class ZmImportFbaPage {

	/**fba仓库id*/
	@ApiModelProperty(value = "fba仓库id")
    private java.lang.String id;
	/**FBA ID*/
	@Excel(name = "FBA ID", width = 15)
	@ApiModelProperty(value = "FBA ID")
    private java.lang.String fbaid;
	/**客户订单号*/
	@Excel(name = "客户订单号", width = 15)
	@ApiModelProperty(value = "客户订单号")
    private java.lang.String orderid;
	/**服务*/
	@Excel(name = "服务", width = 15, dictTable = "zm_service", dicText = "name", dicCode = "name")
    @Dict(dictTable = "zm_service", dicText = "name", dicCode = "name")
	@ApiModelProperty(value = "服务")
    private java.lang.String serviceId;
	/**地址库编码*/
	@Excel(name = "地址库编码", width = 15, dictTable = "zm_fba_warehouse_detail", dicText = "code", dicCode = "code")
    @Dict(dictTable = "zm_fba_warehouse_detail", dicText = "code", dicCode = "code")
	@ApiModelProperty(value = "地址库编码")
    private java.lang.String code;
	/**收件人*/
	@Excel(name = "收件人", width = 15)
	@ApiModelProperty(value = "收件人")
    private java.lang.String name;
	/**公司*/
	@Excel(name = "公司", width = 15)
	@ApiModelProperty(value = "公司")
    private java.lang.String company;
	/**地址*/
	@Excel(name = "地址", width = 15)
	@ApiModelProperty(value = "地址")
    private java.lang.String address;
	/**城市*/
	@Excel(name = "城市", width = 15)
	@ApiModelProperty(value = "城市")
    private java.lang.String city;
	/**州*/
	@Excel(name = "州", width = 15)
	@ApiModelProperty(value = "州")
    private java.lang.String province;
	/**邮编*/
	@Excel(name = "邮编", width = 15)
	@ApiModelProperty(value = "邮编")
    private java.lang.String postcode;
	/**国家*/
	@Excel(name = "国家", width = 15, dictTable = "zm_tool_countries", dicText = "cname", dicCode = "cname")
    @Dict(dictTable = "zm_tool_countries", dicText = "cname", dicCode = "cname")
	@ApiModelProperty(value = "国家")
    private java.lang.String countryCode;
	/**电话*/
	@Excel(name = "电话", width = 15)
	@ApiModelProperty(value = "电话")
    private java.lang.String tel;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
	@ApiModelProperty(value = "邮箱")
    private java.lang.String email;
	/**总箱数*/
	@Excel(name = "总箱数", width = 15)
	@ApiModelProperty(value = "总箱数")
    private java.lang.Integer caseNumber;
	/**物品属性*/
	@Excel(name = "物品属性", width = 15, dicCode = "item_properties")
    @Dict(dicCode = "item_properties")
	@ApiModelProperty(value = "物品属性")
    private java.lang.String properties;
	/**报关方式*/
	@Excel(name = "报关方式", width = 15, dicCode = "customs_eclaration")
    @Dict(dicCode = "customs_eclaration")
	@ApiModelProperty(value = "报关方式")
    private java.lang.Integer customsEclaration;
	/**清关方式*/
	@Excel(name = "清关方式", width = 15, dicCode = "customs_clearance")
    @Dict(dicCode = "customs_clearance")
	@ApiModelProperty(value = "清关方式")
    private java.lang.Integer customsClearance;
	/**交税方式*/
	@Excel(name = "交税方式", width = 15, dicCode = "tax_payment")
    @Dict(dicCode = "tax_payment")
	@ApiModelProperty(value = "交税方式")
    private java.lang.Integer taxPayment;
	/**交货条款*/
	@Excel(name = "交货条款", width = 15, dicCode = "terms_of_delivery")
    @Dict(dicCode = "terms_of_delivery")
	@ApiModelProperty(value = "交货条款")
    private java.lang.String deliveryTerm;
	/**VAT号*/
	@Excel(name = "VAT号", width = 15)
	@ApiModelProperty(value = "VAT号")
    private java.lang.String vat;
	/**参考号1*/
	@Excel(name = "参考号1", width = 15)
	@ApiModelProperty(value = "参考号1")
    private java.lang.String referenceNumber1;
	/**参考号2*/
	@Excel(name = "参考号2", width = 15)
	@ApiModelProperty(value = "参考号2")
    private java.lang.String referenceNumber2;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private java.lang.String note;
	/**发件人姓名*/
	@Excel(name = "发件人姓名", width = 15)
	@ApiModelProperty(value = "发件人姓名")
    private java.lang.String nameSender;
	/**公司*/
	@Excel(name = "公司", width = 15)
	@ApiModelProperty(value = "公司")
    private java.lang.String companySender;
	/**地址*/
	@Excel(name = "地址", width = 15)
	@ApiModelProperty(value = "地址")
    private java.lang.String addressSender;
	/**城市*/
	@Excel(name = "城市", width = 15)
	@ApiModelProperty(value = "城市")
    private java.lang.String citySender;
	/**省份*/
	@Excel(name = "省份", width = 15)
	@ApiModelProperty(value = "省份")
    private java.lang.String provinceSender;
	/**邮编*/
	@Excel(name = "邮编", width = 15)
	@ApiModelProperty(value = "邮编")
    private java.lang.String postcodeSender;
	/**国家*/
	@Excel(name = "国家", width = 15, dictTable = "zm_tool_countries", dicText = "cname", dicCode = "cname")
    @Dict(dictTable = "zm_tool_countries", dicText = "cname", dicCode = "cname")
	@ApiModelProperty(value = "国家")
    private java.lang.String countryCodeSender;
	/**电话*/
	@Excel(name = "电话", width = 15)
	@ApiModelProperty(value = "电话")
    private java.lang.String telSender;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
	@ApiModelProperty(value = "邮箱")
    private java.lang.String emailSender;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**状态*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**客户*/
	@Excel(name = "客户", width = 15)
	@ApiModelProperty(value = "客户")
    private java.lang.String clientid;
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
	private List<ZmImportGood> zmImportGoodList;
	
}
