package org.jeecg.modules.demo.zmexpress.rules;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.math.RandomUtils;
import org.jeecg.common.handler.IFillRuleHandler;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybill;
import org.jeecg.modules.demo.zmexpress.mapper.ZmWaybillMapper;
import org.jeecg.modules.demo.zmexpress.service.*;
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
    private IZmWaybillsService zmWaybillsService;
    @Autowired
    private IZmDeliveryOrderService deliveryOrderService;

    @Autowired
    private IZmClientMainService zmClientMainService;

    public static  ShopOrderRule shopOrderRule;
    @PostConstruct
    public void init() {
        shopOrderRule = this;
    }
    @Override
    public Object execute(JSONObject params, JSONObject formData) {
        System.out.println(params);
        System.out.println(formData);
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

        int count = shopOrderRule.zmWaybillsService.count();
        int orderNum = 100000+count+1;
        String value = prefix + orderNum;
        // 根据formData的值的不同，生成不同的订单号
//        String name = formData.getString("name");
//        if (!StringUtils.isEmpty(name)) {
//            value += name;
//        }
        return value;
    }

    public  String  autoOderid(String params) {

        String prefix = "ZMMD";
        //订单前缀默认为CN 如果规则参数不为空，则取自定义前缀
        if (params != "") {
                prefix = params;
        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        int random = RandomUtils.nextInt(90) + 10;
//        String value = prefix + format.format(new Date()) + random;

        int count = shopOrderRule.zmWaybillsService.count();
        int orderNum = 100000+count+1;
        String value = prefix + orderNum;
        // 根据formData的值的不同，生成不同的订单号
//        String name = formData.getString("name");
//        if (!StringUtils.isEmpty(name)) {
//            value += name;
//        }
        return value;
    }

    /**
     *
     * 获取拣货单编号
     *
     */
    public String autoPickNo() {
        int count = shopOrderRule.deliveryOrderService.count();
        int orderNum = 100000+count+1;
        String prefix = "ZMMD";
        String value =prefix.concat(String.valueOf(orderNum)) ;
        return value;
    }

    /** 获取用户编号
     *
     */
    public  String autoUserNo() {
        int count = shopOrderRule.zmClientMainService.count();
        int orderNum = count+1;
        String prefix = "1";
        String value =prefix.concat(String.format("%06d",orderNum)) ;
        return value;
    }
}
