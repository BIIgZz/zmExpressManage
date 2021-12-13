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
import org.jeecg.modules.demo.zmexpress.entity.ZmHscode;
import org.jeecg.modules.demo.zmexpress.service.IZmHscodeService;

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
 * @Description: hscode
 * @Author: jeecg-boot
 * @Date:   2021-07-19
 * @Version: V1.0
 */
@Api(tags="hscode")
@RestController
@RequestMapping("/zmexpress/zmHscode")
@Slf4j
public class ZmHscodeController extends JeecgController<ZmHscode, IZmHscodeService> {
	@Autowired
	private IZmHscodeService zmHscodeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmHscode
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "hscode-分页列表查询")
	@ApiOperation(value="hscode-分页列表查询", notes="hscode-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmHscode zmHscode,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmHscode> queryWrapper = QueryGenerator.initQueryWrapper(zmHscode, req.getParameterMap());
		Page<ZmHscode> page = new Page<ZmHscode>(pageNo, pageSize);
		IPage<ZmHscode> pageList = zmHscodeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmHscode
	 * @return
	 */
	@AutoLog(value = "hscode-添加")
	@ApiOperation(value="hscode-添加", notes="hscode-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmHscode zmHscode) {
		zmHscodeService.save(zmHscode);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmHscode
	 * @return
	 */
	@AutoLog(value = "hscode-编辑")
	@ApiOperation(value="hscode-编辑", notes="hscode-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmHscode zmHscode) {
		zmHscodeService.updateById(zmHscode);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "hscode-通过id删除")
	@ApiOperation(value="hscode-通过id删除", notes="hscode-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmHscodeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "hscode-批量删除")
	@ApiOperation(value="hscode-批量删除", notes="hscode-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmHscodeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "hscode-通过id查询")
	@ApiOperation(value="hscode-通过id查询", notes="hscode-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmHscode zmHscode = zmHscodeService.getById(id);
		if(zmHscode==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmHscode);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmHscode
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmHscode zmHscode) {
        return super.exportXls(request, zmHscode, ZmHscode.class, "hscode");
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
        return super.importExcel(request, response, ZmHscode.class);
    }

}
