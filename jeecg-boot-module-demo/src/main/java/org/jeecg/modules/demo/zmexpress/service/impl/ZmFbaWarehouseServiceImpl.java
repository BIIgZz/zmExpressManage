package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouse;
import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouseDetail;
import org.jeecg.modules.demo.zmexpress.mapper.ZmFbaWarehouseDetailMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmFbaWarehouseMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmFbaWarehouseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 地址库
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
@Service
public class ZmFbaWarehouseServiceImpl extends ServiceImpl<ZmFbaWarehouseMapper, ZmFbaWarehouse> implements IZmFbaWarehouseService {

	@Autowired
	private ZmFbaWarehouseMapper zmFbaWarehouseMapper;
	@Autowired
	private ZmFbaWarehouseDetailMapper zmFbaWarehouseDetailMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		zmFbaWarehouseDetailMapper.deleteByMainId(id);
		zmFbaWarehouseMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			zmFbaWarehouseDetailMapper.deleteByMainId(id.toString());
			zmFbaWarehouseMapper.deleteById(id);
		}
	}
	
}
