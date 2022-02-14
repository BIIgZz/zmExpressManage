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
import org.jeecg.modules.demo.zmexpress.entity.ZmLogisticsInformation;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybills;
import org.jeecg.modules.demo.zmexpress.service.IZmBillloadingService;
import org.jeecg.modules.demo.zmexpress.service.IZmLogisticsInformationService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.zmexpress.service.IZmWaybillsService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
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
 * @Description: 物流信息
 * @Author: jeecg-boot
 * @Date:   2021-12-21
 * @Version: V1.0
 */
@Api(tags="物流信息")
@RestController
@RequestMapping("/zmexpress/zmLogisticsInformation")
@Slf4j
public class ZmLogisticsInformationController extends JeecgController<ZmLogisticsInformation, IZmLogisticsInformationService> {
	@Autowired
	private IZmLogisticsInformationService zmLogisticsInformationService;
	@Autowired
	private IZmBillloadingService zmBillloadingService;
	@Autowired
	private IZmWaybillsService zmWaybillsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmLogisticsInformation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "物流信息-分页列表查询")
	@ApiOperation(value="物流信息-分页列表查询", notes="物流信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmLogisticsInformation zmLogisticsInformation,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmLogisticsInformation> queryWrapper = QueryGenerator.initQueryWrapper(zmLogisticsInformation, req.getParameterMap());
		Page<ZmLogisticsInformation> page = new Page<ZmLogisticsInformation>(pageNo, pageSize);
		IPage<ZmLogisticsInformation> pageList = zmLogisticsInformationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmLogisticsInformation
	 * @return
	 */
	@AutoLog(value = "物流信息-添加")
	@ApiOperation(value="物流信息-添加", notes="物流信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmLogisticsInformation zmLogisticsInformation) {
		zmLogisticsInformationService.save(zmLogisticsInformation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmLogisticsInformation
	 * @return
	 */
	@AutoLog(value = "物流信息-编辑")
	@ApiOperation(value="物流信息-编辑", notes="物流信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmLogisticsInformation zmLogisticsInformation) {
		zmLogisticsInformationService.updateById(zmLogisticsInformation);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物流信息-通过id删除")
	@ApiOperation(value="物流信息-通过id删除", notes="物流信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmLogisticsInformationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "物流信息-批量删除")
	@ApiOperation(value="物流信息-批量删除", notes="物流信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmLogisticsInformationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物流信息-通过id查询")
	@ApiOperation(value="物流信息-通过id查询", notes="物流信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmLogisticsInformation zmLogisticsInformation = zmLogisticsInformationService.getById(id);
		if(zmLogisticsInformation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmLogisticsInformation);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmLogisticsInformation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmLogisticsInformation zmLogisticsInformation) {
        return super.exportXls(request, zmLogisticsInformation, ZmLogisticsInformation.class, "物流信息");
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
        return super.importExcel(request, response, ZmLogisticsInformation.class);
    }


	 /**
	  * @title queryByOrderId
	  * @description
	  * @author zzh
	  * @param: id
	  * @updateTime 2021/12/24 19:53
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping(value = "/queryByOrderId")
	 public Result<?> queryByOrderId(@RequestParam(name="id",required=true) String id) {

		 QueryWrapper<ZmLogisticsInformation> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("order_id",id);
		 List<ZmLogisticsInformation> zmLogisticsInformations = zmLogisticsInformationService.list(queryWrapper);
		 if(zmLogisticsInformations==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(zmLogisticsInformations);
	 }

	 /**
	  * 通过运单id查询
	  *
	  * @param waybillId 运单id
	  * @return
	  */
	 @AutoLog(value = "物流信息-通过运单id查询")
	 @ApiOperation(value="物流信息-通过运单id查询", notes="物流信息-通过运单id查询")
	 @GetMapping(value = "/queryByWaybillId")
	 public Result<?> queryByWaybillId(@RequestParam(name="waybillId",required=true) String waybillId) {
		 QueryWrapper<ZmLogisticsInformation> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("order_id",waybillId);
		 List<ZmLogisticsInformation> list = zmLogisticsInformationService.list(queryWrapper);
		 if(list==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(list);
	 }

	 /**
	  * @title addMsg
	  * @description
	  * @author zzh
	  * @param: zmLogisticsInformation
	  * @updateTime 2021/12/24 20:26
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */

	 @PutMapping(value = "/addMsg")
	 public Result<?> addMsg(@RequestBody ZmLogisticsInformation zmLogisticsInformation) {
		 //设置运单物流信息
		 QueryWrapper<ZmWaybills> queryWrapperWay = new QueryWrapper<>();
		 queryWrapperWay.eq("bill_id",zmLogisticsInformation.getOrderId());
		 List<ZmWaybills> list = zmWaybillsService.list(queryWrapperWay);
		 for (ZmWaybills zmWaybills : list) {
			 ZmLogisticsInformation zmLogisticsInformationWayBill = new ZmLogisticsInformation();
			 BeanUtils.copyProperties(zmLogisticsInformation,zmLogisticsInformationWayBill);
			 zmLogisticsInformation.setOrderId(zmWaybills.getOrderId());
			 zmLogisticsInformationService.save(zmLogisticsInformationWayBill);
		 }
		 //设置提单物流信息
		 zmLogisticsInformationService.save(zmLogisticsInformation);
		 return Result.OK("路由信息添加成功！");
	 }





 }
