package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmClientMain;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientBasic;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientAddress;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientFinance;
import org.jeecg.modules.demo.zmexpress.mapper.ZmClientBasicMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmClientAddressMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmClientFinanceMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmClientMainMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmClientMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 用户主表
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
@Service
public class ZmClientMainServiceImpl extends ServiceImpl<ZmClientMainMapper, ZmClientMain> implements IZmClientMainService {

	@Autowired
	private ZmClientMainMapper zmClientMainMapper;
	@Autowired
	private ZmClientBasicMapper zmClientBasicMapper;
	@Autowired
	private ZmClientAddressMapper zmClientAddressMapper;
	@Autowired
	private ZmClientFinanceMapper zmClientFinanceMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		zmClientBasicMapper.deleteByMainId(id);
		zmClientAddressMapper.deleteByMainId(id);
		zmClientFinanceMapper.deleteByMainId(id);
		zmClientMainMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			zmClientBasicMapper.deleteByMainId(id.toString());
			zmClientAddressMapper.deleteByMainId(id.toString());
			zmClientFinanceMapper.deleteByMainId(id.toString());
			zmClientMainMapper.deleteById(id);
		}
	}
	
}
