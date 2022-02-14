package org.jeecg.modules.demo.zmexpress.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmAreaReceiving;
import org.jeecg.modules.demo.zmexpress.service.IZmAreaReceivingService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 收货区域
 * @Author: jeecg-boot
 * @Date:   2021-12-14
 * @Version: V1.0
 */
@Api(tags="收货区域")
@RestController
@RequestMapping("/zmexpress/zmAreaReceiving")
@Slf4j
public class ZmAreaReceivingController extends JeecgController<ZmAreaReceiving, IZmAreaReceivingService> {
	@Autowired
	private IZmAreaReceivingService zmAreaReceivingService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmAreaReceiving
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "收货区域-分页列表查询")
	@ApiOperation(value="收货区域-分页列表查询", notes="收货区域-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmAreaReceiving zmAreaReceiving,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmAreaReceiving> queryWrapper = QueryGenerator.initQueryWrapper(zmAreaReceiving, req.getParameterMap());
		Page<ZmAreaReceiving> page = new Page<ZmAreaReceiving>(pageNo, pageSize);
		IPage<ZmAreaReceiving> pageList = zmAreaReceivingService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmAreaReceiving
	 * @return
	 */
	@AutoLog(value = "收货区域-添加")
	@ApiOperation(value="收货区域-添加", notes="收货区域-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmAreaReceiving zmAreaReceiving) {
		zmAreaReceivingService.save(zmAreaReceiving);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmAreaReceiving
	 * @return
	 */
	@AutoLog(value = "收货区域-编辑")
	@ApiOperation(value="收货区域-编辑", notes="收货区域-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmAreaReceiving zmAreaReceiving) {
		zmAreaReceivingService.updateById(zmAreaReceiving);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收货区域-通过id删除")
	@ApiOperation(value="收货区域-通过id删除", notes="收货区域-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmAreaReceivingService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "收货区域-批量删除")
	@ApiOperation(value="收货区域-批量删除", notes="收货区域-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmAreaReceivingService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收货区域-通过id查询")
	@ApiOperation(value="收货区域-通过id查询", notes="收货区域-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmAreaReceiving zmAreaReceiving = zmAreaReceivingService.getById(id);
		if(zmAreaReceiving==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmAreaReceiving);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmAreaReceiving
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmAreaReceiving zmAreaReceiving) {
        return super.exportXls(request, zmAreaReceiving, ZmAreaReceiving.class, "收货区域");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZmAreaReceiving.class);
    }

}
