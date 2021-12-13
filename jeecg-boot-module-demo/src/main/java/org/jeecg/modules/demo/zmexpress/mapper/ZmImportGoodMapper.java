package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-12-13
 * @Version: V1.0
 */
public interface ZmImportGoodMapper extends BaseMapper<ZmImportGood> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmImportGood> selectByMainId(@Param("mainId") String mainId);
}
