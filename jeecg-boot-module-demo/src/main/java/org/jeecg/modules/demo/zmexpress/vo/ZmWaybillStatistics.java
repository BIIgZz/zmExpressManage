package org.jeecg.modules.demo.zmexpress.vo;

import lombok.Data;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmWaybillStatistics.java
 * @Description TODO
 * @createTime 2022年01月06日 12:43:00
 */
@Data
public class ZmWaybillStatistics {
    private double weightCharge;

    private double weight;

    private double volumnWeight;

    private String foamingFactor;

    private double volume;

    private int pieces;

    private double bubbleSplittingRatio;

    private double minBoxWeight;

    private int minTicketChargeWeight;
}
