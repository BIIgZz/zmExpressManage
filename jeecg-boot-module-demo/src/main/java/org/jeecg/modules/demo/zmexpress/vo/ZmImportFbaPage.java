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
 * @Description: 导入FBA表
 * @Author: jeecg-boot
 * @Date:   2021-07-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="zm_import_fbaPage对象", description="导入FBA表")
public class ZmImportFbaPage {

	/**fba仓库id*/
	@ApiModelProperty(value = "fba仓库id")
    private String id;
	/**FBA ID*/
	@Excel(name = "FBA ID", width = 15)
	@ApiModelProperty(value = "FBA ID")
    private String fbaid;
	/**客户订单号*/
	@Excel(name = "客户订单号", width = 15)
	@ApiModelProperty(value = "客户订单号")
    private String orderid;
	/**服务*/
	@Excel(name = "服务", width = 15)
	@ApiModelProperty(value = "服务")
    private String serviceId;
	/**地址库编码*/
	@Excel(name = "地址库编码", width = 15)
	@ApiModelProperty(value = "地址库编码")
    private String code;
	/**收件人姓名*/
	@Excel(name = "收件人姓名", width = 15)
	@ApiModelProperty(value = "收件人姓名")
    private String name;
	/**收件人公司*/
	@Excel(name = "收件人公司", width = 15)
	@ApiModelProperty(value = "收件人公司")
    private String company;
	/**收件人地址*/
	@Excel(name = "收件人地址", width = 15)
	@ApiModelProperty(value = "收件人地址")
    private String address;
	/**收件人城市*/
	@Excel(name = "收件人城市", width = 15)
	@ApiModelProperty(value = "收件人城市")
    private String city;
	/**收件人省份*/
	@Excel(name = "收件人省份", width = 15)
	@ApiModelProperty(value = "收件人省份")
    private String province;
	/**收件人邮编*/
	@Excel(name = "收件人邮编", width = 15)
	@ApiModelProperty(value = "收件人邮编")
    private String postcode;
	/**收件人国家代码*/
	@Excel(name = "收件人国家代码", width = 15)
	@ApiModelProperty(value = "收件人国家代码")
    private String countryCode;
	/**收件人电话*/
	@Excel(name = "收件人电话", width = 15)
	@ApiModelProperty(value = "收件人电话")
    private String tel;
	/**收件人邮箱*/
	@Excel(name = "收件人邮箱", width = 15)
	@ApiModelProperty(value = "收件人邮箱")
    private String email;
	/**总箱数*/
	@Excel(name = "总箱数", width = 15)
	@ApiModelProperty(value = "总箱数")
    private Integer caseNumber;
	/**是否带电*/
	@Excel(name = "是否带电", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
	@ApiModelProperty(value = "是否带电")
    private Integer electrical;
	/**是否带磁性*/
	@Excel(name = "是否带磁性", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
	@ApiModelProperty(value = "是否带磁性")
    private Integer magnetic;
	/**是否是液体*/
	@Excel(name = "是否是液体", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
	@ApiModelProperty(value = "是否是液体")
    private Integer liquid;
	/**是否粉末*/
	@Excel(name = "是否粉末", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
	@ApiModelProperty(value = "是否粉末")
    private Integer powder;
	/**是否是危险品*/
	@Excel(name = "是否是危险品", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
	@ApiModelProperty(value = "是否是危险品")
    private Integer dangerous;
	/**报关方式*/
	@Excel(name = "报关方式", width = 15, dicCode = "customs_eclaration")
    @Dict(dicCode = "customs_eclaration")
	@ApiModelProperty(value = "报关方式")
    private Integer customsEclaration;
	/**清关方式*/
	@Excel(name = "清关方式", width = 15, dicCode = "customs_clearance")
    @Dict(dicCode = "customs_clearance")
	@ApiModelProperty(value = "清关方式")
    private Integer customsClearance;
	/**交税方式*/
	@Excel(name = "交税方式", width = 15, dicCode = "tax_payment")
    @Dict(dicCode = "tax_payment")
	@ApiModelProperty(value = "交税方式")
    private Integer taxPayment;
	/**交货条款*/
	@Excel(name = "交货条款", width = 15)
	@ApiModelProperty(value = "交货条款")
    private String deliveryTerm;
	/**VAT号*/
	@Excel(name = "VAT号", width = 15)
	@ApiModelProperty(value = "VAT号")
    private String vat;
	/**参考号1*/
	@Excel(name = "参考号1", width = 15)
	@ApiModelProperty(value = "参考号1")
    private String referenceNumber1;
	/**参考号2*/
	@Excel(name = "参考号2", width = 15)
	@ApiModelProperty(value = "参考号2")
    private String referenceNumber2;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private String note;
	/**发件人公司*/
	@Excel(name = "发件人公司", width = 15)
	@ApiModelProperty(value = "发件人公司")
    private String companySender;
	/**发件人姓名*/
	@Excel(name = "发件人姓名", width = 15)
	@ApiModelProperty(value = "发件人姓名")
    private String nameSender;
	/**发件人地址*/
	@Excel(name = "发件人地址", width = 15)
	@ApiModelProperty(value = "发件人地址")
    private String addressSender;
	/**发件人城市*/
	@Excel(name = "发件人城市", width = 15)
	@ApiModelProperty(value = "发件人城市")
    private String citySender;
	/**发件人省份*/
	@Excel(name = "发件人省份", width = 15)
	@ApiModelProperty(value = "发件人省份")
    private String provinceSender;
	/**发件人邮编*/
	@Excel(name = "发件人邮编", width = 15)
	@ApiModelProperty(value = "发件人邮编")
    private String postcodeSender;
	/**发件人国家代码*/
	@Excel(name = "发件人国家代码", width = 15)
	@ApiModelProperty(value = "发件人国家代码")
    private String countryCodeSender;
	/**发件人电话*/
	@Excel(name = "发件人电话", width = 15)
	@ApiModelProperty(value = "发件人电话")
    private String telSender;
	/**发件人邮箱*/
	@Excel(name = "发件人邮箱", width = 15)
	@ApiModelProperty(value = "发件人邮箱")
    private String emailSender;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private String createBy;

	@ExcelCollection(name="货柜详情")
	@ApiModelProperty(value = "货柜详情")
	private List<ZmImportGood> zmImportGoodList;


}
