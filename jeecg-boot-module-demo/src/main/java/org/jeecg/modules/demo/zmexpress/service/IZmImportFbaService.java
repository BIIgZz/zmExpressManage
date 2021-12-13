package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportFba;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 导入FBA表
 * @Author: jeecg-boot
 * @Date:   2021-07-13
 * @Version: V1.0
 */
public interface IZmImportFbaService extends IService<ZmImportFba> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(ZmImportFba zmImportFba, List<ZmImportGood> zmImportGoodList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ZmImportFba zmImportFba, List<ZmImportGood> zmImportGoodList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
