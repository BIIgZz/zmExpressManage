package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmProduct;
import org.jeecg.modules.demo.zmexpress.mapper.ZmProductMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmProductService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 产品列表
 * @Author: jeecg-boot
 * @Date:   2021-07-27
 * @Version: V1.0
 */
@Service
public class ZmProductServiceImpl extends ServiceImpl<ZmProductMapper, ZmProduct> implements IZmProductService {

}
