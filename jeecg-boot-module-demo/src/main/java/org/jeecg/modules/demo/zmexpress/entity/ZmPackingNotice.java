package org.jeecg.modules.demo.zmexpress.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 装箱通知书
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
@ApiModel(value="zm_packing_notice对象", description="装箱通知书")
@Data
@TableName("zm_packing_notice")
public class ZmPackingNotice implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**订舱单*/
    @ApiModelProperty(value = "订舱单")
    private String shippingOrderId;
	/**客户*/
	@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private String client;
	/**入仓单号*/
	@Excel(name = "入仓单号", width = 15)
    @ApiModelProperty(value = "入仓单号")
    private String warehouseReceiptNumber;
	/**入仓情况*/
	@Excel(name = "入仓情况", width = 15)
    @ApiModelProperty(value = "入仓情况")
    private String entrySituation;
	/**渠道*/
	@Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private String channel;
	/**装柜顺序*/
	@Excel(name = "装柜顺序", width = 15)
    @ApiModelProperty(value = "装柜顺序")
    private String loadingSequence;
	/**件数*/
	@Excel(name = "件数", width = 15)
    @ApiModelProperty(value = "件数")
    private String pieces;
	/**体积*/
	@Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private String volume;
	/**毛重*/
	@Excel(name = "毛重", width = 15)
    @ApiModelProperty(value = "毛重")
    private String weight;
	/**货物名称*/
	@Excel(name = "货物名称", width = 15)
    @ApiModelProperty(value = "货物名称")
    private String name;
	/**仓库代码*/
	@Excel(name = "仓库代码", width = 15)
    @ApiModelProperty(value = "仓库代码")
    private String code;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remarks;
	/**fbaid*/
	@Excel(name = "fbaid", width = 15)
    @ApiModelProperty(value = "fbaid")
    private String fbaid;
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
}
