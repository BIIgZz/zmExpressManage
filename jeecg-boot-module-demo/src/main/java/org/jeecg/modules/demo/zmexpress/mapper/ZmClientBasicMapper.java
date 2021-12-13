package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientBasic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 用户基础信息
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
public interface ZmClientBasicMapper extends BaseMapper<ZmClientBasic> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmClientBasic> selectByMainId(@Param("mainId") String mainId);

}
