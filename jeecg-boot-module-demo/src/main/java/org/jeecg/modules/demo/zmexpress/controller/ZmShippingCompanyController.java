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
import org.jeecg.modules.demo.zmexpress.entity.ZmShippingCompany;
import org.jeecg.modules.demo.zmexpress.service.IZmShippingCompanyService;

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
 * @Description: 船公司
 * @Author: jeecg-boot
 * @Date:   2022-02-15
 * @Version: V1.0
 */
@Api(tags="船公司")
@RestController
@RequestMapping("/zmexpress/zmShippingCompany")
@Slf4j
public class ZmShippingCompanyController extends JeecgController<ZmShippingCompany, IZmShippingCompanyService> {
	@Autowired
	private IZmShippingCompanyService zmShippingCompanyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmShippingCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "船公司-分页列表查询")
	@ApiOperation(value="船公司-分页列表查询", notes="船公司-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmShippingCompany zmShippingCompany,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmShippingCompany> queryWrapper = QueryGenerator.initQueryWrapper(zmShippingCompany, req.getParameterMap());
		Page<ZmShippingCompany> page = new Page<ZmShippingCompany>(pageNo, pageSize);
		IPage<ZmShippingCompany> pageList = zmShippingCompanyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmShippingCompany
	 * @return
	 */
	@AutoLog(value = "船公司-添加")
	@ApiOperation(value="船公司-添加", notes="船公司-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmShippingCompany zmShippingCompany) {
		zmShippingCompanyService.save(zmShippingCompany);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmShippingCompany
	 * @return
	 */
	@AutoLog(value = "船公司-编辑")
	@ApiOperation(value="船公司-编辑", notes="船公司-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmShippingCompany zmShippingCompany) {
		zmShippingCompanyService.updateById(zmShippingCompany);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "船公司-通过id删除")
	@ApiOperation(value="船公司-通过id删除", notes="船公司-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmShippingCompanyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "船公司-批量删除")
	@ApiOperation(value="船公司-批量删除", notes="船公司-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmShippingCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "船公司-通过id查询")
	@ApiOperation(value="船公司-通过id查询", notes="船公司-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmShippingCompany zmShippingCompany = zmShippingCompanyService.getById(id);
		if(zmShippingCompany==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmShippingCompany);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmShippingCompany
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmShippingCompany zmShippingCompany) {
        return super.exportXls(request, zmShippingCompany, ZmShippingCompany.class, "船公司");
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
        return super.importExcel(request, response, ZmShippingCompany.class);
    }

}
