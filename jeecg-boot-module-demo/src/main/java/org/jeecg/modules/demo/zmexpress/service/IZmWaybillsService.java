package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybills;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 运单全表
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
public interface IZmWaybillsService extends IService<ZmWaybills> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(ZmWaybills zmWaybills,List<ZmGoodCase> zmGoodCaseList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ZmWaybills zmWaybills,List<ZmGoodCase> zmGoodCaseList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
