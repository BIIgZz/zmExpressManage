package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 装箱通知书
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
public interface IZmPackingNoticeService extends IService<ZmPackingNotice> {

	public List<ZmPackingNotice> selectByMainId(String mainId);
}
