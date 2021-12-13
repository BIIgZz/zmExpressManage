package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmPartner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
public interface ZmPartnerMapper extends BaseMapper<ZmPartner> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmPartner> selectByMainId(@Param("mainId") String mainId);

}
