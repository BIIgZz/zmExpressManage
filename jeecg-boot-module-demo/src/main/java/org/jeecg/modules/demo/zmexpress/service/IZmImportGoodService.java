package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-12-13
 * @Version: V1.0
 */
public interface IZmImportGoodService extends IService<ZmImportGood> {

	public List<ZmImportGood> selectByMainId(String mainId);
}
