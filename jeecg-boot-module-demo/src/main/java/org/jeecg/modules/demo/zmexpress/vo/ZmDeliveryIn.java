package org.jeecg.modules.demo.zmexpress.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmDeliveryIn.java
 * @Description 通过箱号或者运单号出货
 * @createTime 2022年01月04日 15:46:00
 */
@Data
@ApiModel(value="运单统计", description="运单表")
public class ZmDeliveryIn {

    //货箱或者运单id
    private  String[] ids;

    //出货单id
    private String id;

    //货箱id还是运单id
    private String type;
}
