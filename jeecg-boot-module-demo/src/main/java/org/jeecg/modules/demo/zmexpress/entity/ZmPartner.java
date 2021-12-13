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
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Data
@TableName("zm_partner")
@ApiModel(value="zm_partner对象", description="客户表")
public class ZmPartner implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**公司名*/
	@Excel(name = "公司名", width = 15)
    @ApiModelProperty(value = "公司名")
    private String company;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String address;
	/**公司营业执照号*/
	@Excel(name = "公司营业执照号", width = 15)
    @ApiModelProperty(value = "公司营业执照号")
    private String licenseNumber;
	/**法人身份证*/
	@Excel(name = "法人身份证", width = 15)
    @ApiModelProperty(value = "法人身份证")
    private String idCard;
	/**法人代表*/
	@Excel(name = "法人代表", width = 15)
    @ApiModelProperty(value = "法人代表")
    private String legalRepresentative;
	/**法人电话*/
	@Excel(name = "法人电话", width = 15)
    @ApiModelProperty(value = "法人电话")
    private String lrTel;
	/**业务联系人*/
	@Excel(name = "业务联系人", width = 15)
    @ApiModelProperty(value = "业务联系人")
    private String businessContact;
	/**业务联系人电话*/
	@Excel(name = "业务联系人电话", width = 15)
    @ApiModelProperty(value = "业务联系人电话")
    private String businessContactTel;
	/**操作*/
	@Excel(name = "操作", width = 15)
    @ApiModelProperty(value = "操作")
    private String operate;
	/**操作电话*/
	@Excel(name = "操作电话", width = 15)
    @ApiModelProperty(value = "操作电话")
    private String operateTel;
	/**财务*/
	@Excel(name = "财务", width = 15)
    @ApiModelProperty(value = "财务")
    private String finance;
	/**财务电话*/
	@Excel(name = "财务电话", width = 15)
    @ApiModelProperty(value = "财务电话")
    private String financeTel;
	/**财务QQ*/
	@Excel(name = "财务QQ", width = 15)
    @ApiModelProperty(value = "财务QQ")
    private String financeQq;
	/**其他*/
	@Excel(name = "其他", width = 15)
    @ApiModelProperty(value = "其他")
    private String other;
	/**营业执照附件*/
	@Excel(name = "营业执照附件", width = 15)
    @ApiModelProperty(value = "营业执照附件")
    private String licenseAttachment;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**公司类型*/
    @Dict(dicCode = "type",dicText = "type",dictTable = "zm_supplier")
    @ApiModelProperty(value = "公司类型")
    private String type;
}
