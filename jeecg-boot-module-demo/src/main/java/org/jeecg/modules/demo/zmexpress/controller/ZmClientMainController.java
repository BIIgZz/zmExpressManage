package org.jeecg.modules.demo.zmexpress.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientBasic;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientAddress;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientFinance;
import org.jeecg.modules.demo.zmexpress.entity.ZmClientMain;
import org.jeecg.modules.demo.zmexpress.service.IZmClientMainService;
import org.jeecg.modules.demo.zmexpress.service.IZmClientBasicService;
import org.jeecg.modules.demo.zmexpress.service.IZmClientAddressService;
import org.jeecg.modules.demo.zmexpress.service.IZmClientFinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 用户主表
 * @Author: jeecg-boot
 * @Date:   2021-08-10
 * @Version: V1.0
 */
@Api(tags="用户主表")
@RestController
@RequestMapping("/zmexpress/zmClientMain")
@Slf4j
public class ZmClientMainController extends JeecgController<ZmClientMain, IZmClientMainService> {

	@Autowired
	private IZmClientMainService zmClientMainService;

	@Autowired
	private IZmClientBasicService zmClientBasicService;

	@Autowired
	private IZmClientAddressService zmClientAddressService;

	@Autowired
	private IZmClientFinanceService zmClientFinanceService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param zmClientMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "用户主表-分页列表查询")
	@ApiOperation(value="用户主表-分页列表查询", notes="用户主表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmClientMain zmClientMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmClientMain> queryWrapper = QueryGenerator.initQueryWrapper(zmClientMain, req.getParameterMap());
		Page<ZmClientMain> page = new Page<ZmClientMain>(pageNo, pageSize);
		IPage<ZmClientMain> pageList = zmClientMainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param zmClientMain
     * @return
     */
    @AutoLog(value = "用户主表-添加")
    @ApiOperation(value="用户主表-添加", notes="用户主表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ZmClientMain zmClientMain) {
        zmClientMainService.save(zmClientMain);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param zmClientMain
     * @return
     */
    @AutoLog(value = "用户主表-编辑")
    @ApiOperation(value="用户主表-编辑", notes="用户主表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ZmClientMain zmClientMain) {
        zmClientMainService.updateById(zmClientMain);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "用户主表-通过id删除")
    @ApiOperation(value="用户主表-通过id删除", notes="用户主表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        zmClientMainService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "用户主表-批量删除")
    @ApiOperation(value="用户主表-批量删除", notes="用户主表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.zmClientMainService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmClientMain zmClientMain) {
        return super.exportXls(request, zmClientMain, ZmClientMain.class, "用户主表");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZmClientMain.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-用户基础信息-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "用户基础信息-通过主表ID查询")
	@ApiOperation(value="用户基础信息-通过主表ID查询", notes="用户基础信息-通过主表ID查询")
	@GetMapping(value = "/listZmClientBasicByMainId")
    public Result<?> listZmClientBasicByMainId(ZmClientBasic zmClientBasic,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<ZmClientBasic> queryWrapper = QueryGenerator.initQueryWrapper(zmClientBasic, req.getParameterMap());
        Page<ZmClientBasic> page = new Page<ZmClientBasic>(pageNo, pageSize);
        IPage<ZmClientBasic> pageList = zmClientBasicService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param zmClientBasic
	 * @return
	 */
	@AutoLog(value = "用户基础信息-添加")
	@ApiOperation(value="用户基础信息-添加", notes="用户基础信息-添加")
	@PostMapping(value = "/addZmClientBasic")
	public Result<?> addZmClientBasic(@RequestBody ZmClientBasic zmClientBasic) {
		zmClientBasicService.save(zmClientBasic);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param zmClientBasic
	 * @return
	 */
	@AutoLog(value = "用户基础信息-编辑")
	@ApiOperation(value="用户基础信息-编辑", notes="用户基础信息-编辑")
	@PutMapping(value = "/editZmClientBasic")
	public Result<?> editZmClientBasic(@RequestBody ZmClientBasic zmClientBasic) {
		zmClientBasicService.updateById(zmClientBasic);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户基础信息-通过id删除")
	@ApiOperation(value="用户基础信息-通过id删除", notes="用户基础信息-通过id删除")
	@DeleteMapping(value = "/deleteZmClientBasic")
	public Result<?> deleteZmClientBasic(@RequestParam(name="id",required=true) String id) {
		zmClientBasicService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户基础信息-批量删除")
	@ApiOperation(value="用户基础信息-批量删除", notes="用户基础信息-批量删除")
	@DeleteMapping(value = "/deleteBatchZmClientBasic")
	public Result<?> deleteBatchZmClientBasic(@RequestParam(name="ids",required=true) String ids) {
	    this.zmClientBasicService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportZmClientBasic")
    public ModelAndView exportZmClientBasic(HttpServletRequest request, ZmClientBasic zmClientBasic) {
		 // Step.1 组装查询条件
		 QueryWrapper<ZmClientBasic> queryWrapper = QueryGenerator.initQueryWrapper(zmClientBasic, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<ZmClientBasic> pageList = zmClientBasicService.list(queryWrapper);
		 List<ZmClientBasic> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "用户基础信息"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, ZmClientBasic.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户基础信息报表", "导出人:" + sysUser.getRealname(), "用户基础信息"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importZmClientBasic/{mainId}")
    public Result<?> importZmClientBasic(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<ZmClientBasic> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmClientBasic.class, params);
				 for (ZmClientBasic temp : list) {
                    temp.setCodeId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 zmClientBasicService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-用户基础信息-end----------------------------------------------*/

    /*--------------------------------子表处理-用户地址-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "用户地址-通过主表ID查询")
	@ApiOperation(value="用户地址-通过主表ID查询", notes="用户地址-通过主表ID查询")
	@GetMapping(value = "/listZmClientAddressByMainId")
    public Result<?> listZmClientAddressByMainId(ZmClientAddress zmClientAddress,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<ZmClientAddress> queryWrapper = QueryGenerator.initQueryWrapper(zmClientAddress, req.getParameterMap());
        Page<ZmClientAddress> page = new Page<ZmClientAddress>(pageNo, pageSize);
        IPage<ZmClientAddress> pageList = zmClientAddressService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param zmClientAddress
	 * @return
	 */
	@AutoLog(value = "用户地址-添加")
	@ApiOperation(value="用户地址-添加", notes="用户地址-添加")
	@PostMapping(value = "/addZmClientAddress")
	public Result<?> addZmClientAddress(@RequestBody ZmClientAddress zmClientAddress) {
		zmClientAddressService.save(zmClientAddress);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param zmClientAddress
	 * @return
	 */
	@AutoLog(value = "用户地址-编辑")
	@ApiOperation(value="用户地址-编辑", notes="用户地址-编辑")
	@PutMapping(value = "/editZmClientAddress")
	public Result<?> editZmClientAddress(@RequestBody ZmClientAddress zmClientAddress) {
		zmClientAddressService.updateById(zmClientAddress);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户地址-通过id删除")
	@ApiOperation(value="用户地址-通过id删除", notes="用户地址-通过id删除")
	@DeleteMapping(value = "/deleteZmClientAddress")
	public Result<?> deleteZmClientAddress(@RequestParam(name="id",required=true) String id) {
		zmClientAddressService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户地址-批量删除")
	@ApiOperation(value="用户地址-批量删除", notes="用户地址-批量删除")
	@DeleteMapping(value = "/deleteBatchZmClientAddress")
	public Result<?> deleteBatchZmClientAddress(@RequestParam(name="ids",required=true) String ids) {
	    this.zmClientAddressService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportZmClientAddress")
    public ModelAndView exportZmClientAddress(HttpServletRequest request, ZmClientAddress zmClientAddress) {
		 // Step.1 组装查询条件
		 QueryWrapper<ZmClientAddress> queryWrapper = QueryGenerator.initQueryWrapper(zmClientAddress, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<ZmClientAddress> pageList = zmClientAddressService.list(queryWrapper);
		 List<ZmClientAddress> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "用户地址"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, ZmClientAddress.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户地址报表", "导出人:" + sysUser.getRealname(), "用户地址"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importZmClientAddress/{mainId}")
    public Result<?> importZmClientAddress(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<ZmClientAddress> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmClientAddress.class, params);
				 for (ZmClientAddress temp : list) {
                    temp.setCodeId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 zmClientAddressService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-用户地址-end----------------------------------------------*/

    /*--------------------------------子表处理-财务数据-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "财务数据-通过主表ID查询")
	@ApiOperation(value="财务数据-通过主表ID查询", notes="财务数据-通过主表ID查询")
	@GetMapping(value = "/listZmClientFinanceByMainId")
    public Result<?> listZmClientFinanceByMainId(ZmClientFinance zmClientFinance,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<ZmClientFinance> queryWrapper = QueryGenerator.initQueryWrapper(zmClientFinance, req.getParameterMap());
        Page<ZmClientFinance> page = new Page<ZmClientFinance>(pageNo, pageSize);
        IPage<ZmClientFinance> pageList = zmClientFinanceService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param zmClientFinance
	 * @return
	 */
	@AutoLog(value = "财务数据-添加")
	@ApiOperation(value="财务数据-添加", notes="财务数据-添加")
	@PostMapping(value = "/addZmClientFinance")
	public Result<?> addZmClientFinance(@RequestBody ZmClientFinance zmClientFinance) {
		zmClientFinanceService.save(zmClientFinance);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param zmClientFinance
	 * @return
	 */
	@AutoLog(value = "财务数据-编辑")
	@ApiOperation(value="财务数据-编辑", notes="财务数据-编辑")
	@PutMapping(value = "/editZmClientFinance")
	public Result<?> editZmClientFinance(@RequestBody ZmClientFinance zmClientFinance) {
		zmClientFinanceService.updateById(zmClientFinance);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "财务数据-通过id删除")
	@ApiOperation(value="财务数据-通过id删除", notes="财务数据-通过id删除")
	@DeleteMapping(value = "/deleteZmClientFinance")
	public Result<?> deleteZmClientFinance(@RequestParam(name="id",required=true) String id) {
		zmClientFinanceService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "财务数据-批量删除")
	@ApiOperation(value="财务数据-批量删除", notes="财务数据-批量删除")
	@DeleteMapping(value = "/deleteBatchZmClientFinance")
	public Result<?> deleteBatchZmClientFinance(@RequestParam(name="ids",required=true) String ids) {
	    this.zmClientFinanceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportZmClientFinance")
    public ModelAndView exportZmClientFinance(HttpServletRequest request, ZmClientFinance zmClientFinance) {
		 // Step.1 组装查询条件
		 QueryWrapper<ZmClientFinance> queryWrapper = QueryGenerator.initQueryWrapper(zmClientFinance, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<ZmClientFinance> pageList = zmClientFinanceService.list(queryWrapper);
		 List<ZmClientFinance> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "财务数据"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, ZmClientFinance.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("财务数据报表", "导出人:" + sysUser.getRealname(), "财务数据"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importZmClientFinance/{mainId}")
    public Result<?> importZmClientFinance(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<ZmClientFinance> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmClientFinance.class, params);
				 for (ZmClientFinance temp : list) {
                    temp.setCodeId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 zmClientFinanceService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-财务数据-end----------------------------------------------*/




}
