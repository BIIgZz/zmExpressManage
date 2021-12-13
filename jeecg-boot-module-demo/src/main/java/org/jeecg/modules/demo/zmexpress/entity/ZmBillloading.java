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
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
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
	/**提单号*/
	@Excel(name = "提单号", width = 15)
    @ApiModelProperty(value = "提单号")
    private String billnum;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String type;
	/**供应商*/
	@Excel(name = "供应商", width = 15)
    @ApiModelProperty(value = "供应商")
    private String supplier;
	/**承运商*/
	@Excel(name = "承运商", width = 15)
    @ApiModelProperty(value = "承运商")
    private String arriage;
	/**清关公司*/
	@Excel(name = "清关公司", width = 15)
    @ApiModelProperty(value = "清关公司")
    private String customsClearance;
	/**路线*/
	@Excel(name = "路线", width = 15)
    @ApiModelProperty(value = "路线")
    private String line;
	/**用户*/
	@Excel(name = "用户", width = 15)
    @ApiModelProperty(value = "用户")
    private String user;
	/**箱数*/
	@Excel(name = "箱数", width = 15)
    @ApiModelProperty(value = "箱数")
    private Integer boxes;
	/**合箱数*/
	@Excel(name = "合箱数", width = 15)
    @ApiModelProperty(value = "合箱数")
    private Integer allBoxes;
	/**实重*/
	@Excel(name = "实重", width = 15)
    @ApiModelProperty(value = "实重")
    private String weight;
	/**材重*/
	@Excel(name = "材重", width = 15)
    @ApiModelProperty(value = "材重")
    private String materialWeight;
	/**收费重*/
	@Excel(name = "收费重", width = 15)
    @ApiModelProperty(value = "收费重")
    private String heavyCharge;
	/**体积*/
	@Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private String volume;
	/**盈亏*/
	@Excel(name = "盈亏", width = 15)
    @ApiModelProperty(value = "盈亏")
    private String profit;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**内部备注*/
	@Excel(name = "内部备注", width = 15)
    @ApiModelProperty(value = "内部备注")
    private String inRemark;
	/**出发时间*/
	@Excel(name = "出发时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "出发时间")
    private Date departure;
	/**到达时间*/
	@Excel(name = "到达时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "到达时间")
    private Date arrive;
	/**清关时间*/
	@Excel(name = "清关时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "清关时间")
    private Date customsClearanceTime;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private String status;
}
