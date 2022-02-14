package org.jeecg.modules.demo.zmexpress.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmDeliveryStatistics.java
 * @Description 运单统计对象
 * @createTime 2022年01月02日 00:53:00
 */
@Data
@ApiModel(value="运单统计", description="运单表")
public class ZmStatistics {

    private int waybillCount;

    private int caseCount;

    private double weight;

    private double volumeWeight;

    private double volume;

}
