package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmWaybill;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.mapper.ZmImportGoodMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmWaybillMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmWaybillService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 运单表
 * @Author: jeecg-boot
 * @Date:   2021-12-13
 * @Version: V1.0
 */
@Service
public class ZmWaybillServiceImpl extends ServiceImpl<ZmWaybillMapper, ZmWaybill> implements IZmWaybillService {

	@Autowired
	private ZmWaybillMapper zmWaybillMapper;
	@Autowired
	private ZmImportGoodMapper zmImportGoodMapper;
	
	@Override
	@Transactional
	public void saveMain(ZmWaybill zmWaybill, List<ZmImportGood> zmImportGoodList) {
		zmWaybillMapper.insert(zmWaybill);
		if(zmImportGoodList!=null && zmImportGoodList.size()>0) {
			for(ZmImportGood entity:zmImportGoodList) {
				//外键设置
				entity.setFbaid(zmWaybill.getId());
				zmImportGoodMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ZmWaybill zmWaybill,List<ZmImportGood> zmImportGoodList) {
		zmWaybillMapper.updateById(zmWaybill);
		
		//1.先删除子表数据
		zmImportGoodMapper.deleteByMainId(zmWaybill.getId());
		
		//2.子表数据重新插入
		if(zmImportGoodList!=null && zmImportGoodList.size()>0) {
			for(ZmImportGood entity:zmImportGoodList) {
				//外键设置
				entity.setFbaid(zmWaybill.getId());
				zmImportGoodMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		zmImportGoodMapper.deleteByMainId(id);
		zmWaybillMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			zmImportGoodMapper.deleteByMainId(id.toString());
			zmWaybillMapper.deleteById(id);
		}
	}
	
}
