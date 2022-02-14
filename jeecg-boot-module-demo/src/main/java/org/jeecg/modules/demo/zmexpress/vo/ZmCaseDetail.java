package org.jeecg.modules.demo.zmexpress.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmCaseList.java
 * @Description 展示货箱信息过程对象
 * @createTime 2022年01月05日 18:05:00
 */
@Data
@ApiModel(value="货箱信息", description="货箱信息")
public class ZmCaseDetail {

    private String id;
    private String caseId;

    private String customerData;

    private String pickData;

    private String ladingId;

    private String carrier;
}
