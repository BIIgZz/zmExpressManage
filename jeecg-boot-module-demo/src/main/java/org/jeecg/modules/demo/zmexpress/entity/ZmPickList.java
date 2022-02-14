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
 * @Description: zm_pick_list
 * @Author: jeecg-boot
 * @Date:   2021-12-18
 * @Version: V1.0
 */
@Data
@TableName("zm_pick_list")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_pick_list对象", description="zm_pick_list")
public class ZmPickList implements Serializable {
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
	/**用户*/
	@Excel(name = "用户", width = 15)
    @ApiModelProperty(value = "用户")
    private java.lang.String user;
	/**用户编号*/
	@Excel(name = "用户编号", width = 15)
    @ApiModelProperty(value = "用户编号")
    private java.lang.String userId;
	/**运单号*/
	@Excel(name = "运单号", width = 15)
    @ApiModelProperty(value = "运单号")
    private java.lang.String waybillId;
	/**货箱号*/
	@Excel(name = "货箱号", width = 15)
    @ApiModelProperty(value = "货箱号")
    private java.lang.String boxNum;
	/**客户参考号*/
	@Excel(name = "客户参考号", width = 15)
    @ApiModelProperty(value = "客户参考号")
    private java.lang.String clientReferenceNo;
	/**FBA/扩展箱号*/
	@Excel(name = "FBA/扩展箱号", width = 15)
    @ApiModelProperty(value = "FBA/扩展箱号")
    private java.lang.String fbaid;
	/**清关号*/
	@Excel(name = "清关号", width = 15)
    @ApiModelProperty(value = "清关号")
    private java.lang.String clearCustoms;
	/**实际重量*/
	@Excel(name = "实际重量", width = 15)
    @ApiModelProperty(value = "实际重量")
    private java.lang.String weight;
	/**尺寸*/
	@Excel(name = "尺寸", width = 15)
    @ApiModelProperty(value = "尺寸")
    private java.lang.String size;
	/**拣货单编号*/
	@Excel(name = "拣货单编号", width = 15)
    @ApiModelProperty(value = "拣货单编号")
    private java.lang.String pickNo;
	/**发往国家*/
	@Excel(name = "发往国家", width = 15)
    @ApiModelProperty(value = "发往国家")
    private java.lang.String country;
	/**拣货人*/
	@Excel(name = "拣货人", width = 15)
    @ApiModelProperty(value = "拣货人")
    private java.lang.String operator;
	/**拣货时间*/
	@Excel(name = "拣货时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "拣货时间")
    private java.util.Date pickTime;
}
