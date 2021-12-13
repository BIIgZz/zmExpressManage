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
import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouseDetail;
import org.jeecg.modules.demo.zmexpress.entity.ZmFbaWarehouse;
import org.jeecg.modules.demo.zmexpress.service.IZmFbaWarehouseService;
import org.jeecg.modules.demo.zmexpress.service.IZmFbaWarehouseDetailService;
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
 * @Description: 地址库
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
@Api(tags="地址库")
@RestController
@RequestMapping("/zmexpress/zmFbaWarehouse")
@Slf4j
public class ZmFbaWarehouseController extends JeecgController<ZmFbaWarehouse, IZmFbaWarehouseService> {

	@Autowired
	private IZmFbaWarehouseService zmFbaWarehouseService;

	@Autowired
	private IZmFbaWarehouseDetailService zmFbaWarehouseDetailService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param zmFbaWarehouse
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "地址库-分页列表查询")
	@ApiOperation(value="地址库-分页列表查询", notes="地址库-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmFbaWarehouse zmFbaWarehouse,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmFbaWarehouse> queryWrapper = QueryGenerator.initQueryWrapper(zmFbaWarehouse, req.getParameterMap());
		Page<ZmFbaWarehouse> page = new Page<ZmFbaWarehouse>(pageNo, pageSize);
		IPage<ZmFbaWarehouse> pageList = zmFbaWarehouseService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param zmFbaWarehouse
     * @return
     */
    @AutoLog(value = "地址库-添加")
    @ApiOperation(value="地址库-添加", notes="地址库-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ZmFbaWarehouse zmFbaWarehouse) {
        zmFbaWarehouseService.save(zmFbaWarehouse);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param zmFbaWarehouse
     * @return
     */
    @AutoLog(value = "地址库-编辑")
    @ApiOperation(value="地址库-编辑", notes="地址库-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ZmFbaWarehouse zmFbaWarehouse) {
        zmFbaWarehouseService.updateById(zmFbaWarehouse);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "地址库-通过id删除")
    @ApiOperation(value="地址库-通过id删除", notes="地址库-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        zmFbaWarehouseService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "地址库-批量删除")
    @ApiOperation(value="地址库-批量删除", notes="地址库-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.zmFbaWarehouseService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmFbaWarehouse zmFbaWarehouse) {
        return super.exportXls(request, zmFbaWarehouse, ZmFbaWarehouse.class, "地址库");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZmFbaWarehouse.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-地址详情-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "地址详情-通过主表ID查询")
	@ApiOperation(value="地址详情-通过主表ID查询", notes="地址详情-通过主表ID查询")
	@GetMapping(value = "/listZmFbaWarehouseDetailByMainId")
    public Result<?> listZmFbaWarehouseDetailByMainId(ZmFbaWarehouseDetail zmFbaWarehouseDetail,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<ZmFbaWarehouseDetail> queryWrapper = QueryGenerator.initQueryWrapper(zmFbaWarehouseDetail, req.getParameterMap());
        Page<ZmFbaWarehouseDetail> page = new Page<ZmFbaWarehouseDetail>(pageNo, pageSize);
        IPage<ZmFbaWarehouseDetail> pageList = zmFbaWarehouseDetailService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param zmFbaWarehouseDetail
	 * @return
	 */
	@AutoLog(value = "地址详情-添加")
	@ApiOperation(value="地址详情-添加", notes="地址详情-添加")
	@PostMapping(value = "/addZmFbaWarehouseDetail")
	public Result<?> addZmFbaWarehouseDetail(@RequestBody ZmFbaWarehouseDetail zmFbaWarehouseDetail) {
		zmFbaWarehouseDetailService.save(zmFbaWarehouseDetail);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param zmFbaWarehouseDetail
	 * @return
	 */
	@AutoLog(value = "地址详情-编辑")
	@ApiOperation(value="地址详情-编辑", notes="地址详情-编辑")
	@PutMapping(value = "/editZmFbaWarehouseDetail")
	public Result<?> editZmFbaWarehouseDetail(@RequestBody ZmFbaWarehouseDetail zmFbaWarehouseDetail) {
		zmFbaWarehouseDetailService.updateById(zmFbaWarehouseDetail);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "地址详情-通过id删除")
	@ApiOperation(value="地址详情-通过id删除", notes="地址详情-通过id删除")
	@DeleteMapping(value = "/deleteZmFbaWarehouseDetail")
	public Result<?> deleteZmFbaWarehouseDetail(@RequestParam(name="id",required=true) String id) {
		zmFbaWarehouseDetailService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "地址详情-批量删除")
	@ApiOperation(value="地址详情-批量删除", notes="地址详情-批量删除")
	@DeleteMapping(value = "/deleteBatchZmFbaWarehouseDetail")
	public Result<?> deleteBatchZmFbaWarehouseDetail(@RequestParam(name="ids",required=true) String ids) {
	    this.zmFbaWarehouseDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportZmFbaWarehouseDetail")
    public ModelAndView exportZmFbaWarehouseDetail(HttpServletRequest request, ZmFbaWarehouseDetail zmFbaWarehouseDetail) {
		 // Step.1 组装查询条件
		 QueryWrapper<ZmFbaWarehouseDetail> queryWrapper = QueryGenerator.initQueryWrapper(zmFbaWarehouseDetail, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<ZmFbaWarehouseDetail> pageList = zmFbaWarehouseDetailService.list(queryWrapper);
		 List<ZmFbaWarehouseDetail> exportList = null;

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
		 mv.addObject(NormalExcelConstants.FILE_NAME, "地址详情"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, ZmFbaWarehouseDetail.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("地址详情报表", "导出人:" + sysUser.getRealname(), "地址详情"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importZmFbaWarehouseDetail/{mainId}")
    public Result<?> importZmFbaWarehouseDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<ZmFbaWarehouseDetail> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmFbaWarehouseDetail.class, params);
				 for (ZmFbaWarehouseDetail temp : list) {
                    temp.setFbaWarehouseId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 zmFbaWarehouseDetailService.saveBatch(list);
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

    /*--------------------------------子表处理-地址详情-end----------------------------------------------*/




}
