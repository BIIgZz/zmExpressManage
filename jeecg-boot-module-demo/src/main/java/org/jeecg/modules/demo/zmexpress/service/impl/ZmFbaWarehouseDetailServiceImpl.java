package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouseDetail;
import org.jeecg.modules.demo.zmexpress.mapper.ZmFbaWarehouseDetailMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmFbaWarehouseDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 地址详情
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
@Service
public class ZmFbaWarehouseDetailServiceImpl extends ServiceImpl<ZmFbaWarehouseDetailMapper, ZmFbaWarehouseDetail> implements IZmFbaWarehouseDetailService {
	
	@Autowired
	private ZmFbaWarehouseDetailMapper zmFbaWarehouseDetailMapper;
	
	@Override
	public List<ZmFbaWarehouseDetail> selectByMainId(String mainId) {
		return zmFbaWarehouseDetailMapper.selectByMainId(mainId);
	}
}
