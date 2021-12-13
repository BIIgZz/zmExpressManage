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
import org.jeecg.modules.demo.zmexpress.entity.ZmStation;
import org.jeecg.modules.demo.zmexpress.service.IZmStationService;

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
 * @Description: 货站/仓库
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
@Api(tags="货站/仓库")
@RestController
@RequestMapping("/zmexpress/zmStation")
@Slf4j
public class ZmStationController extends JeecgController<ZmStation, IZmStationService> {
	@Autowired
	private IZmStationService zmStationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmStation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "货站/仓库-分页列表查询")
	@ApiOperation(value="货站/仓库-分页列表查询", notes="货站/仓库-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmStation zmStation,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmStation> queryWrapper = QueryGenerator.initQueryWrapper(zmStation, req.getParameterMap());
		Page<ZmStation> page = new Page<ZmStation>(pageNo, pageSize);
		IPage<ZmStation> pageList = zmStationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmStation
	 * @return
	 */
	@AutoLog(value = "货站/仓库-添加")
	@ApiOperation(value="货站/仓库-添加", notes="货站/仓库-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmStation zmStation) {
		zmStationService.save(zmStation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmStation
	 * @return
	 */
	@AutoLog(value = "货站/仓库-编辑")
	@ApiOperation(value="货站/仓库-编辑", notes="货站/仓库-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmStation zmStation) {
		zmStationService.updateById(zmStation);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货站/仓库-通过id删除")
	@ApiOperation(value="货站/仓库-通过id删除", notes="货站/仓库-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmStationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "货站/仓库-批量删除")
	@ApiOperation(value="货站/仓库-批量删除", notes="货站/仓库-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmStationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货站/仓库-通过id查询")
	@ApiOperation(value="货站/仓库-通过id查询", notes="货站/仓库-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmStation zmStation = zmStationService.getById(id);
		if(zmStation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmStation);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmStation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmStation zmStation) {
        return super.exportXls(request, zmStation, ZmStation.class, "货站/仓库");
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
        return super.importExcel(request, response, ZmStation.class);
    }

}
