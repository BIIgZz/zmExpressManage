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
 * @Description: hscode(已导出)
 * @Author: jeecg-boot
 * @Date:   2022-01-06
 * @Version: V1.0
 */
@Data
@TableName("zm_hscode")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_hscode对象", description="hscode(已导出)")
public class ZmHscode implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**海关编码*/
	@Excel(name = "海关编码", width = 15)
    @ApiModelProperty(value = "海关编码")
    private java.lang.String hscode;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String description;
	/**材质*/
	@Excel(name = "材质", width = 15)
    @ApiModelProperty(value = "材质")
    private java.lang.String material;
	/**原始名称*/
	@Excel(name = "原始名称", width = 15)
    @ApiModelProperty(value = "原始名称")
    private java.lang.String originalName;
	/**建议名称*/
	@Excel(name = "建议名称", width = 15)
    @ApiModelProperty(value = "建议名称")
    private java.lang.String suggestedName;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.lang.String rateTax;
	/** 创建人*/
    @ApiModelProperty(value = " 创建人")
    private java.lang.String createBy;
	/**修改日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改日期")
    private java.util.Date updateTime;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**美国海关编码*/
	@Excel(name = "美国海关编码", width = 15)
    @ApiModelProperty(value = "美国海关编码")
    private java.lang.String hscodeUsa;
	/**美国税率*/
	@Excel(name = "美国税率", width = 15)
    @ApiModelProperty(value = "美国税率")
    private java.lang.String taxUsa;
	/**美国建议名称*/
	@Excel(name = "美国建议名称", width = 15)
    @ApiModelProperty(value = "美国建议名称")
    private java.lang.String nameUsa;
	/**增税*/
	@Excel(name = "增税", width = 15)
    @ApiModelProperty(value = "增税")
    private java.lang.String raiseTaxes;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
}
