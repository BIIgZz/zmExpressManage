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
 * @Description: 服务
 * @Author: jeecg-boot
 * @Date:   2021-12-30
 * @Version: V1.0
 */
@Data
@TableName("zm_service")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_service对象", description="服务")
public class ZmService implements Serializable {
    private static final long serialVersionUID = 1L;

	/**服务id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "服务id")
    private java.lang.String id;
	/**服务名称*/
	@Excel(name = "服务名称", width = 15)
    @ApiModelProperty(value = "服务名称")
    private java.lang.String name;
	/**服务代码*/
	@Excel(name = "服务代码", width = 15)
    @ApiModelProperty(value = "服务代码")
    private java.lang.String serviceCode;
	/**服务分类*/
	@Excel(name = "服务分类", width = 15, dicCode = "service_sort")
	@Dict(dicCode = "service_sort")
    @ApiModelProperty(value = "服务分类")
    private java.lang.String serviceSort;
	/**计费方式*/
	@Excel(name = "计费方式", width = 15, dicCode = "bill")
	@Dict(dicCode = "bill")
    @ApiModelProperty(value = "计费方式")
    private java.lang.String billingPlan;
	/**计重方式*/
	@Excel(name = "计重方式", width = 15, dicCode = "weight")
	@Dict(dicCode = "weight")
    @ApiModelProperty(value = "计重方式")
    private java.lang.String weighingMethod;
	/**进位方式*/
	@Excel(name = "进位方式", width = 15, dicCode = "carry_method")
	@Dict(dicCode = "carry_method")
    @ApiModelProperty(value = "进位方式")
    private java.lang.String carryMethod;
	/**计泡系数*/
	@Excel(name = "计泡系数", width = 15)
    @ApiModelProperty(value = "计泡系数")
    private java.lang.String bubble;
	/**分泡比例*/
	@Excel(name = "分泡比例", width = 15)
    @ApiModelProperty(value = "分泡比例")
    private java.lang.Double bubbleSplittingRatio;
	/**销售提成基数*/
	@Excel(name = "销售提成基数", width = 15, dicCode = "sales_base")
	@Dict(dicCode = "sales_base")
    @ApiModelProperty(value = "销售提成基数")
    private java.lang.String salesCommissionBase;
	/**销售提成比例*/
	@Excel(name = "销售提成比例", width = 15)
    @ApiModelProperty(value = "销售提成比例")
    private java.lang.String salesCommission;
	/**销售提成单价*/
	@Excel(name = "销售提成单价", width = 15)
    @ApiModelProperty(value = "销售提成单价")
    private java.lang.String salesCommissionUnitPrice;
	/**运单号规则*/
	@Excel(name = "运单号规则", width = 15)
    @ApiModelProperty(value = "运单号规则")
    private java.lang.String numberRules;
	/**标签代码*/
	@Excel(name = "标签代码", width = 15)
    @ApiModelProperty(value = "标签代码")
    private java.lang.String tagCode;
	/**运单号规则*/
	@Excel(name = "运单号规则", width = 15)
    @ApiModelProperty(value = "运单号规则")
    private java.lang.String waybilRules;
	/**申报后补*/
	@Excel(name = "申报后补", width = 15)
    @ApiModelProperty(value = "申报后补")
    private java.lang.String supplementAfterDeclaration;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "service_status")
	@Dict(dicCode = "service_status")
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**取消方式*/
	@Excel(name = "取消方式", width = 15, dicCode = "cancel_method")
	@Dict(dicCode = "cancel_method")
    @ApiModelProperty(value = "取消方式")
    private java.lang.String cancelMethod;
	/**发件人*/
	@Excel(name = "发件人", width = 15, dicCode = "fill_in")
	@Dict(dicCode = "fill_in")
    @ApiModelProperty(value = "发件人")
    private java.lang.String sender;
	/**支持报关方式*/
	@Excel(name = "支持报关方式", width = 15, dicCode = "customs_eclaration")
	@Dict(dicCode = "customs_eclaration")
    @ApiModelProperty(value = "支持报关方式")
    private java.lang.String customsDeclarationMethod;
	/**支持清关方式*/
	@Excel(name = "支持清关方式", width = 15, dicCode = "customs_clearance")
	@Dict(dicCode = "customs_clearance")
    @ApiModelProperty(value = "支持清关方式")
    private java.lang.String customsLearanceMethod;
	/**支持交税方式*/
	@Excel(name = "支持交税方式", width = 15, dicCode = "tax_payment")
	@Dict(dicCode = "tax_payment")
    @ApiModelProperty(value = "支持交税方式")
    private java.lang.String taxPaymentMethod;
	/**支持交货条款*/
	@Excel(name = "支持交货条款", width = 15, dicCode = "delivery_terms")
	@Dict(dicCode = "delivery_terms")
    @ApiModelProperty(value = "支持交货条款")
    private java.lang.String deliveryTerms;
	/**票记重精度*/
	@Excel(name = "票记重精度", width = 15)
    @ApiModelProperty(value = "票记重精度")
    private java.lang.String ticketWeightAccuracy;
	/**箱记重精度*/
	@Excel(name = "箱记重精度", width = 15)
    @ApiModelProperty(value = "箱记重精度")
    private java.lang.String caseWeightAccuracy;
	/**尺寸精度*/
	@Excel(name = "尺寸精度", width = 15)
    @ApiModelProperty(value = "尺寸精度")
    private java.lang.String sizeAccuracy;
	/**最小件数*/
	@Excel(name = "最小件数", width = 15)
    @ApiModelProperty(value = "最小件数")
    private java.lang.String minPieceNum;
	/**最大件数*/
	@Excel(name = "最大件数", width = 15)
    @ApiModelProperty(value = "最大件数")
    private java.lang.String maxPieceNum;
	/**最低箱实重*/
	@Excel(name = "最低箱实重", width = 15)
    @ApiModelProperty(value = "最低箱实重")
    private java.lang.Double minBoxWeight;
	/**最低箱材重*/
	@Excel(name = "最低箱材重", width = 15)
    @ApiModelProperty(value = "最低箱材重")
    private java.lang.String minBoxMaterialWeight;
	/**最低箱收费重*/
	@Excel(name = "最低箱收费重", width = 15)
    @ApiModelProperty(value = "最低箱收费重")
    private java.lang.String minBoxChargeWeight;
	/**最低箱均重*/
	@Excel(name = "最低箱均重", width = 15)
    @ApiModelProperty(value = "最低箱均重")
    private java.lang.String minBoxAverageWeight;
	/**最低票收费重*/
	@Excel(name = "最低票收费重", width = 15)
    @ApiModelProperty(value = "最低票收费重")
    private java.lang.Integer minTicketChargeWeight;
	/**最小票实重*/
	@Excel(name = "最小票实重", width = 15)
    @ApiModelProperty(value = "最小票实重")
    private java.lang.String minTicketWeight;
	/**最大票实重*/
	@Excel(name = "最大票实重", width = 15)
    @ApiModelProperty(value = "最大票实重")
    private java.lang.String maxTicketWeight;
	/**最大票收费重*/
	@Excel(name = "最大票收费重", width = 15)
    @ApiModelProperty(value = "最大票收费重")
    private java.lang.String maxTicketChargeWeight;
	/**最大箱实重*/
	@Excel(name = "最大箱实重", width = 15)
    @ApiModelProperty(value = "最大箱实重")
    private java.lang.Integer maxBoxWeight;
	/**用户等级*/
	@Excel(name = "用户等级", width = 15, dictTable = "zm_user_level", dicText = "name", dicCode = "name")
	@Dict(dictTable = "zm_user_level", dicText = "name", dicCode = "name")
    @ApiModelProperty(value = "用户等级")
    private java.lang.String userLevel;
	/**指定用户*/
	@Excel(name = "指定用户", width = 15)
    @ApiModelProperty(value = "指定用户")
    private java.lang.String designatedUser;
	/**时效*/
	@Excel(name = "时效", width = 15)
    @ApiModelProperty(value = "时效")
    private java.lang.String aging;
	/**默认交货区域*/
	@Excel(name = "默认交货区域", width = 15)
    @ApiModelProperty(value = "默认交货区域")
    private java.lang.String area;
	/**是否显示*/
	@Excel(name = "是否显示", width = 15)
    @ApiModelProperty(value = "是否显示")
    private java.lang.String display;
	/**带电标识*/
	@Excel(name = "带电标识", width = 15)
    @ApiModelProperty(value = "带电标识")
    private java.lang.String electric;
	/**到达国家*/
	@Excel(name = "到达国家", width = 15)
    @ApiModelProperty(value = "到达国家")
    private java.lang.String country;
	/**申报币种*/
	@Excel(name = "申报币种", width = 15, dicCode = "currency")
	@Dict(dicCode = "currency")
    @ApiModelProperty(value = "申报币种")
    private java.lang.String reportingCurrency;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
    @ApiModelProperty(value = "创建日期")
    private java.lang.String createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
}
