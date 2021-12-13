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
 * @Description: 机场/港口
 * @Author: jeecg-boot
 * @Date:   2021-08-09
 * @Version: V1.0
 */
@Data
@TableName("zm_airport")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_airport对象", description="机场/港口")
public class ZmAirport implements Serializable {
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
	/**地点名称*/
	@Excel(name = "地点名称", width = 15)
    @ApiModelProperty(value = "地点名称")
    private String name;
	/**地点名称(英)*/
	@Excel(name = "地点名称(英)", width = 15)
    @ApiModelProperty(value = "地点名称(英)")
    private String enname;
	/**类型*/
	@Excel(name = "类型", width = 15, dicCode = "port_type")
	@Dict(dicCode = "port_type")
    @ApiModelProperty(value = "类型")
    private String type;
	/**地点代码*/
	@Excel(name = "地点代码", width = 15)
    @ApiModelProperty(value = "地点代码")
    private String code;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String address;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private String city;
	/**省/州*/
	@Excel(name = "省/州", width = 15)
    @ApiModelProperty(value = "省/州")
    private String state;
	/**国家*/
	@Excel(name = "国家", width = 15, dictTable = "zm_tool_countries", dicText = "cname", dicCode = "cname")
	@Dict(dictTable = "zm_tool_countries", dicText = "cname", dicCode = "cname")
    @ApiModelProperty(value = "国家")
    private String country;
	/**邮编*/
	@Excel(name = "邮编", width = 15)
    @ApiModelProperty(value = "邮编")
    private String postcode;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String note;
}
