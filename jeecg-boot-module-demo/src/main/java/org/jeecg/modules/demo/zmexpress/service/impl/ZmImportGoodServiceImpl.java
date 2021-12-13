package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.mapper.ZmImportGoodMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmImportGoodService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-12-13
 * @Version: V1.0
 */
@Service
public class ZmImportGoodServiceImpl extends ServiceImpl<ZmImportGoodMapper, ZmImportGood> implements IZmImportGoodService {
	
	@Autowired
	private ZmImportGoodMapper zmImportGoodMapper;
	
	@Override
	public List<ZmImportGood> selectByMainId(String mainId) {
		return zmImportGoodMapper.selectByMainId(mainId);
	}
}
