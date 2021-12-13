package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouseDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 地址详情
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
public interface ZmFbaWarehouseDetailMapper extends BaseMapper<ZmFbaWarehouseDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmFbaWarehouseDetail> selectByMainId(@Param("mainId") String mainId);

}
