package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
public interface IZmGoodCaseService extends IService<ZmGoodCase> {

	public List<ZmGoodCase> selectByMainId(String mainId);


}
