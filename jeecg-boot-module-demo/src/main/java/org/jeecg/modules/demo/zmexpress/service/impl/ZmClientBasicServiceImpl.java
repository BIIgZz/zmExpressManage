package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmClientBasic;
import org.jeecg.modules.demo.zmexpress.mapper.ZmClientBasicMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmClientBasicService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 用户基础信息
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
@Service
public class ZmClientBasicServiceImpl extends ServiceImpl<ZmClientBasicMapper, ZmClientBasic> implements IZmClientBasicService {
	
	@Autowired
	private ZmClientBasicMapper zmClientBasicMapper;
	
	@Override
	public List<ZmClientBasic> selectByMainId(String mainId) {
		return zmClientBasicMapper.selectByMainId(mainId);
	}
}
