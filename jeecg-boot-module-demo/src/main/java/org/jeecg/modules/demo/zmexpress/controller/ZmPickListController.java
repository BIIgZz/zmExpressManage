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
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmPickList;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybill;
import org.jeecg.modules.demo.zmexpress.rules.ShopOrderRule;
import org.jeecg.modules.demo.zmexpress.service.IZmPickListService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.zmexpress.service.IZmWaybillService;
import org.jeecg.modules.demo.zmexpress.vo.ZmWaybillPage;
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
 * @Description: zm_pick_list
 * @Author: jeecg-boot
 * @Date:   2021-12-18
 * @Version: V1.0
 */
@Api(tags="zm_pick_list")
@RestController
@RequestMapping("/zmexpress/zmPickList")
@Slf4j
public class ZmPickListController extends JeecgController<ZmPickList, IZmPickListService> {

	 @Autowired
	 private IZmPickListService zmPickListService;
	 @Autowired
	 private IZmWaybillService zmWaybillService;
	 @Autowired
	 private ShopOrderRule shopOrderRule;
	/**
	 * ??????????????????
	 *
	 * @param zmPickList
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "zm_pick_list-??????????????????")
	@ApiOperation(value="zm_pick_list-??????????????????", notes="zm_pick_list-??????????????????")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmPickList zmPickList,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmPickList> queryWrapper = QueryGenerator.initQueryWrapper(zmPickList, req.getParameterMap());
		Page<ZmPickList> page = new Page<ZmPickList>(pageNo, pageSize);
		IPage<ZmPickList> pageList = zmPickListService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   ??????
	 *
	 * @param zmPickList
	 * @return
	 */
	@AutoLog(value = "zm_pick_list-??????")
	@ApiOperation(value="zm_pick_list-??????", notes="zm_pick_list-??????")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmPickList zmPickList) {
		zmPickListService.save(zmPickList);
		return Result.OK("???????????????");
	}
	
	/**
	 *  ??????
	 *
	 * @param zmPickList
	 * @return
	 */
	@AutoLog(value = "zm_pick_list-??????")
	@ApiOperation(value="zm_pick_list-??????", notes="zm_pick_list-??????")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmPickList zmPickList) {
		zmPickListService.updateById(zmPickList);
		return Result.OK("????????????!");
	}
	
	/**
	 *   ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "zm_pick_list-??????id??????")
	@ApiOperation(value="zm_pick_list-??????id??????", notes="zm_pick_list-??????id??????")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmPickListService.removeById(id);
		return Result.OK("????????????!");
	}
	
	/**
	 *  ????????????
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "zm_pick_list-????????????")
	@ApiOperation(value="zm_pick_list-????????????", notes="zm_pick_list-????????????")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmPickListService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("??????????????????!");
	}
	
	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "zm_pick_list-??????id??????")
	@ApiOperation(value="zm_pick_list-??????id??????", notes="zm_pick_list-??????id??????")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmPickList zmPickList = zmPickListService.getById(id);
		if(zmPickList==null) {
			return Result.error("?????????????????????");
		}
		return Result.OK(zmPickList);
	}

    /**
    * ??????excel
    *
    * @param request
    * @param zmPickList
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmPickList zmPickList) {
        return super.exportXls(request, zmPickList, ZmPickList.class, "zm_pick_list");
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
        return super.importExcel(request, response, ZmPickList.class);
    }

	 /**
	  * ??????
	  * ???????????????????????????????????????
	  * ?????????????????????
	  * ????????????
	  * ???????????????
	  */
	 @PutMapping(value = "/picking")
	 public  Result<?> picking( @RequestParam(name = "id", required = true) String id,
								@RequestBody List<ZmPickList> pickLists){
		 ZmWaybill zmWaybillEntity = zmWaybillService.getById(id);
		 String pickNo = shopOrderRule.autoPickNo();
		 for (ZmPickList pickList : pickLists) {
			 pickList.setWaybillId(zmWaybillEntity.getWaybillId());
			 pickList.setPickNo(pickNo);
			 pickList.setBoxNum(String.valueOf(pickLists.size()));
		 }
		 if (zmWaybillEntity == null) {
			 return Result.error("?????????????????????");
		 }
		 zmPickListService.saveBatch(pickLists);
		 return Result.OK("????????????!");
	 }
	 /**
	  * ?????????????????????
	  *
	  */
	 @PutMapping(value = "/pickingCustomer")
	 public  Result<?> pickingCustomer( @RequestParam(name = "id", required = true) String id,
								@RequestBody List<ZmPickList> pickLists){
		 ZmWaybill zmWaybillEntity = zmWaybillService.getById(id);
		 String pickNo = shopOrderRule.autoPickNo();
		 for (ZmPickList pickList : pickLists) {
			 pickList.setWaybillId(zmWaybillEntity.getWaybillId());
			 pickList.setPickNo(pickNo);
			 pickList.setBoxNum(String.valueOf(pickLists.size()));
		 }
		 if (zmWaybillEntity == null) {
			 return Result.error("?????????????????????");
		 }
		 zmPickListService.saveBatch(pickLists);
		 return Result.OK("????????????!");
	 }

}
