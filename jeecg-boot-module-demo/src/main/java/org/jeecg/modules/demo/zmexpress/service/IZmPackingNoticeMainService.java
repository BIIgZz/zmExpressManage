package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNotice;
import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNoticeMain;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 装箱通知书主表
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
public interface IZmPackingNoticeMainService extends IService<ZmPackingNoticeMain> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(ZmPackingNoticeMain zmPackingNoticeMain, List<ZmPackingNotice> zmPackingNoticeList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ZmPackingNoticeMain zmPackingNoticeMain, List<ZmPackingNotice> zmPackingNoticeList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
