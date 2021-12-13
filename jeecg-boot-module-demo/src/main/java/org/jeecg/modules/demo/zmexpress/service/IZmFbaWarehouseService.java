package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouseDetail;
import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 地址库
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
public interface IZmFbaWarehouseService extends IService<ZmFbaWarehouse> {

	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);


}
