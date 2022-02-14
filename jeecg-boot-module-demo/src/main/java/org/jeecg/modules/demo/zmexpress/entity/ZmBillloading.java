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
 * @Description: 提单表
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Data
@TableName("zm_billloading")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_billloading对象", description="提单表")
public class ZmBillloading implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**柜号*/
    @Excel(name = "柜号", width = 15)
    @ApiModelProperty(value = "柜号")
    private java.lang.String billnum;
    /**提单号*/
    @Excel(name = "提单号", width = 15)
    @ApiModelProperty(value = "提单号")
    private java.lang.String inBillnum;
    /**类型*/
    @Excel(name = "类型", width = 15, dicCode = "bill_loding")
    @Dict(dicCode = "bill_loding")
    @ApiModelProperty(value = "类型")
    private java.lang.String type;
    /**供应商*/
    @Excel(name = "供应商", width = 15)
    @Dict(dictTable = "zm_supplier  ", dicText = "company", dicCode = "company")
    @ApiModelProperty(value = "供应商")
    private java.lang.String supplier;
    /**承运商*/
    @Excel(name = "承运商", width = 15 )
    @Dict(dictTable = "zm_supplier", dicText = "company", dicCode = "company")
    @ApiModelProperty(value = "承运商")
    private java.lang.String arriage;
    /**清关公司*/
    @Excel(name = "清关公司", width = 15 )
    @Dict(dictTable = "zm_supplier", dicText = "company", dicCode = "company")
    @ApiModelProperty(value = "清关公司")
    private java.lang.String customsClearance;
    /**报关公司*/
    @Excel(name = "报关公司", width = 15 )
    @Dict(dictTable = "zm_supplier", dicText = "company", dicCode = "company")
    @ApiModelProperty(value = "报关公司")
    private java.lang.String declarationCompany;
    /**出发站点*/
    @Excel(name = "出发站点", width = 15 )
    @Dict(dictTable = "zm_airport", dicText = "name", dicCode = "name")
    @ApiModelProperty(value = "出发站点")
    private java.lang.String departurePoint;
    /**发往站点*/
    @Excel(name = "发往站点", width = 15 )
    @Dict(dictTable = "zm_airport", dicText = "name", dicCode = "name")
    @ApiModelProperty(value = "发往站点")
    private java.lang.String sendSite;
    /**箱数*/
    @Excel(name = "箱数", width = 15)
    @ApiModelProperty(value = "箱数")
    private java.lang.Integer boxes;
    /**合箱数*/
    @Excel(name = "合箱数", width = 15)
    @ApiModelProperty(value = "合箱数")
    private java.lang.Integer allBoxes;
    /**价值*/
    @Excel(name = "价值", width = 15)
    @ApiModelProperty(value = "价值")
    private java.lang.String worth;
    /**实重*/
    @Excel(name = "实重", width = 15)
    @ApiModelProperty(value = "实重")
    private java.lang.String weight;
    /**体积比*/
    @Excel(name = "体积比", width = 15)
    @ApiModelProperty(value = "体积比")
    private java.lang.String volumeRatio;
    /**应收*/
    @Excel(name = "应收", width = 15)
    @ApiModelProperty(value = "应收")
    private java.lang.String receivable;
    /**提单应付*/
    @Excel(name = "提单应付", width = 15)
    @ApiModelProperty(value = "提单应付")
    private java.lang.String billLadingPayable;
    /**运单应付*/
    @Excel(name = "运单应付", width = 15)
    @ApiModelProperty(value = "运单应付")
    private java.lang.String waybillPayable;
    /**体积*/
    @Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private java.lang.String volume;
    /**盈亏*/
    @Excel(name = "盈亏", width = 15)
    @ApiModelProperty(value = "盈亏")
    private java.lang.String profit;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
    /**内部备注*/
    @Excel(name = "内部备注", width = 15)
    @ApiModelProperty(value = "内部备注")
    private java.lang.String inRemark;
    /**出发时间*/
    @Excel(name = "发出时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出发时间")
    private java.util.Date departure;
    /**到达时间*/
    @Excel(name = "到达时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "到达时间")
    private java.util.Date arrive;
    /**清关时间*/
    @Excel(name = "清关时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "清关时间")
    private java.util.Date customsClearanceTime;
    /**创建日期*/
    @Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**状态*/
	@Excel(name = "状态", width = 15,dicCode="status_billLoding", dicText = "name")
    @ApiModelProperty(value = "状态")
    private String status;
    /**集装箱规格*/
    @Dict(dicCode = "container")
    @ApiModelProperty(value = "集装箱规格")
    private java.lang.String container;
    /**班次*/
    @ApiModelProperty(value = "班次")
    private java.lang.String shift;
    /**船号*/
    @ApiModelProperty(value = "船号")
    private java.lang.String shipNumber;
    /**工作号*/
    @ApiModelProperty(value = "工作号")
    private java.lang.String workNo;
}
