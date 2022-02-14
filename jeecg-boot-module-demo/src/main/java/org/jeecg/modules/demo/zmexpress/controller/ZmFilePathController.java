package org.jeecg.modules.demo.zmexpress.controller;

import java.io.File;
import java.util.ArrayList;
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
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmFilePath;
import org.jeecg.modules.demo.zmexpress.service.IZmFilePathService;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 文件路径
 * @Author: jeecg-boot
 * @Date:   2022-01-26
 * @Version: V1.0
 */
@Api(tags="文件路径")
@RestController
@RequestMapping("/zmexpress/zmFilePath")
@Slf4j
public class ZmFilePathController extends JeecgController<ZmFilePath, IZmFilePathService> {
	@Autowired
	private IZmFilePathService zmFilePathService;

	 @Value(value = "${jeecg.path.upload}")
	 private String uploadpath;
	/**
	 * 分页列表查询
	 *
	 * @param zmFilePath
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "文件路径-分页列表查询")
	@ApiOperation(value="文件路径-分页列表查询", notes="文件路径-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmFilePath zmFilePath,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmFilePath> queryWrapper = QueryGenerator.initQueryWrapper(zmFilePath, req.getParameterMap());
		Page<ZmFilePath> page = new Page<ZmFilePath>(pageNo, pageSize);
		IPage<ZmFilePath> pageList = zmFilePathService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmFilePath
	 * @return
	 */
	@AutoLog(value = "文件路径-添加")
	@ApiOperation(value="文件路径-添加", notes="文件路径-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmFilePath zmFilePath) {
		zmFilePathService.save(zmFilePath);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmFilePath
	 * @return
	 */
	@AutoLog(value = "文件路径-编辑")
	@ApiOperation(value="文件路径-编辑", notes="文件路径-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmFilePath zmFilePath) {
		zmFilePathService.updateById(zmFilePath);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文件路径-通过id删除")
	@ApiOperation(value="文件路径-通过id删除", notes="文件路径-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ZmFilePath filePath = zmFilePathService.getById(id);
		zmFilePathService.removeById(id);

		String result = "" ;
		try{
			File file = new File(filePath.getPath());
			if(file.delete()){
				result=file.getName() + " 文件已被删除！";
			}else{
				result=("文件删除失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Result.OK(result);
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文件路径-批量删除")
	@ApiOperation(value="文件路径-批量删除", notes="文件路径-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmFilePathService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文件路径-通过id查询")
	@ApiOperation(value="文件路径-通过id查询", notes="文件路径-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmFilePath zmFilePath = zmFilePathService.getById(id);
		if(zmFilePath==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmFilePath);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmFilePath
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmFilePath zmFilePath) {
        return super.exportXls(request, zmFilePath, ZmFilePath.class, "文件路径");
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
		String[] waybillId = multipartRequest.getParameterValues("waybillId");
		//存入本地
		MultipartFile multipartFile = fileMap.get("files[]");// 获取上传文件对象
		try {
			String bizPath = "";
			String ctxPath = uploadpath;
			String fileName = null;
			File file = new File(ctxPath + File.separator + bizPath + File.separator );
			if (!file.exists()) {
				file.mkdirs();// 创建文件根目录
			}
			String orgName = multipartFile.getOriginalFilename();// 获取文件名
			orgName = CommonUtils.getFileName(orgName);
			ZmFilePath filePath = new ZmFilePath();
			fileName = orgName;
			String savePath = file.getPath() + File.separator + fileName;
			File savefile = new File(savePath);
			FileCopyUtils.copy(multipartFile.getBytes(), savefile);
			filePath.setPath(savePath);
			filePath.setWaybillId(waybillId[0]);
			filePath.setFileName(fileName);
			zmFilePathService.save(filePath);
			String dbpath = null;
			if(oConvertUtils.isNotEmpty(bizPath)){
				dbpath = bizPath + File.separator + fileName;
			}else{
				dbpath = fileName;
			}
			if (dbpath.contains("\\")) {
				dbpath = dbpath.replace("\\", "/");
			}

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

        return Result.OK("上传成功");
    }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "文件路径-通过运单id查询")
	 @ApiOperation(value="文件路径-通过运单id查询", notes="文件路径-通过运单id查询")
	 @GetMapping(value = "/queryByWaybillId")
	 public Result<?> queryByWaybillId(@RequestParam(name="id",required=true) String id) {
		 List<ZmFilePath> zmFilePath = zmFilePathService.list(new QueryWrapper<ZmFilePath>().eq("waybill_id",id));
		 if(zmFilePath==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(zmFilePath);
	 }
	 /**
	  *  编辑名称
	  *
	  * @param zmFilePath
	  * @return
	  */
	 @AutoLog(value = "文件路径-编辑")
	 @ApiOperation(value="文件路径-编辑", notes="文件路径-编辑")
	 @PutMapping(value = "/editFileName")
	 public Result<?> editFileName(@RequestBody ZmFilePath zmFilePath) {
		 ZmFilePath originFile = zmFilePathService.getById(zmFilePath.getId());
		 //想命名的原文件的路径
		 File file = new File(originFile.getPath());
		 //将原文件更改为f:\a\b.xlsx，其中路径是必要的。注意
		 file.renameTo(new File(uploadpath+"//"+zmFilePath.getFileName()));
		 zmFilePath.setPath(uploadpath+"//"+zmFilePath.getFileName());
		 if (zmFilePath.getPath().contains("//")) {
			 String path = zmFilePath.getPath();
			 zmFilePath.setPath(path.replace("//", "\\")) ;
		 }
		 zmFilePathService.updateById(zmFilePath);
		 return Result.OK("重命名成功!");
	 }
 }