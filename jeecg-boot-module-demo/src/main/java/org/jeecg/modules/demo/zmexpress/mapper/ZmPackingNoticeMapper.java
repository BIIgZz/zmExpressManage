package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 装箱通知书
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
public interface ZmPackingNoticeMapper extends BaseMapper<ZmPackingNotice> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmPackingNotice> selectByMainId(@Param("mainId") String mainId);
}
