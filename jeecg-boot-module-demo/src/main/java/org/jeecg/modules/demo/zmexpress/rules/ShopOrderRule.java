package org.jeecg.modules.demo.zmexpress.rules;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.math.RandomUtils;
import org.jeecg.common.handler.IFillRuleHandler;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybill;
import org.jeecg.modules.demo.zmexpress.mapper.ZmWaybillMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmWaybillService;
import org.jeecg.modules.demo.zmexpress.service.impl.ZmWaybillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Component
public class ShopOrderRule implements IFillRuleHandler {
    @Autowired
    private IZmWaybillService zmWaybillService;

    public static  ShopOrderRule shopOrderRule;
    @PostConstruct
    public void init() {
        shopOrderRule = this;
    }
    @Override
    public Object execute(JSONObject params, JSONObject formData) {
        String prefix = "ZMMD";
        //订单前缀默认为CN 如果规则参数不为空，则取自定义前缀
        if (params != null) {
            Object obj = params.get("prefix");
            if (obj != null) {
                prefix = obj.toString();
            }
        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        int random = RandomUtils.nextInt(90) + 10;
//        String value = prefix + format.format(new Date()) + random;

        int count = shopOrderRule.zmWaybillService.count();
        int orderNum = 100000+count;
        String value = prefix + orderNum;
        // 根据formData的值的不同，生成不同的订单号
//        String name = formData.getString("name");
//        if (!StringUtils.isEmpty(name)) {
//            value += name;
//        }
        return value;
    }
}
