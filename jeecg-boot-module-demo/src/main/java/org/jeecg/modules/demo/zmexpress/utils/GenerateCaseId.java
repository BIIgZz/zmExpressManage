package org.jeecg.modules.demo.zmexpress.utils;

import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;

import java.util.List;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName GenerateCaseId.java
 * @Description TODO
 * @createTime 2022年01月09日 11:14:00
 */
public class GenerateCaseId {

    public static List<ZmGoodCase> generateCaseId(int orderId,List<ZmGoodCase> zmGoodCases){
        int caseNum = 0;
        String caseId = null;
        for (ZmGoodCase zmGoodCase : zmGoodCases) {
            ZmGoodCase zmGoodCase1 = new ZmGoodCase();
            if(!zmGoodCase.getCaseid().contains("-")){
                if (caseNum<10){
                    caseId = orderId+"U00"+caseNum;
                    zmGoodCase1.setCaseid(caseId);
                    updateElement(zmGoodCases,zmGoodCase,zmGoodCase1);
                }else if (caseNum>=10&&caseNum<100) {
                    caseId = orderId + "U0" + caseNum;
                }
                caseNum++;
                continue;
            }else {
                String[] split = zmGoodCase.getCaseid().split("-");
                int start = Integer.parseInt(split[0]);
                int end = Integer.parseInt(split[1]);
                for (int i = start; i < end; i++){
                }
            }
        }

        return zmGoodCases;
    }

    /**
     * 更新list内的元素。
     * @param objList
     * @param oldObj 旧对象
     * @param newObj 要更新的对象
     * @return
     */
    public static List updateElement(List<ZmGoodCase> objList,ZmGoodCase oldObj,ZmGoodCase newObj){
        int position=objList.indexOf(oldObj);
        objList.remove(position);
        objList.add(position, newObj);

        return objList;
    }

}
