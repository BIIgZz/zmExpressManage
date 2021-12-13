package org.jeecg.modules.demo.zmexpress.entity;

/**
 * @version 1.0.0
 * @ClassName ZmProductExport
 * @description: 商品导出
 * @author: zzh
 * @create: 2021年08月13日 15:39:00
 **/

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
 * @Description: 产品列表
 * @Author: jeecg-boot
 * @Date:   2021-07-27
 * @Version: V1.0
 */
@Data
@TableName("zm_product")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zm_product对象", description="产品列表")
public class ZmProductExport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
    /**中文名称*/
    @Excel(name = "中文名称", width = 15)
    @ApiModelProperty(value = "中文名称")
    private String cnName;
    /**英文名称*/
    @Excel(name = "英文名称", width = 15)
    @ApiModelProperty(value = "英文名称")
    private String enName;
    /**申报单价*/
    @Excel(name = "申报单价", width = 15)
    @ApiModelProperty(value = "申报单价")
    private Double declaredPrice;
    /**产品海关编码*/
    @Excel(name = "产品海关编码", width = 15)
    @ApiModelProperty(value = "产品海关编码")
    private String hscode;
    /**产品材料*/
    @Excel(name = "产品材料", width = 15)
    @ApiModelProperty(value = "产品材料")
    private String material;
    /**产品用途*/
    @Excel(name = "产品用途", width = 15)
    @ApiModelProperty(value = "产品用途")
    private String application;
    /**品牌*/
    @Excel(name = "品牌", width = 15)
    @ApiModelProperty(value = "品牌")
    private String brand;
    /**品牌类型*/
    @Excel(name = "品牌类型", width = 15, dicCode = "type")
    @Dict(dicCode = "type")
    @ApiModelProperty(value = "品牌类型")
    private Integer type;
    /**产品型号*/
    @Excel(name = "产品型号", width = 15)
    @ApiModelProperty(value = "产品型号")
    private String model;
    /**销售链接*/
    @Excel(name = "销售链接", width = 15)
    @ApiModelProperty(value = "销售链接")
    private String link;
    /**产品售价*/
    @Excel(name = "产品售价", width = 15)
    @ApiModelProperty(value = "产品售价")
    private Double price;
    /**图片链接*/
    @Excel(name = "图片链接", width = 15 , height = 20,imageType = 2,type = 2)
    @ApiModelProperty(value = "图片链接")
    private byte[] picture;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**修改日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改日期")
    private Date updateTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
}

