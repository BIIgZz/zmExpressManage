package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmPartner;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
public interface IZmPartnerService extends IService<ZmPartner> {

	public List<ZmPartner> selectByMainId(String mainId);
}
