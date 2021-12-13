package org.jeecg.modules.demo.zmexpress.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.zmexpress.entity.ZmHscode;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmProduct;
import org.jeecg.modules.demo.zmexpress.service.IZmHscodeService;
import org.jeecg.modules.demo.zmexpress.service.IZmProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @ClassName ProductInsertUtils
 * @description: product拆解插入工具
 * @author: zzh
 * @create: 2021年07月17日 10:16:00
 **/
@Component
public class InsertUtils {


    @Autowired
    private IZmProductService zmProductService;

    @Autowired
    private IZmHscodeService zmHscodeService;


    public  boolean insertProduct(List<ZmProduct> zmProductList){
        for (ZmProduct  zmProduct: zmProductList) {
            zmProductService.save(zmProduct);
        }
       return true;
    }
    public  boolean insertHscode(List<ZmImportGood> zmImportGoods){
        ZmHscode zmHscode = new ZmHscode();
        for (ZmImportGood zmImportGood: zmImportGoods) {
            zmHscode.setId(zmImportGood.getId());
            zmHscode.setHscode(zmImportGood.getHscode());
            zmHscode.setDescription(zmImportGood.getCnName());
            zmHscode.setMaterial(zmImportGood.getMaterial());
            zmHscode.setOriginalName(zmImportGood.getCnName());
            zmHscode.setCreateBy(zmImportGood.getCreateBy());
            zmHscode.setCreateTime(zmImportGood.getCreateTime());
            zmHscode.setUpdateBy(zmImportGood.getUpdateBy());
            zmHscode.setUpdateTime(zmImportGood.getUpdateTime());
            zmHscodeService.save(zmHscode);
        }
        return true;
    }

//    /**
//     * 字符串转换为JSONObject的List
//     * @param str
//     * @return
//     */
//    public static List<JSONObject> strToList(String str) {
//        List<JSONObject> res = new ArrayList<>();
//        JSONArray json = JSONArray.fromObject(str);
//        for(int i = 0; i < json.size(); i++) {
//            res.add(json.getJSONObject(i));
//        }
//        return res;
//    }
    
}
