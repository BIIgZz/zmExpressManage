package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmClientFinance;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 财务数据
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
public interface IZmClientFinanceService extends IService<ZmClientFinance> {

	public List<ZmClientFinance> selectByMainId(String mainId);
}
