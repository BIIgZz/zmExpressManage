package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNoticeMain;
import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNotice;
import org.jeecg.modules.demo.zmexpress.mapper.ZmPackingNoticeMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmPackingNoticeMainMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmPackingNoticeMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 装箱通知书主表
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
@Service
public class ZmPackingNoticeMainServiceImpl extends ServiceImpl<ZmPackingNoticeMainMapper, ZmPackingNoticeMain> implements IZmPackingNoticeMainService {

	@Autowired
	private ZmPackingNoticeMainMapper zmPackingNoticeMainMapper;
	@Autowired
	private ZmPackingNoticeMapper zmPackingNoticeMapper;
	
	@Override
	@Transactional
	public void saveMain(ZmPackingNoticeMain zmPackingNoticeMain, List<ZmPackingNotice> zmPackingNoticeList) {
		zmPackingNoticeMainMapper.insert(zmPackingNoticeMain);
		if(zmPackingNoticeList!=null && zmPackingNoticeList.size()>0) {
			for(ZmPackingNotice entity:zmPackingNoticeList) {
				//外键设置
				entity.setShippingOrderId(zmPackingNoticeMain.getId());
				zmPackingNoticeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ZmPackingNoticeMain zmPackingNoticeMain,List<ZmPackingNotice> zmPackingNoticeList) {
		zmPackingNoticeMainMapper.updateById(zmPackingNoticeMain);
		
		//1.先删除子表数据
		zmPackingNoticeMapper.deleteByMainId(zmPackingNoticeMain.getId());
		
		//2.子表数据重新插入
		if(zmPackingNoticeList!=null && zmPackingNoticeList.size()>0) {
			for(ZmPackingNotice entity:zmPackingNoticeList) {
				//外键设置
				entity.setShippingOrderId(zmPackingNoticeMain.getId());
				zmPackingNoticeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		zmPackingNoticeMapper.deleteByMainId(id);
		zmPackingNoticeMainMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			zmPackingNoticeMapper.deleteByMainId(id.toString());
			zmPackingNoticeMainMapper.deleteById(id);
		}
	}
	
}
