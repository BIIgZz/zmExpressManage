package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmClientBasic;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 用户基础信息
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
public interface IZmClientBasicService extends IService<ZmClientBasic> {

	public List<ZmClientBasic> selectByMainId(String mainId);
}
