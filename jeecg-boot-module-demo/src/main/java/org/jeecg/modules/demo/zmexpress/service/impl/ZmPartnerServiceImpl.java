package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmPartner;
import org.jeecg.modules.demo.zmexpress.mapper.ZmPartnerMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmPartnerService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Service
public class ZmPartnerServiceImpl extends ServiceImpl<ZmPartnerMapper, ZmPartner> implements IZmPartnerService {
	
	@Autowired
	private ZmPartnerMapper zmPartnerMapper;
	
	@Override
	public List<ZmPartner> selectByMainId(String mainId) {
		return zmPartnerMapper.selectByMainId(mainId);
	}
}
