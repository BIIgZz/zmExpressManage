package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouseDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 地址详情
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
public interface IZmFbaWarehouseDetailService extends IService<ZmFbaWarehouseDetail> {

	public List<ZmFbaWarehouseDetail> selectByMainId(String mainId);
}
