package org.jeecg.modules.demo.zmexpress.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmToStatus.java
 * @Description 更改状态对象
 * @createTime 2021年12月23日 15:17:00
 */
@Data
public class ZmToStatus {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String ids;
    /**切换成啥对象*/
    @ApiModelProperty(value = "切换成啥对象")
    private String status;

}
