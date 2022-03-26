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
import org.jeecg.modules.demo.zmexpress.entity.ZmClientMain;
import org.jeecg.modules.demo.zmexpress.entity.ZmUserDetail;
import org.jeecg.modules.demo.zmexpress.service.IZmClientMainService;
import org.jeecg.modules.demo.zmexpress.service.IZmUserDetailService;

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
 * @Description: 用户认证资料
 * @Author: jeecg-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
@Api(tags="用户认证资料")
@RestController
@RequestMapping("/zmexpress/zmUserDetail")
@Slf4j
public class ZmUserDetailController extends JeecgController<ZmUserDetail, IZmUserDetailService> {
	@Autowired
	private IZmUserDetailService zmUserDetailService;
	@Autowired
	private IZmClientMainService zmClientMainService;

	/**
	 * 分页列表查询
	 *
	 * @param zmUserDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "用户认证资料-分页列表查询")
	@ApiOperation(value="用户认证资料-分页列表查询", notes="用户认证资料-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmUserDetail zmUserDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmUserDetail> queryWrapper = QueryGenerator.initQueryWrapper(zmUserDetail, req.getParameterMap());
		Page<ZmUserDetail> page = new Page<ZmUserDetail>(pageNo, pageSize);
		IPage<ZmUserDetail> pageList = zmUserDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmUserDetail
	 * @return
	 */
	@AutoLog(value = "用户认证资料-添加")
	@ApiOperation(value="用户认证资料-添加", notes="用户认证资料-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmUserDetail zmUserDetail) {
		zmUserDetail.setStatus("1");
		ZmClientMain clientMain = zmClientMainService.getOne(new QueryWrapper<ZmClientMain>().eq("code", zmUserDetail.getUserId()));
		clientMain.setStatus("1");
		zmClientMainService.updateById(clientMain);
		zmUserDetailService.save(zmUserDetail);

		return Result.OK("认证信息提交成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmUserDetail
	 * @return
	 */
	@AutoLog(value = "用户认证资料-编辑")
	@ApiOperation(value="用户认证资料-编辑", notes="用户认证资料-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmUserDetail zmUserDetail) {
		zmUserDetailService.updateById(zmUserDetail);
		return Result.OK("修改成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户认证资料-通过id删除")
	@ApiOperation(value="用户认证资料-通过id删除", notes="用户认证资料-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmUserDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户认证资料-批量删除")
	@ApiOperation(value="用户认证资料-批量删除", notes="用户认证资料-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmUserDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户认证资料-通过id查询")
	@ApiOperation(value="用户认证资料-通过id查询", notes="用户认证资料-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmUserDetail zmUserDetail = zmUserDetailService.getById(id);
		if(zmUserDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmUserDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmUserDetail
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmUserDetail zmUserDetail) {
        return super.exportXls(request, zmUserDetail, ZmUserDetail.class, "用户认证资料");
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
        return super.importExcel(request, response, ZmUserDetail.class);
    }
	 /**
	  * 通过用户名查询
	  *
	  * @param name
	  * @return
	  */
	 @AutoLog(value = "用户认证资料-通过用户名查询")
	 @ApiOperation(value="用户认证资料-通过用户名查询", notes="用户认证资料-通过用户名查询")
	 @GetMapping(value = "/queryByName")
	 public Result<?> queryByName(@RequestParam(name="name",required=true) String name) {

		 ZmUserDetail zmUserDetail = zmUserDetailService.getOne(new QueryWrapper<ZmUserDetail>().eq("create_by",name));
		 if(zmUserDetail==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(zmUserDetail);
	 }

	 /**
	  * 通过用户code
	  *
	  * @param userCode
	  * @return
	  */
	 @AutoLog(value = "用户认证资料-通过用户code")
	 @ApiOperation(value="用户认证资料-通过用户code", notes="用户认证资料-通过用户code")
	 @GetMapping(value = "/queryByUserCode")
	 public Result<?> queryByUserCode(@RequestParam(name="userCode",required=true,defaultValue = "0") String userCode) {

		 ZmUserDetail zmUserDetail = zmUserDetailService.getOne(new QueryWrapper<ZmUserDetail>().eq("user_id",userCode));
		 if(zmUserDetail==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(zmUserDetail);
	 }

}
