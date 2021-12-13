package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNotice;
import org.jeecg.modules.demo.zmexpress.mapper.ZmPackingNoticeMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmPackingNoticeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 装箱通知书
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
@Service
public class ZmPackingNoticeServiceImpl extends ServiceImpl<ZmPackingNoticeMapper, ZmPackingNotice> implements IZmPackingNoticeService {
	
	@Autowired
	private ZmPackingNoticeMapper zmPackingNoticeMapper;
	
	@Override
	public List<ZmPackingNotice> selectByMainId(String mainId) {
		return zmPackingNoticeMapper.selectByMainId(mainId);
	}
}
