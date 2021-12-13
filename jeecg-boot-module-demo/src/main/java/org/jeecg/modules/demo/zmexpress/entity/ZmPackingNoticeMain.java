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
 * @Description: 装箱通知书主表
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
@ApiModel(value="zm_packing_notice_main对象", description="装箱通知书主表")
@Data
@TableName("zm_packing_notice_main")
public class ZmPackingNoticeMain implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**装柜地址*/
	@Excel(name = "装柜地址", width = 15)
    @ApiModelProperty(value = "装柜地址")
    private String packingAddress;
	/**装柜日期*/
	@Excel(name = "装柜日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "装柜日期")
    private Date packingDate;
	/**订舱单*/
	@Excel(name = "订舱单", width = 15)
    @ApiModelProperty(value = "订舱单")
    private String shippingOrder;
	/**柜号*/
	@Excel(name = "柜号", width = 15)
    @ApiModelProperty(value = "柜号")
    private String cabinetNumber;
	/**封条*/
	@Excel(name = "封条", width = 15)
    @ApiModelProperty(value = "封条")
    private String seal;
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
