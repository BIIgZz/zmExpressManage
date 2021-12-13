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
 * @Description: zm_service
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
@Data
@TableName("zm_service")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_service对象", description="zm_service")
public class ZmService implements Serializable {
    private static final long serialVersionUID = 1L;

	/**服务id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "服务id")
    private String id;
	/**服务名称*/
	@Excel(name = "服务名称", width = 15)
    @ApiModelProperty(value = "服务名称")
    private String name;
	/**服务代码*/
	@Excel(name = "服务代码", width = 15)
    @ApiModelProperty(value = "服务代码")
    private String serviceCode;
	/**服务分类*/
	@Excel(name = "服务分类", width = 15, dicCode = "service_sort")
	@Dict(dicCode = "service_sort")
    @ApiModelProperty(value = "服务分类")
    private String serviceSort;
	/**计费方式*/
	@Excel(name = "计费方式", width = 15, dicCode = "bill")
	@Dict(dicCode = "bill")
    @ApiModelProperty(value = "计费方式")
    private String billingPlan;
	/**计重方式*/
	@Excel(name = "计重方式", width = 15, dicCode = "weight")
	@Dict(dicCode = "weight")
    @ApiModelProperty(value = "计重方式")
    private String weighingMethod;
	/**计泡系数*/
	@Excel(name = "计泡系数", width = 15)
    @ApiModelProperty(value = "计泡系数")
    private String bubble;
	/**分泡比例*/
	@Excel(name = "分泡比例", width = 15)
    @ApiModelProperty(value = "分泡比例")
    private String bubbleSplittingRatio;
	/**销售提成基数*/
	@Excel(name = "销售提成基数", width = 15)
    @ApiModelProperty(value = "销售提成基数")
    private String salesCommissionBase;
	/**销售提成比例*/
	@Excel(name = "销售提成比例", width = 15)
    @ApiModelProperty(value = "销售提成比例")
    private String salesCommission;
	/**销售提成单价*/
	@Excel(name = "销售提成单价", width = 15)
    @ApiModelProperty(value = "销售提成单价")
    private String salesCommissionUnitPrice;
	/**运单号规则*/
	@Excel(name = "运单号规则", width = 15)
    @ApiModelProperty(value = "运单号规则")
    private String numberRules;
	/**标签代码*/
	@Excel(name = "标签代码", width = 15)
    @ApiModelProperty(value = "标签代码")
    private String tagCode;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "service_status")
	@Dict(dicCode = "service_status")
    @ApiModelProperty(value = "状态")
    private String status;
	/**发件人*/
	@Excel(name = "发件人", width = 15)
    @ApiModelProperty(value = "发件人")
    private String sender;
	/**支持报关方式*/
	@Excel(name = "支持报关方式", width = 15, dicCode = "customs_eclaration")
	@Dict(dicCode = "customs_eclaration")
    @ApiModelProperty(value = "支持报关方式")
    private String customsDeclarationMethod;
	/**支持清关方式*/
	@Excel(name = "支持清关方式", width = 15, dicCode = "customs_clearance")
	@Dict(dicCode = "customs_clearance")
    @ApiModelProperty(value = "支持清关方式")
    private String customsLearanceMethod;
	/**支持交税方式*/
	@Excel(name = "支持交税方式", width = 15, dicCode = "tax_payment")
	@Dict(dicCode = "tax_payment")
    @ApiModelProperty(value = "支持交税方式")
    private String taxPaymentMethod;
	/**支持交货条款*/
	@Excel(name = "支持交货条款", width = 15, dicCode = "delivery_terms")
	@Dict(dicCode = "delivery_terms")
    @ApiModelProperty(value = "支持交货条款")
    private String deliveryTerms;
	/**带电标识*/
	@Excel(name = "带电标识", width = 15)
    @ApiModelProperty(value = "带电标识")
    private String electric;
	/**最低箱收费重*/
	@Excel(name = "最低箱收费重", width = 15)
    @ApiModelProperty(value = "最低箱收费重")
    private String boxWeight;
	/**到达国家*/
	@Excel(name = "到达国家", width = 15)
    @ApiModelProperty(value = "到达国家")
    private String country;
	/**申报币种*/
	@Excel(name = "申报币种", width = 15, dicCode = "currency")
	@Dict(dicCode = "currency")
    @ApiModelProperty(value = "申报币种")
    private String reportingCurrency;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
    @ApiModelProperty(value = "创建日期")
    private String createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**update_time*/
    @ApiModelProperty(value = "update_time")
    private String updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
}
