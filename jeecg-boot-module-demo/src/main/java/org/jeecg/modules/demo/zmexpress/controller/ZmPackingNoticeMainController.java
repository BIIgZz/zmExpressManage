package org.jeecg.modules.demo.zmexpress.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.TemplateExcelConstants;
import org.jeecgframework.poi.excel.def.TemplateWordConstants;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNotice;
import org.jeecg.modules.demo.zmexpress.entity.ZmPackingNoticeMain;
import org.jeecg.modules.demo.zmexpress.vo.ZmPackingNoticeMainPage;
import org.jeecg.modules.demo.zmexpress.service.IZmPackingNoticeMainService;
import org.jeecg.modules.demo.zmexpress.service.IZmPackingNoticeService;
import org.jeecgframework.poi.excel.view.JeecgTemplateExcelView;
import org.jeecgframework.poi.excel.view.JeecgTemplateWordView;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 装箱通知书主表
 * @Author: jeecg-boot
 * @Date:   2021-07-18
 * @Version: V1.0
 */
@Api(tags="装箱通知书主表")
@RestController
@RequestMapping("/zmexpress/zmPackingNoticeMain")
@Slf4j
public class ZmPackingNoticeMainController {
	@Autowired
	private IZmPackingNoticeMainService zmPackingNoticeMainService;
	@Autowired
	private IZmPackingNoticeService zmPackingNoticeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmPackingNoticeMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "装箱通知书主表-分页列表查询")
	@ApiOperation(value="装箱通知书主表-分页列表查询", notes="装箱通知书主表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmPackingNoticeMain zmPackingNoticeMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmPackingNoticeMain> queryWrapper = QueryGenerator.initQueryWrapper(zmPackingNoticeMain, req.getParameterMap());
		Page<ZmPackingNoticeMain> page = new Page<ZmPackingNoticeMain>(pageNo, pageSize);
		IPage<ZmPackingNoticeMain> pageList = zmPackingNoticeMainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmPackingNoticeMainPage
	 * @return
	 */
	@AutoLog(value = "装箱通知书主表-添加")
	@ApiOperation(value="装箱通知书主表-添加", notes="装箱通知书主表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmPackingNoticeMainPage zmPackingNoticeMainPage) {
		ZmPackingNoticeMain zmPackingNoticeMain = new ZmPackingNoticeMain();
		BeanUtils.copyProperties(zmPackingNoticeMainPage, zmPackingNoticeMain);
		zmPackingNoticeMainService.saveMain(zmPackingNoticeMain, zmPackingNoticeMainPage.getZmPackingNoticeList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmPackingNoticeMainPage
	 * @return
	 */
	@AutoLog(value = "装箱通知书主表-编辑")
	@ApiOperation(value="装箱通知书主表-编辑", notes="装箱通知书主表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmPackingNoticeMainPage zmPackingNoticeMainPage) {
		ZmPackingNoticeMain zmPackingNoticeMain = new ZmPackingNoticeMain();
		BeanUtils.copyProperties(zmPackingNoticeMainPage, zmPackingNoticeMain);
		ZmPackingNoticeMain zmPackingNoticeMainEntity = zmPackingNoticeMainService.getById(zmPackingNoticeMain.getId());
		if(zmPackingNoticeMainEntity==null) {
			return Result.error("未找到对应数据");
		}
		zmPackingNoticeMainService.updateMain(zmPackingNoticeMain, zmPackingNoticeMainPage.getZmPackingNoticeList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "装箱通知书主表-通过id删除")
	@ApiOperation(value="装箱通知书主表-通过id删除", notes="装箱通知书主表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmPackingNoticeMainService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "装箱通知书主表-批量删除")
	@ApiOperation(value="装箱通知书主表-批量删除", notes="装箱通知书主表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmPackingNoticeMainService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "装箱通知书主表-通过id查询")
	@ApiOperation(value="装箱通知书主表-通过id查询", notes="装箱通知书主表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmPackingNoticeMain zmPackingNoticeMain = zmPackingNoticeMainService.getById(id);
		if(zmPackingNoticeMain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmPackingNoticeMain);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "装箱通知书通过主表ID查询")
	@ApiOperation(value="装箱通知书主表ID查询", notes="装箱通知书-通主表ID查询")
	@GetMapping(value = "/queryZmPackingNoticeByMainId")
	public Result<?> queryZmPackingNoticeListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ZmPackingNotice> zmPackingNoticeList = zmPackingNoticeService.selectByMainId(id);
		return Result.OK(zmPackingNoticeList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmPackingNoticeMain
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmPackingNoticeMain zmPackingNoticeMain) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ZmPackingNoticeMain> queryWrapper = QueryGenerator.initQueryWrapper(zmPackingNoticeMain, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<ZmPackingNoticeMain> queryList = zmPackingNoticeMainService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<ZmPackingNoticeMain> zmPackingNoticeMainList = new ArrayList<ZmPackingNoticeMain>();
      if(oConvertUtils.isEmpty(selections)) {
          zmPackingNoticeMainList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          zmPackingNoticeMainList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ZmPackingNoticeMainPage> pageList = new ArrayList<ZmPackingNoticeMainPage>();
      for (ZmPackingNoticeMain main : zmPackingNoticeMainList) {
          ZmPackingNoticeMainPage vo = new ZmPackingNoticeMainPage();
          BeanUtils.copyProperties(main, vo);
          List<ZmPackingNotice> zmPackingNoticeList = zmPackingNoticeService.selectByMainId(main.getId());
          vo.setZmPackingNoticeList(zmPackingNoticeList);
          pageList.add(vo);
      }
		// Step.3.1 创建模板
		Map<String,Object> map = new HashMap<>();
		map.put("packingAddress",pageList.get(0).getPackingAddress());
		map.put("packingDate",pageList.get(0).getPackingDate());
		map.put("shippingOrder",pageList.get(0).getShippingOrder());
		map.put("cabinetNumber",pageList.get(0).getCabinetNumber());
		map.put("seal",pageList.get(0).getSeal());

		//统计
		int countPieces = 0;
		double countVolume = 0;
		double countWeight = 0;
		for (ZmPackingNotice zmPackingNotice : pageList.get(0).getZmPackingNoticeList()) {
			countPieces = countPieces + Integer.parseInt(zmPackingNotice.getPieces());
			countVolume = countVolume + Double.parseDouble(zmPackingNotice.getVolume());
			countWeight = countWeight +  Double.parseDouble(zmPackingNotice.getWeight());
		}
		map.put("countPieces",countPieces);
		map.put("countVolume",countVolume);
		map.put("countWeight",countWeight);


		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		for (ZmPackingNotice zmPackingNotice: pageList.get(0).getZmPackingNoticeList()) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id",zmPackingNotice.getId());
			m.put("shippingOrderId",zmPackingNotice.getShippingOrderId());
			m.put("client",zmPackingNotice.getClient());
			m.put("warehouseReceiptNumber",zmPackingNotice.getWarehouseReceiptNumber());
			m.put("entrySituation",zmPackingNotice.getEntrySituation());
			m.put("channel",zmPackingNotice.getChannel());
			m.put("loadingSequence",zmPackingNotice.getLoadingSequence());
			m.put("pieces",zmPackingNotice.getPieces());
			m.put("volume",zmPackingNotice.getVolume());
			m.put("weight",zmPackingNotice.getWeight());
			m.put("name",zmPackingNotice.getName());
			m.put("code",zmPackingNotice.getCode());
			m.put("remarks",zmPackingNotice.getRemarks());
			m.put("fbaid",zmPackingNotice.getFbaid());
			listMap.add(m);
		}
		map.put("maplist",listMap);

      // Step.4 AutoPoi 导出Excel
//		标准导出
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      mv.addObject(NormalExcelConstants.FILE_NAME, "装箱通知书主表列表");
//      mv.addObject(NormalExcelConstants.CLASS, ZmPackingNoticeMainPage.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("装箱通知书主表数据", "导出人:"+sysUser.getRealname(), "装箱通知书主表"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);

//		模板导出
		TemplateExportParams params = new TemplateExportParams();
		params.setTemplateUrl("E:\\postGraduate\\express\\jeecg-boot-master\\jeecg-boot\\jeecg-boot-module-demo\\src\\main\\resources\\exportTemplate\\Packinglist.xls");
		ModelAndView mv = new ModelAndView(new JeecgTemplateExcelView());
		mv.addObject(TemplateExcelConstants.PARAMS,params);
		mv.addObject(TemplateExcelConstants.MAP_DATA,map);

//		word
//		ModelAndView mv = new ModelAndView(new JeecgTemplateWordView());
//		Map<String, Object> mapWord = new HashMap<>();
//		mapWord.put("shippingOrder",pageList.get(0).getShippingOrder());
//		mapWord.put("packingAddress","ONT8-以星");
//		mapWord.put("cabinetNumber",pageList.get(0).getCabinetNumber());
//		mv.addObject(TemplateWordConstants.URL,"E:\\postGraduate\\express\\jeecg-boot-master\\jeecg-boot\\jeecg-boot-module-demo\\src\\main\\resources\\exportTemplate\\附件四：海运唛头.docx");
//		mv.addObject(TemplateWordConstants.MAP_DATA, mapWord);
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
              List<ZmPackingNoticeMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmPackingNoticeMainPage.class, params);
              for (ZmPackingNoticeMainPage page : list) {
                  ZmPackingNoticeMain po = new ZmPackingNoticeMain();
                  BeanUtils.copyProperties(page, po);
                  zmPackingNoticeMainService.saveMain(po, page.getZmPackingNoticeList());
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
