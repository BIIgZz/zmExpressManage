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
 * @Description: zm_tool_world_area
 * @Author: jeecg-boot
 * @Date:   2021-08-09
 * @Version: V1.0
 */
@Data
@TableName("zm_tool_world_area")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_tool_world_area对象", description="zm_tool_world_area")
public class ZmToolWorldArea implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**pid*/
	@Excel(name = "pid", width = 15)
    @ApiModelProperty(value = "pid")
    private Integer pid;
	/**地区代码*/
	@Excel(name = "地区代码", width = 15)
    @ApiModelProperty(value = "地区代码")
    private String areaCode;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**fullName*/
	@Excel(name = "fullName", width = 15)
    @ApiModelProperty(value = "fullName")
    private String fullname;
	/**cname*/
	@Excel(name = "cname", width = 15)
    @ApiModelProperty(value = "cname")
    private String cname;
	/**fullCname*/
	@Excel(name = "fullCname", width = 15)
    @ApiModelProperty(value = "fullCname")
    private String fullcname;
	/**lowerName*/
	@Excel(name = "lowerName", width = 15)
    @ApiModelProperty(value = "lowerName")
    private String lowername;
	/**remark*/
	@Excel(name = "remark", width = 15)
    @ApiModelProperty(value = "remark")
    private String remark;
}
