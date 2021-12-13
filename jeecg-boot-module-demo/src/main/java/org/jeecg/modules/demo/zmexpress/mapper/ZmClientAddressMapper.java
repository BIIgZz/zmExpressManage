package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 用户地址
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
public interface ZmClientAddressMapper extends BaseMapper<ZmClientAddress> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmClientAddress> selectByMainId(@Param("mainId") String mainId);

}
