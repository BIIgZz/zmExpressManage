package org.jeecg.modules.demo.zmexpress.mapper;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientFinance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 财务数据
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
public interface ZmClientFinanceMapper extends BaseMapper<ZmClientFinance> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ZmClientFinance> selectByMainId(@Param("mainId") String mainId);

}
