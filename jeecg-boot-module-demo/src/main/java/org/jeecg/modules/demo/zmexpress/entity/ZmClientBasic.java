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
 * @Description: 用户基础信息
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
@Data
@TableName("zm_client_basic")
@ApiModel(value="zm_client_basic对象", description="用户基础信息")
public class ZmClientBasic implements Serializable {
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
	/**用户编号*/
    @ApiModelProperty(value = "用户编号")
    private String codeId;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private String username;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
    @ApiModelProperty(value = "昵称")
    private String nickname;
	/**结算方式*/
	@Excel(name = "结算方式", width = 15)
    @ApiModelProperty(value = "结算方式")
    private String pay;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @Dict(dicCode = "status")
    @ApiModelProperty(value = "状态")
    private String status;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String linkman;
	/**公司*/
	@Excel(name = "公司", width = 15)
    @ApiModelProperty(value = "公司")
    private String company;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
    private String email;
	/**手机*/
	@Excel(name = "手机", width = 15)
    @ApiModelProperty(value = "手机")
    private String phone;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private String tel;
	/**QQ*/
	@Excel(name = "QQ", width = 15)
    @ApiModelProperty(value = "QQ")
    private String qq;
	/**客户等级*/
	@Excel(name = "客户等级", width = 15)
    @ApiModelProperty(value = "客户等级")
    private String level;
	/**财务代表*/
	@Excel(name = "财务代表", width = 15)
    @ApiModelProperty(value = "财务代表")
    private String financialRepresentative;
	/**客服代表*/
	@Excel(name = "客服代表", width = 15)
    @ApiModelProperty(value = "客服代表")
    private String customsService;
	/**销售代表*/
	@Excel(name = "销售代表", width = 15)
    @ApiModelProperty(value = "销售代表")
    private String saleRepresentative;
	/**销售来源*/
	@Excel(name = "销售来源", width = 15)
    @ApiModelProperty(value = "销售来源")
    private String salesSource;
	/**分公司*/
	@Excel(name = "分公司", width = 15)
    @ApiModelProperty(value = "分公司")
    private String branchOffice;
	/**收货区域*/
	@Excel(name = "收货区域", width = 15)
    @Dict(dicCode = "area_name",dicText = "area_name",dictTable = "zm_area_receiving")
    @ApiModelProperty(value = "收货区域")
    private String area;
	/**站点*/
	@Excel(name = "站点", width = 15)
    @ApiModelProperty(value = "站点")
    private String station;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String note;
}
