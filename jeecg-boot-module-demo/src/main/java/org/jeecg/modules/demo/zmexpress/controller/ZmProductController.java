package org.jeecg.modules.demo.zmexpress.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportFba;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmProduct;
import org.jeecg.modules.demo.zmexpress.entity.ZmProductExport;
import org.jeecg.modules.demo.zmexpress.service.IZmProductService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.zmexpress.utils.HttpClientUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.def.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.view.JeecgTemplateExcelView;
import org.springframework.beans.BeanUtils;
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
 * @Description: ????????????
 * @Author: jeecg-boot
 * @Date:   2021-07-27
 * @Version: V1.0
 */
@Api(tags="????????????")
@RestController
@RequestMapping("/zmexpress/zmProduct")
@Slf4j
public class ZmProductController extends JeecgController<ZmProduct, IZmProductService> {
	@Autowired
	private IZmProductService zmProductService;
	
	/**
	 * ??????????????????
	 *
	 * @param zmProduct
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "????????????-??????????????????")
	@ApiOperation(value="????????????-??????????????????", notes="????????????-??????????????????")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmProduct zmProduct,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmProduct> queryWrapper = QueryGenerator.initQueryWrapper(zmProduct, req.getParameterMap());
		Page<ZmProduct> page = new Page<ZmProduct>(pageNo, pageSize);
		IPage<ZmProduct> pageList = zmProductService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   ??????
	 *
	 * @param zmProduct
	 * @return
	 */
	@AutoLog(value = "????????????-??????")
	@ApiOperation(value="????????????-??????", notes="????????????-??????")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmProduct zmProduct) {
		zmProductService.save(zmProduct);
		return Result.OK("???????????????");
	}
	
	/**
	 *  ??????
	 *
	 * @param zmProduct
	 * @return
	 */
	@AutoLog(value = "????????????-??????")
	@ApiOperation(value="????????????-??????", notes="????????????-??????")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmProduct zmProduct) {
		zmProductService.updateById(zmProduct);
		return Result.OK("????????????!");
	}
	
	/**
	 *   ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "????????????-??????id??????")
	@ApiOperation(value="????????????-??????id??????", notes="????????????-??????id??????")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmProductService.removeById(id);
		return Result.OK("????????????!");
	}
	
	/**
	 *  ????????????
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "????????????-????????????")
	@ApiOperation(value="????????????-????????????", notes="????????????-????????????")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmProductService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("??????????????????!");
	}
	
	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "????????????-??????id??????")
	@ApiOperation(value="????????????-??????id??????", notes="????????????-??????id??????")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmProduct zmProduct = zmProductService.getById(id);
		if(zmProduct==null) {
			return Result.error("?????????????????????");
		}
		return Result.OK(zmProduct);
	}

    /**
    * ??????excel
    *
    * @param request
    * @param zmProduct
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmProduct zmProduct) {

//        // Step.1 ??????????????????
//        QueryWrapper<ZmProduct> queryWrapper = QueryGenerator.initQueryWrapper(zmProduct, request.getParameterMap());
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//
//        // Step.2 ??????????????????
//        List<ZmProduct> pageList = zmProductService.list(queryWrapper);
//
//
//        // ??????????????????
//        String selections = request.getParameter("selections");
//        List<ZmProduct> zmProductList = new ArrayList<ZmProduct>();
//        if(oConvertUtils.isEmpty(selections)) {
//            zmProductList = pageList;
//        }else {
//            List<String> selectionList = Arrays.asList(selections.split(","));
//            zmProductList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
//        }
//
//        //step.3 ????????????
//        Map<String,Object> map = new HashMap<>();
//        List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
//        for (ZmProduct zmProduct1:pageList) {
//            Map<String, Object> m = new HashMap<String, Object>();
//            m.put("cnName",zmProduct1.getCnName());
//            m.put("enName",zmProduct1.getCnName());
//            m.put("declaredPrice",zmProduct1.getDeclaredPrice());
//            m.put("hscode",zmProduct1.getCnName());
//            m.put("material",zmProduct1.getCnName());
//            m.put("brand",zmProduct1.getCnName());
//            m.put("cnName",zmProduct1.getCnName());
//            m.put("cnName",zmProduct1.getCnName());
//            m.put("cnName",zmProduct1.getCnName());
//            m.put("cnName",zmProduct1.getCnName());
//            m.put("cnName",zmProduct1.getCnName());
//            m.put("cnName",zmProduct1.getCnName());
//
//        }
//
//        // Step.4 AutoPoi ??????Excel
//        TemplateExportParams params = new TemplateExportParams();
//        params.setTemplateUrl("E:\\postGraduate\\express\\excel????????????\\template");
//        ModelAndView mv = new ModelAndView(new Templa());
//        ImageEntity
//        mv.addObject(TemplateExcelConstants.PARAMS,params);
//        mv.addObject(TemplateExcelConstants.MAP_DATA, exportList);
//        return mv;

        // Step.1 ??????????????????
        QueryWrapper<ZmProduct> queryWrapper = QueryGenerator.initQueryWrapper(zmProduct, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 ??????????????????
        List<ZmProduct> pageList = zmProductService.list(queryWrapper);


        // ??????????????????
        String selections = request.getParameter("selections");
        List<ZmProduct> zmProductList = new ArrayList<ZmProduct>();
        if(oConvertUtils.isEmpty(selections)) {
            zmProductList = pageList;
        }else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            zmProductList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        List<ZmProductExport> zmProductExportList = new ArrayList<>();
        for (int i = 0; i < zmProductList.size(); i++) {
            ZmProductExport zmProductExport = new ZmProductExport();
            BeanUtils.copyProperties(zmProductList.get(i),zmProductExport);
            zmProductExport.setPicture(HttpClientUtil.getImageFromNetByUrl(zmProductList.get(i).getPicture()));
            zmProductExportList.add(zmProductExport);
        }

        // Step.3 AutoPoi ??????Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//        mv.addObject(NormalExcelConstants.FILE_NAME, title); //???????????????filename?????? ,??????????????????????????????
        mv.addObject(NormalExcelConstants.CLASS, ZmProductExport.class);
//        //update-begin--Author:liusq  Date:20210126 for????????????????????????ImageBasePath?????????--------------------
        ExportParams  exportParams=new ExportParams("??????", "?????????:" + sysUser.getRealname());
//        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for????????????????????????ImageBasePath?????????----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, zmProductExportList);
        return mv;

//        return super.exportXls(request, zmProduct, ZmProduct.class, "????????????");
    }

    /**
      * ??????excel????????????
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZmProduct.class);
    }

}
