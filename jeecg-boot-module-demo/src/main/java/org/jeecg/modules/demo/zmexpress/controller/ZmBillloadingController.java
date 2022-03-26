package org.jeecg.modules.demo.zmexpress.controller;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.*;
import org.jeecg.modules.demo.zmexpress.service.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.zmexpress.vo.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.def.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
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
 * @Description: 提单表
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Api(tags="提单表")
@RestController
@RequestMapping("/zmexpress/zmBillloading")
@Slf4j
public class ZmBillloadingController extends JeecgController<ZmBillloading, IZmBillloadingService> {
	@Autowired
	private IZmBillloadingService zmBillloadingService;
	@Autowired
	private IZmWaybillsService zmWaybillService;
	@Autowired
	private IZmLogisticsInformationService zmLogisticsInformationService;
	@Autowired
	private IZmGoodCaseService zmGoodCaseService;
	@Autowired
	private IZmServiceService zmServiceService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmBillloading
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "提单表-分页列表查询")
	@ApiOperation(value="提单表-分页列表查询", notes="提单表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmBillloading zmBillloading,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmBillloading> queryWrapper = QueryGenerator.initQueryWrapper(zmBillloading, req.getParameterMap());
		Page<ZmBillloading> page = new Page<ZmBillloading>(pageNo, pageSize);
		IPage<ZmBillloading> pageList = zmBillloadingService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmBillloading
	 * @return
	 */
	@AutoLog(value = "提单表-添加")
	@ApiOperation(value="提单表-添加", notes="提单表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmBillloading zmBillloading) {
		zmBillloading.setStatus("待定仓");
		zmBillloadingService.save(zmBillloading);
//		QueryWrapper<ZmBillloading> queryWrapper = new QueryWrapper<>();
//		queryWrapper.eq("billnum",zmBillloading.getBillnum());
//		zmBillloadingService.getOne(queryWrapper);
		ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
		zmLogisticsInformation.setOrderId(zmBillloading.getId());
		zmLogisticsInformation.setMsg("提单已创建");
		zmLogisticsInformationService.save(zmLogisticsInformation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmBillloading
	 * @return
	 */
	@AutoLog(value = "提单表-编辑")
	@ApiOperation(value="提单表-编辑", notes="提单表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmBillloading zmBillloading) {
		zmBillloadingService.updateById(zmBillloading);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "提单表-通过id删除")
	@ApiOperation(value="提单表-通过id删除", notes="提单表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmBillloadingService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "提单表-批量删除")
	@ApiOperation(value="提单表-批量删除", notes="提单表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmBillloadingService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "提单表-通过id查询")
	@ApiOperation(value="提单表-通过id查询", notes="提单表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmBillloading zmBillloading = zmBillloadingService.getById(id);
		if(zmBillloading==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmBillloading);
	}

	 /**
	  *  查询可用提单
	  *
	  *
	  */
	 @AutoLog(value = "提单表-查询可用提单")
	 @ApiOperation(value="提单表-查询可用提单", notes="提单表-查询可用提单")
	 @GetMapping(value = "/queryList")
	 public Result<?> queryList() {
		 QueryWrapper<ZmBillloading> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("status","待订仓")
				 .or().eq("status","已定仓");
		 List<ZmBillloading> list = zmBillloadingService.list(queryWrapper);
		 if(list==null) {
			 return Result.error("当前没有提单可用，请创建提单！");
		 }
		 return Result.OK(list);
	 }
	 /**
	  *  查询该提单的所有运单
	  *
	  *
	  */
	 @AutoLog(value = "查询该提单的所有运单")
	 @ApiOperation(value="查询该提单的所有运单", notes="查询该提单的所有运单")
	 @GetMapping(value = "/queryWaybillList")
	 public Result<?> queryWaybillList(@RequestParam(name = "billnum")String billnum) {
		 QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("bill_id",billnum);
		 List<ZmWaybills> list = zmWaybillService.list(queryWrapper);
		 if(list==null) {
			 return Result.error("请创建提单");
		 }
		 return Result.OK(list);
	 }
    /**
    * 导出excel
    *
    * @param request
    * @param zmBillloading
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmBillloading zmBillloading) {
        return super.exportXls(request, zmBillloading, ZmBillloading.class, "提单表");
    }

	 /**
	  * 通过excel导入数据
	  *
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	 protected Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
//			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<ZmBillloading> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmBillloading.class, params);
				 //update-begin-author:taoyan date:20190528 for:批量插入数据
				 long start = System.currentTimeMillis();
				 String[] str = {"待定仓","已定仓","已出发","已到站","已清关","已完成","已作废"};
				 //添加物流信息
				 for (ZmBillloading zmBillloading : list) {
					 zmBillloading.setStatus(str[Integer.parseInt(zmBillloading.getStatus())]);
					 zmBillloading.setBillnum(zmBillloading.getInBillnum());
					 ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
					 zmLogisticsInformation.setOrderId(zmBillloading.getBillnum());
					 zmLogisticsInformation.setMsg("提单已创建");
					 zmLogisticsInformationService.save(zmLogisticsInformation);
				 }
				 service.saveBatch(list);
				 //400条 saveBatch消耗时间1592毫秒  循环插入消耗时间1947毫秒
				 //1200条  saveBatch消耗时间3687毫秒 循环插入消耗时间5212毫秒
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 //update-end-author:taoyan date:20190528 for:批量插入数据

				 return Result.ok("文件导入成功！数据行数：" + list.size());
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



	 /**
	  * @title tostatus
	  * @description  改变状态
	  * @author zzh
	  * @param: ids
	  * @updateTime 2021/12/23 15:13
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @PutMapping(value = "/tostatus")
	 public Result<?> tostatus(@RequestBody ZmToStatus zmToStatus) {
		 //判断当前订单状态 和 需要转换的状态
		 String ids = zmToStatus.getIds().replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 String status ;
		 String[] str = {"待定仓","已定仓","已出发","已到站","已清关","已完成","已作废"};
 		 for (String id : list) {
			 ZmBillloading zmBillloading = zmBillloadingService.getById(id);
			 if (zmBillloading == null) {
				 return Result.error("未找到对应数据");
			 }
			 ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();

			 status = str[Integer.parseInt(zmToStatus.getStatus())];
			 if (status.equals("已作废"))
				 zmBillloading.setStatus("待定仓");
			 else
				 zmBillloading.setStatus(status);

			 zmBillloadingService.updateById(zmBillloading);
		 }
		 return Result.OK("操作成功！");
	 }

	 /**
	  * @title toCancel
	  * @description  运单作废
	  * @author zzh
	  * @param: zmReason
	  * @updateTime 2021/12/23 16:35
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @PutMapping(value = "/toCancel")
	 public Result<?> toCancel (@RequestBody ZmReason zmReason) {
		 //判断当前订单状态 和 需要转换的状态
		 String ids = zmReason.getIds().replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 for (String id : list) {
			 ZmBillloading zmBillloading = zmBillloadingService.getById(id);

			 if (zmBillloading == null) {
				 return Result.error("未找到对应数据");
			 }
			 zmBillloading.setStatus("已作废");
			 zmBillloading.setRemark("作废原因:" +zmReason.getRemark());
			 zmBillloadingService.updateById(zmBillloading);
		 }
		 return Result.OK("此提单已经作废！");
	 }

	 /**
	  * @title getTotalbill
	  * @description  获取总提单数
	  * @author zzh
	  * @updateTime 2021/12/25 17:02
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping(value = "/getTotalBill")
	 public Result<?> getTotalBill() {
		 int res = zmBillloadingService.count();
		 return Result.OK(res);
	 }

	 /**
	  * @title statisticsByStatus
	  * @description 获取按状态分类数量
	  * @author zzh
	  * @updateTime 2022/1/6 23:02
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping("/statisticsByStatus")
	 public Result<?> statisticsByStatus() {
		 String[] res  = new String[8];
		 int count = 0;
		 QueryWrapper<ZmBillloading> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("status","待定仓");
		 count = zmBillloadingService.count(queryWrapper);
		 res[0]="待定仓("+count+")";
		 queryWrapper.clear();
		 queryWrapper.eq("status","已定仓");
		 count = zmBillloadingService.count(queryWrapper);
		 res[1]="已定仓("+count+")";
		 queryWrapper.clear();
		 queryWrapper.eq("status","已出发");
		 count = zmBillloadingService.count(queryWrapper);
		 res[2]="已出发("+count+")";
		 queryWrapper.clear();
		 queryWrapper.eq("status","已到站");
		 count = zmBillloadingService.count(queryWrapper);
		 res[3]="已到站("+count+")";
		 queryWrapper.clear();
		 queryWrapper.eq("status","已清关");
		 count = zmBillloadingService.count(queryWrapper);
		 res[4]="已清关("+count+")";
		 queryWrapper.clear();
		 queryWrapper.eq("status","已完成");
		 count = zmBillloadingService.count(queryWrapper);
		 res[5]="已完成("+count+")";
		 queryWrapper.clear();
		 queryWrapper.eq("status","已作废");
		 count = zmBillloadingService.count(queryWrapper);
		 res[6]="已作废("+count+")";
		 count = zmBillloadingService.count();
		 res[7]="全部("+count+")";
		 return  Result.OK(res);
	 }

	 /**
	  * 导出excel
	  *
	  * @param billId
	  */
	 @RequestMapping(value = "/exportCustomsInformation")
	 public ModelAndView exportCustomsInformation(@RequestParam(name = "billId")String billId) {
		 // Step.1 组装查询条件查询数据
		 QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("bill_id", billId);
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 //Step.2 获取导出运单数据
		 List<ZmWaybills> queryList = zmWaybillService.list(queryWrapper);
		 //Step.3 获取导出运单中的货箱数据
		 List<ZmExportCustoms> zmExportCustomsList = new ArrayList<>() ;
		 List<ZmCaseKind> zmCaseKinds = new ArrayList<>();

		 Map<String,ZmExportCustoms> zmCaseKindMap = new HashMap<>();
		 int sumPiece=0;
		 double sumVolumn = 0;
		 double sumGrossWeight = 0;
		 double sumNetWeight = 0;
		 double sumPrice = 0;
		 for (ZmWaybills zmWaybills : queryList) {
			 QueryWrapper<ZmGoodCase> goodCaseQueryWrapper = new QueryWrapper<>();
			 goodCaseQueryWrapper.eq("fbaid", zmWaybills.getId());
			 //取出该运单的货箱
			 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(goodCaseQueryWrapper);
			 for (ZmGoodCase zmGoodCase : zmGoodCases) {
				 if (zmCaseKindMap.containsKey(zmGoodCase.getCnName())){
					 ZmExportCustoms zmCaseKind=zmCaseKindMap.get(zmGoodCase.getCnName());
					 //申报数量
					 int number = zmCaseKind.getDeclaredNumber()+zmGoodCase.getDeclaredNumber();
					 //累计件数
					 int piece = zmCaseKind.getPiece()+1;
					 sumPiece+=piece;
					 //累计体积
					 double volumn = zmCaseKind.getVolumn()+(Double.parseDouble(zmGoodCase.getHeightActually())*Double.parseDouble(zmGoodCase.getWidthActually())*Double.parseDouble(zmGoodCase.getLengthActually()))/1000000;
					 sumVolumn+=volumn;
					 //累计毛重
					 double grossWeight = zmCaseKind.getGrossWeight()+Double.parseDouble(zmGoodCase.getWeightActually());
					 sumGrossWeight+=grossWeight;
					 //累计净重
					 double netWeight = zmCaseKind.getNetWeight()+zmGoodCase.getWeight();
					 sumNetWeight += netWeight;
					 zmCaseKind.setGrossWeight(grossWeight);
					 zmCaseKind.setNetWeight(netWeight);
					 zmCaseKind.setVolumn(volumn);
					 zmCaseKind.setPiece(piece);
					 zmCaseKind.setDeclaredNumber(number);
					 zmCaseKindMap.put(zmGoodCase.getCnName(),zmCaseKind);
				 }else{
					 ZmExportCustoms zmExportCustoms = new ZmExportCustoms();

					 BeanUtils.copyProperties(zmGoodCase,zmExportCustoms);
					 zmExportCustoms.setWaybillId(zmWaybills.getOrderId());
					 zmExportCustoms.setGrossWeight(Double.parseDouble(zmGoodCase.getWeightActually()));
					 sumGrossWeight+=Double.parseDouble(zmGoodCase.getWeightActually());
					 zmExportCustoms.setNetWeight(zmGoodCase.getWeight());
					 sumNetWeight+=zmGoodCase.getWeight();
					 zmExportCustoms.setVolumn((Double.parseDouble(zmGoodCase.getHeightActually())*Double.parseDouble(zmGoodCase.getWidthActually())*Double.parseDouble(zmGoodCase.getLengthActually()))/1000000);
					 sumVolumn+=(Double.parseDouble(zmGoodCase.getHeightActually())*Double.parseDouble(zmGoodCase.getWidthActually())*Double.parseDouble(zmGoodCase.getLengthActually()))/1000000;
					 zmExportCustoms.setPiece(1);
					 sumPiece+=1;
					 zmCaseKindMap.put(zmGoodCase.getCnName(),zmExportCustoms);
				 }
			 }
			 Iterator<String> iterator = zmCaseKindMap.keySet().iterator();
			 while (iterator.hasNext()) {
				 String key = iterator.next();
				 ZmExportCustoms value = zmCaseKindMap.get(key);
				 zmExportCustomsList.add(value);
			 }
		 }


//      // Step.3.1 创建模板
		 Map<String, Object> map = new HashMap<String, Object>();
//
		 map.put("sumNetWeight",sumNetWeight);
		 map.put("sumGrossWeight",sumGrossWeight);
		 map.put("sumVolumn",sumVolumn);
		 map.put("sumPiece",sumPiece);
		 map.put("sumPrice",sumPrice);
		 List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		 for (ZmExportCustoms zmExportCustoms : zmExportCustomsList) {
			 Map<String, Object> m = new HashMap<String, Object>();
			 m.put("id", zmExportCustoms.getWaybillId());
			 m.put("piece",zmExportCustoms.getPiece());
			 m.put("volumn", zmExportCustoms.getVolumn());
			 m.put("grossWeight", zmExportCustoms.getGrossWeight());
			 m.put("netWeight", zmExportCustoms.getNetWeight());
			 m.put("totalPrice", Double.parseDouble(zmExportCustoms.getDeclaredPrice())*zmExportCustoms.getDeclaredNumber());
			 m.put("enName", zmExportCustoms.getEnName());
			 m.put("cnName", zmExportCustoms.getCnName());
			 m.put("declaredPrice", zmExportCustoms.getDeclaredPrice());
			 m.put("declaredNumber", zmExportCustoms.getDeclaredNumber());
			 m.put("material", zmExportCustoms.getMaterial());
			 m.put("hscode", zmExportCustoms.getHscode());
			 m.put("application", zmExportCustoms.getApplication());
			 m.put("brand", zmExportCustoms.getBrand());
			 m.put("type", zmExportCustoms.getType());
			 m.put("model", zmExportCustoms.getModel());
			 m.put("link", zmExportCustoms.getLink());
			 m.put("remark", zmExportCustoms.getRemark());
			 m.put("picture", zmExportCustoms.getPicture());
			 listMap.add(m);
		 }
		 map.put("maplist", listMap);
		 TemplateExportParams params = new TemplateExportParams();
		 params.setTemplateUrl("D:\\zm\\jeecg-boot\\jeecg-boot-module-demo\\src\\main\\resources\\exportTemplate\\templateExport.xlsx");

		 // Step.4 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgTemplateExcelView());
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      mv.addObject(TemplateExcelConstants.FILE_NAME, map.get("fbaid"));
//      mv.addObject(NormalExcelConstants.CLASS, ZmImportFbaPage.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("导入FBA表数据", "导出人:"+sysUser.getRealname(), "导入FBA表"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		 // map导出
		 mv.addObject(TemplateExcelConstants.PARAMS, params);
		 mv.addObject(TemplateExcelConstants.MAP_DATA, map);
		 return mv;
	 }

	 /**
	  * @title statistic
	  * @description 根据提单统计
	  * @author zzh
	  * @param: id
	  * @updateTime 2022/1/11 10:17
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping("/statistic")
	 public Result<?> statistic (@RequestParam(value = "id")String id){
		 ZmBillloading zmBillloading = zmBillloadingService.getById(id);

		 //查询根据提单查询货箱
		 QueryWrapper<ZmGoodCase> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("bill_id",id);
		 List<ZmGoodCase> goodCases = zmGoodCaseService.list(queryWrapper);

		 //查询根据提单查询运单
		 QueryWrapper<ZmWaybills> waybillsQueryWrapper = new QueryWrapper<>();
		 waybillsQueryWrapper.eq("bill_id",id);
		 int count = zmWaybillService.count(waybillsQueryWrapper);
		 //创建统计对象
		 ZmStatistics zmStatistics = new ZmStatistics();

		 //根据货箱计算统计
		 DecimalFormat df = new DecimalFormat("0.000");
		 double weight=0,volumeWeight=0,volume=0;
		 for (ZmGoodCase zmGoodCase : goodCases) {
			 weight+=zmGoodCase.getWeight();
			 //todo 数据
			 volumeWeight+=Double.parseDouble(zmGoodCase.getWeightVolume());
			 volume+=zmGoodCase.getWidth()*zmGoodCase.getHeight()*zmGoodCase.getLength()/1000000;
		 }
		 zmStatistics.setWaybillCount(count);
		 zmStatistics.setCaseCount(goodCases.size());
		 zmStatistics.setVolume(Double.parseDouble(df.format(volume)));
		 zmStatistics.setVolumeWeight(Double.parseDouble(df.format(volumeWeight)));
		 zmStatistics.setWeight(weight);

		 return Result.OK(zmStatistics);
	 }
	 /**
	  * @title getCaseListByBillId
	  * @description 通过提单id获取货箱
	  * @author zzh
	  * @param: id
	  * @updateTime 2022/1/11 11:07
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping("/getCaseListByBillId")
	 public Result<?> getCaseListByBillId(@RequestParam(value = "id")String id){
		 //构建货箱查询条件
		 QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
		 zmGoodCaseQueryWrapper.eq("bill_id",id);
		 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);

		 //取出该货箱的运单
		 List<ZmCaseDetail> zmCaseDetails = new ArrayList<>();

		 String pickData ;
		 String customerData;
		 String carrierData;
		 for (ZmGoodCase zmGoodCase : zmGoodCases) {
			 ZmWaybills zmWaybills = zmWaybillService.getById(zmGoodCase.getFbaid());
			 ZmCaseDetail zmCaseDetail = new ZmCaseDetail();
			 zmCaseDetail.setCaseId(zmGoodCase.getWaybillId()+"</br>"+zmGoodCase.getCaseid());
			 customerData = zmGoodCase.getWeight()+"(kg)</br>"+zmGoodCase.getLength()+"*"+
					 zmGoodCase.getWidth()+"*"+zmGoodCase.getHeight()+"(cm)";
			 pickData = zmGoodCase.getWeightActually()+"/"+zmGoodCase.getWeightVolume()+"(kg)</br>"+
					 zmGoodCase.getLengthActually()+"*"+zmGoodCase.getWidthActually()+"*"+zmGoodCase.getHeightActually()+"(cm)";
			 carrierData = zmWaybills.getCarrier()+"</br>"+zmWaybills.getTrackingNumber();
			 zmCaseDetail.setCustomerData(customerData);
			 zmCaseDetail.setPickData(pickData);
			 zmCaseDetail.setCarrier(carrierData);
			 zmCaseDetail.setId(zmGoodCase.getId());
			 zmCaseDetails.add(zmCaseDetail);
		 }
		 return Result.OK(zmCaseDetails);
	 }

	 /**
	  * @title moveOut
	  * @description 将箱子移除
	  * @author zzh
	  * @param: ids
	  * @updateTime 2022/1/16 20:41
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @PutMapping("/moveOut")
	 public Result<?> moveOut(@RequestBody String ids){
		 ids = ids.replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 for (String s : list) {
			 ZmGoodCase zmGoodCase = zmGoodCaseService.getById(s);
			 zmGoodCase.setBillId("");
			 zmGoodCaseService.updateById(zmGoodCase);
		 }
		 return Result.OK("移除成功!");
	 }

	 /**
	  * @title moveOutWaybill
	  * @description 移除运单 后期可以和上面的箱子整合加入判断条件即可
	  * @author zzh
	  * @param: ids
	  * @updateTime 2022/1/17 10:35
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @PutMapping("/moveOutWaybill")
	 public Result<?> moveOutWaybill(@RequestBody String ids){
		 ids = ids.replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 for (String s : list) {
			 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(new QueryWrapper<ZmGoodCase>().eq("fbaid", s));
			 for (ZmGoodCase zmGoodCase : zmGoodCases) {
				 zmGoodCase.setBillId("");
				 zmGoodCaseService.updateById(zmGoodCase);
			 }
			 ZmWaybills waybills = zmWaybillService.getById(s);
			 waybills.setBillId("");
			 zmWaybillService.updateById(waybills);
		 }

		 return Result.OK("移除成功!");
	 }
 }
