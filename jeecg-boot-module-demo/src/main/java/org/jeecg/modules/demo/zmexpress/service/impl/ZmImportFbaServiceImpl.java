package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmImportFba;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.mapper.ZmImportGoodMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmImportFbaMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmImportFbaService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 导入FBA表
 * @Author: jeecg-boot
 * @Date:   2021-07-13
 * @Version: V1.0
 */
@Service
public class ZmImportFbaServiceImpl extends ServiceImpl<ZmImportFbaMapper, ZmImportFba> implements IZmImportFbaService {

	@Autowired
	private ZmImportFbaMapper zmImportFbaMapper;
	@Autowired
	private ZmImportGoodMapper zmImportGoodMapper;
	
	@Override
	@Transactional
	public void saveMain(ZmImportFba zmImportFba, List<ZmImportGood> zmImportGoodList) {
		zmImportFbaMapper.insert(zmImportFba);
		if(zmImportGoodList!=null && zmImportGoodList.size()>0) {
			for(ZmImportGood entity:zmImportGoodList) {
				//外键设置
				entity.setFbaid(zmImportFba.getId());
				zmImportGoodMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ZmImportFba zmImportFba,List<ZmImportGood> zmImportGoodList) {
		zmImportFbaMapper.updateById(zmImportFba);
		
		//1.先删除子表数据
		zmImportGoodMapper.deleteByMainId(zmImportFba.getId());
		
		//2.子表数据重新插入
		if(zmImportGoodList!=null && zmImportGoodList.size()>0) {
			for(ZmImportGood entity:zmImportGoodList) {
				//外键设置
				entity.setFbaid(zmImportFba.getId());
				zmImportGoodMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		zmImportGoodMapper.deleteByMainId(id);
		zmImportFbaMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			zmImportGoodMapper.deleteByMainId(id.toString());
			zmImportFbaMapper.deleteById(id);
		}
	}
	
}
