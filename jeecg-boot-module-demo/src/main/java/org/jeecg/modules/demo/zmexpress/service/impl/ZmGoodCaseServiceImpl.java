package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import org.jeecg.modules.demo.zmexpress.mapper.ZmGoodCaseMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmGoodCaseService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@Service
public class ZmGoodCaseServiceImpl extends ServiceImpl<ZmGoodCaseMapper, ZmGoodCase> implements IZmGoodCaseService {
	
	@Autowired
	private ZmGoodCaseMapper zmGoodCaseMapper;
	
	@Override
	public List<ZmGoodCase> selectByMainId(String mainId) {
		return zmGoodCaseMapper.selectByMainId(mainId);
	}
}
