package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
public interface ZmGoodCaseMapper extends BaseMapper<ZmGoodCase> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmGoodCase> selectByMainId(@Param("mainId") String mainId);
}
