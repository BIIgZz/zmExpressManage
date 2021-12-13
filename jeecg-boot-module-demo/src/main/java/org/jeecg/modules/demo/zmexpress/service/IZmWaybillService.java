package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybill;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 运单表
 * @Author: jeecg-boot
 * @Date:   2021-12-13
 * @Version: V1.0
 */
public interface IZmWaybillService extends IService<ZmWaybill> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(ZmWaybill zmWaybill,List<ZmImportGood> zmImportGoodList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ZmWaybill zmWaybill,List<ZmImportGood> zmImportGoodList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
