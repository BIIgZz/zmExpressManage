package org.jeecg.modules.demo.zmexpress.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmDeliveryWaybill.java
 * @Description 出货单中的运单列表信息
 * @createTime 2022年01月17日 20:56:00
 */
@Data
public class ZmDeliveryWaybill {

    private String id;

    private String username ;

    private String waybillId;

    private String service;

    private String pieces;

    private double volume;

    private double weightActual;

    private String recipientCountry;

    private String carrier;

    private Date createTime;
}
