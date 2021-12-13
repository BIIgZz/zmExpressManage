package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmClientAddress;
import org.jeecg.modules.demo.zmexpress.mapper.ZmClientAddressMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmClientAddressService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 用户地址
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
@Service
public class ZmClientAddressServiceImpl extends ServiceImpl<ZmClientAddressMapper, ZmClientAddress> implements IZmClientAddressService {
	
	@Autowired
	private ZmClientAddressMapper zmClientAddressMapper;
	
	@Override
	public List<ZmClientAddress> selectByMainId(String mainId) {
		return zmClientAddressMapper.selectByMainId(mainId);
	}
}
