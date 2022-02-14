package org.jeecg.modules.demo.zmexpress.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 退件理由
 */
@Data
public class ZmReason {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String ids;
    /**理由*/
    @ApiModelProperty(value = "理由")
    private String remark;
}
