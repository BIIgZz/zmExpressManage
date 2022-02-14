package org.jeecg.modules.demo.zmexpress.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportFba;
import org.jeecg.modules.demo.zmexpress.vo.ZmImportFbaPage;
import org.jeecg.modules.demo.zmexpress.service.IZmImportFbaService;
import org.jeecg.modules.demo.zmexpress.service.IZmImportGoodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 导入FBA表(已导出)
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@Api(tags="导入FBA表(已导出)")
@RestController
@RequestMapping("/zmexpress/zmImportFba")
@Slf4j
public class ZmImportFbaController {
	@Autowired
	private IZmImportFbaService zmImportFbaService;
	@Autowired
	private IZmImportGoodService zmImportGoodService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmImportFba
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "导入FBA表(已导出)-分页列表查询")
	@ApiOperation(value="导入FBA表(已导出)-分页列表查询", notes="导入FBA表(已导出)-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmImportFba zmImportFba,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmImportFba> queryWrapper = QueryGenerator.initQueryWrapper(zmImportFba, req.getParameterMap());
		Page<ZmImportFba> page = new Page<ZmImportFba>(pageNo, pageSize);
		IPage<ZmImportFba> pageList = zmImportFbaService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmImportFbaPage
	 * @return
	 */
	@AutoLog(value = "导入FBA表(已导出)-添加")
	@ApiOperation(value="导入FBA表(已导出)-添加", notes="导入FBA表(已导出)-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmImportFbaPage zmImportFbaPage) {
		ZmImportFba zmImportFba = new ZmImportFba();
		BeanUtils.copyProperties(zmImportFbaPage, zmImportFba);
		zmImportFbaService.saveMain(zmImportFba, zmImportFbaPage.getZmImportGoodList());
		return Result.OK("添加成功！");
	}
		
	/**
	 *  编辑
	 *
	 * @param zmImportFbaPage
	 * @return
	 */
	@AutoLog(value = "导入FBA表(已导出)-编辑")
	@ApiOperation(value="导入FBA表(已导出)-编辑", notes="导入FBA表(已导出)-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmImportFbaPage zmImportFbaPage) {
		ZmImportFba zmImportFba = new ZmImportFba();
		BeanUtils.copyProperties(zmImportFbaPage, zmImportFba);
		ZmImportFba zmImportFbaEntity = zmImportFbaService.getById(zmImportFba.getId());
		if(zmImportFbaEntity==null) {
			return Result.error("未找到对应数据");
		}
		zmImportFbaService.updateMain(zmImportFba, zmImportFbaPage.getZmImportGoodList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "导入FBA表(已导出)-通过id删除")
	@ApiOperation(value="导入FBA表(已导出)-通过id删除", notes="导入FBA表(已导出)-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmImportFbaService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "导入FBA表(已导出)-批量删除")
	@ApiOperation(value="导入FBA表(已导出)-批量删除", notes="导入FBA表(已导出)-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmImportFbaService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "导入FBA表(已导出)-通过id查询")
	@ApiOperation(value="导入FBA表(已导出)-通过id查询", notes="导入FBA表(已导出)-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmImportFba zmImportFba = zmImportFbaService.getById(id);
		if(zmImportFba==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmImportFba);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货柜详情-通过主表ID查询")
	@ApiOperation(value="货柜详情-通过主表ID查询", notes="货柜详情-通过主表ID查询")
	@GetMapping(value = "/queryZmImportGoodByMainId")
	public Result<?> queryZmImportGoodListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ZmImportGood> zmImportGoodList = zmImportGoodService.selectByMainId(id);
		IPage <ZmImportGood> page = new Page<>();
		page.setRecords(zmImportGoodList);
		page.setTotal(zmImportGoodList.size());
		return Result.OK(page);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmImportFba
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmImportFba zmImportFba) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ZmImportFba> queryWrapper = QueryGenerator.initQueryWrapper(zmImportFba, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<ZmImportFba> queryList = zmImportFbaService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<ZmImportFba> zmImportFbaList = new ArrayList<ZmImportFba>();
      if(oConvertUtils.isEmpty(selections)) {
          zmImportFbaList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          zmImportFbaList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ZmImportFbaPage> pageList = new ArrayList<ZmImportFbaPage>();
      for (ZmImportFba main : zmImportFbaList) {
          ZmImportFbaPage vo = new ZmImportFbaPage();
          BeanUtils.copyProperties(main, vo);
          List<ZmImportGood> zmImportGoodList = zmImportGoodService.selectByMainId(main.getId());
          vo.setZmImportGoodList(zmImportGoodList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "导入FBA表(已导出)列表");
      mv.addObject(NormalExcelConstants.CLASS, ZmImportFbaPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("导入FBA表(已导出)数据", "导出人:"+sysUser.getRealname(), "导入FBA表(已导出)"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ZmImportFbaPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmImportFbaPage.class, params);
              for (ZmImportFbaPage page : list) {
                  ZmImportFba po = new ZmImportFba();
                  BeanUtils.copyProperties(page, po);
                  zmImportFbaService.saveMain(po, page.getZmImportGoodList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
