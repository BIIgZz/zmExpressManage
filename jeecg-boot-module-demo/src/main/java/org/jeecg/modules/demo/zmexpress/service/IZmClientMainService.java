package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmClientBasic;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientAddress;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientFinance;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientMain;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 用户主表
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
public interface IZmClientMainService extends IService<ZmClientMain> {

	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);


}
