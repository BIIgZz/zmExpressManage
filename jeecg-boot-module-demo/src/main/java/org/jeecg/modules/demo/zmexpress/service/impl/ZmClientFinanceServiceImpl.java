package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmClientFinance;
import org.jeecg.modules.demo.zmexpress.mapper.ZmClientFinanceMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmClientFinanceService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 财务数据
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
@Service
public class ZmClientFinanceServiceImpl extends ServiceImpl<ZmClientFinanceMapper, ZmClientFinance> implements IZmClientFinanceService {
	
	@Autowired
	private ZmClientFinanceMapper zmClientFinanceMapper;
	
	@Override
	public List<ZmClientFinance> selectByMainId(String mainId) {
		return zmClientFinanceMapper.selectByMainId(mainId);
	}
}
