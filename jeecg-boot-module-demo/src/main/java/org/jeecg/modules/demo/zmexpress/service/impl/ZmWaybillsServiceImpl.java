package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmWaybills;
import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import org.jeecg.modules.demo.zmexpress.mapper.ZmGoodCaseMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmWaybillsMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmWaybillsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 运单全表
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@Service
public class ZmWaybillsServiceImpl extends ServiceImpl<ZmWaybillsMapper, ZmWaybills> implements IZmWaybillsService {

	@Autowired
	private ZmWaybillsMapper zmWaybillsMapper;
	@Autowired
	private ZmGoodCaseMapper zmGoodCaseMapper;
	
	@Override
	@Transactional
	public void saveMain(ZmWaybills zmWaybills, List<ZmGoodCase> zmGoodCaseList) {
		zmWaybillsMapper.insert(zmWaybills);
		if(zmGoodCaseList!=null && zmGoodCaseList.size()>0) {
			for(ZmGoodCase entity:zmGoodCaseList) {
				//外键设置
				entity.setFbaid(zmWaybills.getId());
				zmGoodCaseMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ZmWaybills zmWaybills,List<ZmGoodCase> zmGoodCaseList) {
		zmWaybillsMapper.updateById(zmWaybills);
		
		//1.先删除子表数据
		zmGoodCaseMapper.deleteByMainId(zmWaybills.getId());
		
		//2.子表数据重新插入
		if(zmGoodCaseList!=null && zmGoodCaseList.size()>0) {
			for(ZmGoodCase entity:zmGoodCaseList) {
				//外键设置
				entity.setFbaid(zmWaybills.getId());
				zmGoodCaseMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		zmGoodCaseMapper.deleteByMainId(id);
		zmWaybillsMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			zmGoodCaseMapper.deleteByMainId(id.toString());
			zmWaybillsMapper.deleteById(id);
		}
	}
	
}
