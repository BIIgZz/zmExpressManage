package org.jeecg.modules.demo.zmexpress.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmReceipt.java
 * @Description 接收前端传入加入提单、取货单的对象
 * @createTime 2021年12月23日 10:04:00
 */
@Data
public class ZmReceipt {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String ids;

    /**单据id*/
    @ApiModelProperty(value = "单据id")
    private String id;
}
