package org.jeecg.modules.demo.zmexpress.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.list.SynchronizedList;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.zmexpress.entity.ZmBillloading;
import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybill;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybills;
import org.jeecg.modules.demo.zmexpress.service.IZmWaybillsService;
import org.jeecg.modules.demo.zmexpress.service.impl.ZmBillloadingServiceImpl;
import org.jeecg.modules.demo.zmexpress.service.impl.ZmGoodCaseServiceImpl;
import org.jeecg.modules.demo.zmexpress.service.impl.ZmWaybillServiceImpl;
import org.jeecg.modules.demo.zmexpress.service.impl.ZmWaybillsServiceImpl;
import org.jeecg.modules.demo.zmexpress.vo.ZmCaseDetail;
import org.jeecg.modules.demo.zmexpress.vo.ZmCaseKind;
import org.jeecg.modules.demo.zmexpress.vo.ZmCasePack;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmGoodCaseController.java
 * @Description 处理货箱数据
 * @createTime 2021年12月31日 23:33:00
 */
@Api(tags="货箱")
@RestController
@RequestMapping("/zmexpress/zmGoodCase")
@Slf4j
public class ZmGoodCaseController {
    @Autowired
    private ZmGoodCaseServiceImpl zmGoodCaseService;
    @Autowired
    private ZmWaybillsServiceImpl zmWaybillsService;
    @Autowired
    private ZmBillloadingServiceImpl zmBillloadingService;


    /**
     * @title updateData
     * @description  更新货箱数据
     * @author zzh
     * @param: zmGoodCases
     * @updateTime 2021/12/31 23:39
     * @return: org.jeecg.common.api.vo.Result<?>
     * @throws
     */
    @PutMapping("/updateData")
    public Result<?> updateData(@RequestBody List<ZmGoodCase> zmGoodCases){
        QueryWrapper<ZmWaybills> zmWaybillsQueryWrapper = new QueryWrapper<>();
        ZmWaybills zmWaybills = zmWaybillsService.getById(zmGoodCases.get(0).getFbaid());
        for (ZmGoodCase zmGoodCase : zmGoodCases) {
            zmWaybills.setStatus("已收货");
            zmWaybillsService.updateById(zmWaybills);
            zmGoodCaseService.updateById(zmGoodCase);
        }
        return Result.OK("货箱数据更新成功");
    }


    /**
     * @title getCaseListByWayBillId
     * @description 获取货箱列表
     * @author zzh
     * @param: id
     * @updateTime 2022/1/5 19:15
     * @return: org.jeecg.common.api.vo.Result<?>
     * @throws
     */
    @GetMapping("/getCaseListByWayBillId")
    public Result<?> getCaseListByWayBillId(@RequestParam(value = "id")String id){

        //构建货箱查询条件
        QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
        zmGoodCaseQueryWrapper.eq("fbaid",id);
        List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);

        //取出该货箱的运单
        ZmWaybills zmWaybills = zmWaybillsService.getById(id);
        List<ZmCaseDetail> zmCaseDetails = new ArrayList<>();

        //取出提单
        ZmBillloading billloading = zmBillloadingService.getById(zmWaybills.getBillId());

        String pickData ;
        String customerData;
        String carrierData;
        for (ZmGoodCase zmGoodCase : zmGoodCases) {
            ZmCaseDetail zmCaseDetail = new ZmCaseDetail();
            zmCaseDetail.setCaseId(zmGoodCase.getWaybillId()+"</br>"+zmGoodCase.getCaseid());
            customerData = zmGoodCase.getWeight()+"(kg)</br>"+zmGoodCase.getLength()+"*"+
                    zmGoodCase.getWidth()+"*"+zmGoodCase.getHeight()+"(cm)";
            pickData = zmGoodCase.getWeightActually()+"/"+zmGoodCase.getWeightVolume()+"(kg)</br>"+
                    zmGoodCase.getLengthActually()+"*"+zmGoodCase.getWidthActually()+"*"+zmGoodCase.getHeightActually()+"(cm)";
            carrierData = zmWaybills.getCarrier()+"</br>"+zmWaybills.getTrackingNumber();
            zmCaseDetail.setCustomerData(customerData);
            zmCaseDetail.setPickData(pickData);
            zmCaseDetail.setCarrier(carrierData);
//            if (billloading!=null)
//              zmCaseDetail.setLadingId(billloading.getBillnum());
            zmCaseDetails.add(zmCaseDetail);
        }
        return Result.OK(zmCaseDetails);
    }

    /**
     * @title getPackListByWayBillId
     * @description 装箱单
     * @author zzh
     * @param: id
     * @updateTime 2022/1/6 0:43
     * @return: org.jeecg.common.api.vo.Result<?>
     * @throws
     */
    @GetMapping("/getPackListByWayBillId")
    public Result<?> getPackListByWayBillId(@RequestParam(value = "id")String id){

        //构建货箱查询条件
        QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
        zmGoodCaseQueryWrapper.eq("fbaid",id);
        List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);

        //取出该货箱的运单
        ZmWaybills zmWaybills = zmWaybillsService.getById(id);
        List<ZmCasePack> zmCaseDetails = new ArrayList<>();

        //取出提单
        ZmBillloading billloading = zmBillloadingService.getById(zmWaybills.getBillId());

        for (ZmGoodCase zmGoodCase : zmGoodCases) {
            ZmCasePack zmCaseDetail = new ZmCasePack();
            BeanUtils.copyProperties(zmGoodCase,zmCaseDetail);
            zmCaseDetail.setCaseId(zmGoodCase.getWaybillId()+"</br>"+zmGoodCase.getCaseid());
            zmCaseDetails.add(zmCaseDetail);
        }
        return Result.OK(zmCaseDetails);
    }

    /**
     * @title getKindListByWayBillId
     * @description 获取商品分类统计信息
     * @author zzh
     * @param: id
     * @updateTime 2022/1/5 23:41
     * @return: org.jeecg.common.api.vo.Result<?>
     * @throws
     */
    @GetMapping("/getKindListByWayBillId")
    public Result<?> getKindListByWayBillId(@RequestParam(value = "id")String id){
        //构建货箱查询条件
        QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
        zmGoodCaseQueryWrapper.eq("fbaid",id);
        List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);

        //取出该货箱的运单
        ZmWaybills zmWaybills = zmWaybillsService.getById(id);
        List<ZmCaseKind> zmCaseKinds = new ArrayList<>();

        Map<String,ZmCaseKind> zmCaseKindMap = new HashMap<>();

        for (ZmGoodCase zmGoodCase : zmGoodCases) {

            if (zmCaseKindMap.containsKey(zmGoodCase.getCnName())){
                ZmCaseKind zmCaseKind=zmCaseKindMap.get(zmGoodCase.getCnName());
                int number = zmCaseKind.getDeclaredNumber()+zmGoodCase.getDeclaredNumber();
                zmCaseKind.setDeclaredNumber(number);
                zmCaseKindMap.put(zmGoodCase.getCnName(),zmCaseKind);
            }else{
                ZmCaseKind zmCaseKind = new ZmCaseKind();
                BeanUtils.copyProperties(zmGoodCase,zmCaseKind);
                zmCaseKindMap.put(zmGoodCase.getCnName(),zmCaseKind);
            }
        }
        Iterator<String> iterator = zmCaseKindMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            ZmCaseKind value = zmCaseKindMap.get(key);
            zmCaseKinds.add(value);
        }
        return Result.OK(zmCaseKinds);
    }
}
