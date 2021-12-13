package org.jeecg.modules.demo.zmexpress.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 地址详情
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
@Data
@TableName("zm_fba_warehouse_detail")
@ApiModel(value="zm_fba_warehouse_detail对象", description="地址详情")
public class ZmFbaWarehouseDetail implements Serializable {
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
	/**地址简称*/
	@Excel(name = "地址简称", width = 15)
    @ApiModelProperty(value = "地址简称")
    private String simpleName;
	/**地址编码*/
	@Excel(name = "地址编码", width = 15)
    @ApiModelProperty(value = "地址编码")
    private String code;
	/**FBA仓库代码*/
	@Excel(name = "FBA仓库代码", width = 15)
    @ApiModelProperty(value = "FBA仓库代码")
    private String fbaCode;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String linkman;
	/**公司名*/
	@Excel(name = "公司名", width = 15)
    @ApiModelProperty(value = "公司名")
    private String company;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private String tel;
	/**联系手机*/
	@Excel(name = "联系手机", width = 15)
    @ApiModelProperty(value = "联系手机")
    private String phone;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
    private String email;
	/**地址一*/
	@Excel(name = "地址一", width = 15)
    @ApiModelProperty(value = "地址一")
    private String address1;
	/**地址二*/
	@Excel(name = "地址二", width = 15)
    @ApiModelProperty(value = "地址二")
    private String addres2;
	/**地址三*/
	@Excel(name = "地址三", width = 15)
    @ApiModelProperty(value = "地址三")
    private String address3;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private String city;
	/**省/州*/
	@Excel(name = "省/州", width = 15)
    @ApiModelProperty(value = "省/州")
    private String state;
	/**国家*/
	@Excel(name = "国家", width = 15)
    @ApiModelProperty(value = "国家")
    private String country;
	/**邮编*/
	@Excel(name = "邮编", width = 15)
    @ApiModelProperty(value = "邮编")
    private String postCode;
	/**id*/
    @ApiModelProperty(value = "id")
    private String fbaWarehouseId;
}
