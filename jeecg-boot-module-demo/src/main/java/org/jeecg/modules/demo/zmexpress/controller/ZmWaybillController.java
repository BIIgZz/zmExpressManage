package org.jeecg.modules.demo.zmexpress.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import org.jeecg.modules.demo.zmexpress.entity.*;
import org.jeecg.modules.demo.zmexpress.mapper.ZmFbaWarehouseDetailMapper;
import org.jeecg.modules.demo.zmexpress.rules.ShopOrderRule;
import org.jeecg.modules.demo.zmexpress.service.*;
import org.jeecg.modules.demo.zmexpress.utils.InsertUtils;
import org.jeecg.modules.demo.zmexpress.utils.TimeUtils;
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
import org.jeecg.modules.demo.zmexpress.vo.ZmWaybillPage;
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
 * @Description: 运单表
 * @Author: jeecg-boot
 * @Date:   2021-12-13
 * @Version: V1.0
 */
@Api(tags="运单表")
@RestController
@RequestMapping("/zmexpress/zmWaybill")
@Slf4j
public class ZmWaybillController {
	 @Autowired
	 private IZmWaybillService zmWaybillService;
     @Autowired
     private IZmImportFbaService zmImportFbaService;
	 @Autowired
	 private IZmImportGoodService zmImportGoodService;
	 @Autowired
	 private InsertUtils insertUtils;
	 @Autowired
	 private ShopOrderRule shopOrderRule;
	 @Autowired
	 private IZmFbaWarehouseDetailService zmFbaWarehouseDetailService;
	 @Autowired
	 private IZmPickListService pickListService;
	 @Autowired
	 private IZmLogisticsInformationService zmLogisticsInformationService;

	 /**
	  * 分页列表查询
	  *
	  * @param zmWaybill
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "运单表-分页列表查询")
	 @ApiOperation(value = "运单表-分页列表查询", notes = "运单表-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(ZmWaybill zmWaybill,
									@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
									@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<ZmWaybill> queryWrapper = QueryGenerator.initQueryWrapper(zmWaybill, req.getParameterMap());
		 Page<ZmWaybill> page = new Page<ZmWaybill>(pageNo, pageSize);
		 IPage<ZmWaybill> pageList = zmWaybillService.page(page, queryWrapper);


		 return Result.OK(pageList);
	 }

	 /**
	  * 添加
	  *
	  * @param zmWaybillPage
	  * @return
	  */
	 @AutoLog(value = "运单表-添加")
	 @ApiOperation(value = "运单表-添加", notes = "运单表-添加")
	 @PostMapping(value = "/add")
	 public Result<?> add(@RequestBody ZmWaybillPage zmWaybillPage) {
		 ZmWaybill zmWaybill = new ZmWaybill();
		 BeanUtils.copyProperties(zmWaybillPage, zmWaybill);
		 ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
		 zmLogisticsInformation.setOrderId(zmWaybill.getWaybillId());
		 String msg = "已下单-"+TimeUtils.getNowTime()+"@";  //@结束符
		 zmLogisticsInformation.setMsg(msg);
		 zmLogisticsInformationService.save(zmLogisticsInformation);
		 zmWaybillService.saveMain(zmWaybill, zmWaybillPage.getZmImportGoodList());
		 return Result.OK("添加成功！");
	 }

	 /**
	  * 编辑
	  *
	  * @param zmWaybillPage
	  * @return
	  */
	 @AutoLog(value = "运单表-编辑")
	 @ApiOperation(value = "运单表-编辑", notes = "运单表-编辑")
	 @PutMapping(value = "/edit")
	 public Result<?> edit(@RequestBody ZmWaybillPage zmWaybillPage) {
		 ZmWaybill zmWaybill = new ZmWaybill();
		 BeanUtils.copyProperties(zmWaybillPage, zmWaybill);
		 ZmWaybill zmWaybillEntity = zmWaybillService.getById(zmWaybill.getId());
		 if (zmWaybillEntity == null) {
			 return Result.error("未找到对应数据");
		 }
		 zmWaybillService.updateMain(zmWaybill, zmWaybillPage.getZmImportGoodList());
		 return Result.OK("编辑成功!");
	 }

	 /**
	  * 通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "运单表-通过id删除")
	 @ApiOperation(value = "运单表-通过id删除", notes = "运单表-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		 zmWaybillService.delMain(id);
		 return Result.OK("删除成功!");
	 }

	 /**
	  * 批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "运单表-批量删除")
	 @ApiOperation(value = "运单表-批量删除", notes = "运单表-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		 this.zmWaybillService.delBatchMain(Arrays.asList(ids.split(",")));
		 return Result.OK("批量删除成功！");
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "运单表-通过id查询")
	 @ApiOperation(value = "运单表-通过id查询", notes = "运单表-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		 ZmWaybill zmWaybill = zmWaybillService.getById(id);
		 if (zmWaybill == null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(zmWaybill);

	 }


	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "货柜详情通过主表ID查询")
	 @ApiOperation(value = "货柜详情主表ID查询", notes = "货柜详情-通主表ID查询")
	 @GetMapping(value = "/queryZmImportGoodByMainId")
	 public Result<?> queryZmImportGoodListByMainId(@RequestParam(name = "id", required = true) String id) {
		 List<ZmImportGood> zmImportGoodList = zmImportGoodService.selectByMainId(id);
		 return Result.OK(zmImportGoodList);
	 }

	 /**
	  * 导出excel
	  *
	  * @param request
	  * @param zmWaybill
	  */
	 @RequestMapping(value = "/exportXls")
	 public ModelAndView exportXls(HttpServletRequest request, ZmWaybill zmWaybill) {
		 // Step.1 组装查询条件查询数据
		 QueryWrapper<ZmWaybill> queryWrapper = QueryGenerator.initQueryWrapper(zmWaybill, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 //Step.2 获取导出数据
		 List<ZmWaybill> queryList = zmWaybillService.list(queryWrapper);
		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 List<ZmWaybill> zmWaybillList = new ArrayList<ZmWaybill>();
		 if (oConvertUtils.isEmpty(selections)) {
			 zmWaybillList = queryList;
		 } else {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 zmWaybillList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 }

		 // Step.3 组装pageList
		 List<ZmWaybillPage> pageList = new ArrayList<ZmWaybillPage>();
		 for (ZmWaybill main : zmWaybillList) {
			 ZmWaybillPage vo = new ZmWaybillPage();
			 BeanUtils.copyProperties(main, vo);
			 List<ZmImportGood> zmImportGoodList = zmImportGoodService.selectByMainId(main.getId());
			 vo.setZmImportGoodList(zmImportGoodList);
			 pageList.add(vo);
		 }

		 // Step.4 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "运单表列表");
		 mv.addObject(NormalExcelConstants.CLASS, ZmWaybillPage.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("运单表数据", "导出人:" + sysUser.getRealname(), "运单表"));
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
				 List<ZmWaybillPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmWaybillPage.class, params);
				 for (ZmWaybillPage page : list) {
					 ZmWaybill po = new ZmWaybill();
					 BeanUtils.copyProperties(page, po);
					 ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
					 zmLogisticsInformation.setOrderId(po.getWaybillId());
					 String msg = "已下单-"+TimeUtils.getNowTime()+"@";  //@结束符
					 zmLogisticsInformation.setMsg(msg);
					 zmLogisticsInformationService.save(zmLogisticsInformation);
					 zmWaybillService.saveMain(po, page.getZmImportGoodList());
				 }

				 return Result.OK("文件导入成功！数据行数:" + list.size());
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
		 return Result.OK("文件导入失败！");
	 }
	 /**
	  * 通过excel模板导入数据
	  *
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping(value = "/importExcelSingle", method = RequestMethod.POST)
	 public Result<?> importExcelSingle(HttpServletRequest request, HttpServletResponse response) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 //key-value
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 cn.afterturn.easypoi.excel.entity.ImportParams params = new cn.afterturn.easypoi.excel.entity.ImportParams();
			 params.setKeyMark(":");
			 params.setReadSingleCell(true);
			 params.setTitleRows(17);
			 try {
				 ExcelImportResult<Map> result = cn.afterturn.easypoi.excel.ExcelImportUtil.importExcelMore(file.getInputStream(), Map.class, params);
				 List<ZmImportGood> importGoodList = new ArrayList<>();
				 Map<String, Object> map = result.getMap();
				 Map map1 = result.getList().get(0);

				 for (int i = 0; i < result.getList().size(); i++) {
					 ZmImportGood zmImportGood = new ZmImportGood();
					 zmImportGood.setFbaid((String) map.get("FBA ID*:"));
					 zmImportGood.setApplication((String) result.getList().get(i).get("产品用途*"));
					 zmImportGood.setCaseid((String) result.getList().get(i).get("货箱编号*"));
					 zmImportGood.setWeight(Double.parseDouble(result.getList().get(i).get("货箱重量(KG)*").toString()));
					 zmImportGood.setLength(Double.parseDouble(result.getList().get(i).get("货箱长度(CM)*").toString()));
//					zmImportGood.setWeight((String) map.get("货箱宽度(CM)*:"));
					 zmImportGood.setHeight(Double.parseDouble(result.getList().get(i).get("货箱高度(CM)*").toString()));
					 zmImportGood.setEnName((String) result.getList().get(i).get("产品英文品名*"));
					 zmImportGood.setCnName((String) result.getList().get(i).get("产品中文品名*"));
					 zmImportGood.setDeclaredPrice(result.getList().get(i).get("产品申报单价*").toString());
					 zmImportGood.setDeclaredNumber((Integer) result.getList().get(i).get("产品申报数量*"));
					 zmImportGood.setMaterial(result.getList().get(i).get("产品材质*").toString());
					 try {
						 Object o = result.getList().get(i).get("产品海关编码*");
						 double v = Double.parseDouble(o.toString());
						 NumberFormat numberFormat = NumberFormat.getInstance();
						 numberFormat.setGroupingUsed(false);
						 String format = numberFormat.format(v);
						 zmImportGood.setHscode(format);
					 } catch (NumberFormatException e) {
						 zmImportGood.setHscode(result.getList().get(i).get("产品海关编码*").toString());
					 }
					 zmImportGood.setBrand((String) result.getList().get(i).get("产品品牌*"));
					 zmImportGood.setType((String) result.getList().get(i).get("品牌类型*"));
					 zmImportGood.setModel((String) result.getList().get(i).get("产品型号*"));
//					zmImportGood.set((String) map.get("PO Number*"));
					 zmImportGood.setLink((String) result.getList().get(i).get("产品销售链接"));
					 zmImportGood.setPrice((Double) result.getList().get(i).get("产品销售价格"));
					 zmImportGood.setPicture((String) result.getList().get(i).get("产品图片链接"));
					 importGoodList.add(zmImportGood);
				 }

				 ZmWaybill zmWaybill = new ZmWaybill();
//				ZmImportFbaPage zmImportFbaPage = new ZmImportFbaPage();
				 zmWaybill.setAddress((String) map.get("收件人地址一*:"));
				 zmWaybill.setFbaId((String) map.get("FBA ID*:"));
				 zmWaybill.setOrderId((String) map.get("客户订单号:"));
				 zmWaybill.setWarehouseId((String) map.get("地址库编码*:"));
				 zmWaybill.setService((String) map.get("服务*:"));
				 zmWaybill.setName((String) map.get("收件人姓名*:"));
//				 zmWaybill.setCity((String) map.get("收件人城市*:"));
//				 zmWaybill.setProvince((String) map.get("收件人省份/州*(二字代码):"));
//				 zmWaybill.setPostcode((String) map.get("收件人邮编*:"));
//				 zmWaybill.setCountryCode((String) map.get("收件人国家代码(二字代码)*:"));
//				 zmWaybill.setTel((String) map.get("收件人电话:"));
//				 zmWaybill.setEmail((String) map.get("收件人邮箱:"));
//				zmImportFbaPage.setCaseNumber(Integer.parseInt((String) map.get("总箱数*:")) );
//				 if (map.get("带电*:").equals("是")) {
//					 zmWaybill.setElectrical(1);
//				 } else {
//					 zmWaybill.setElectrical(0);
//				 }
//				 if (map.get("带磁*:").equals("是")) {
//					 zmWaybill.setMagnetic(1);
//				 } else {
//					 zmWaybill.setMagnetic(0);
//				 }
//				 if (map.get("液体*:").equals("是")) {
//					 zmWaybill.setLiquid(1);
//				 } else {
//					 zmWaybill.setLiquid(0);
//				 }
//				 if (map.get("粉末*:").equals("是")) {
//					 zmWaybill.setPowder(1);
//				 } else {
//					 zmWaybill.setPowder(0);
//				 }
//				 if (map.get("危险品*:").equals("是")) {
//					 zmWaybill.setDangerous(1);
//				 } else {
//					 zmWaybill.setDangerous(0);
//				 }
//				 if (map.get("报关方式*:").equals("买单报关")) {
//					 zmWaybill.setCustomsEclaration(0);
//				 } else if (map.get("报关方式*:").equals("单独报关")) {
//					 zmWaybill.setCustomsEclaration(1);
//				 } else {
//					 zmWaybill.setCustomsEclaration(2);
//				 }
//				 if (map.get("清关方式:").equals("买单报关")) {
//					 zmWaybill.setCustomsEclaration(0);
//				 } else if (map.get("清关方式:").equals("单独报关")) {
//					 zmWaybill.setCustomsClearance(1);
//				 } else {
//					 zmWaybill.setCustomsClearance(2);
//				 }
//				 if (map.get("交税方式*:").equals("包税")) {
//					 zmWaybill.setTaxPayment(1);
//				 } else {
//					 zmWaybill.setTaxPayment(0);
//				 }
//				 zmWaybill.setVat((String) map.get("VAT号:"));
//				 zmWaybill.setReferenceNumber1((String) map.get("参考号一:"));
//				 zmWaybill.setReferenceNumber2((String) map.get("参考号二:"));
//				 zmWaybill.setNote((String) map.get("备注:"));
//				 zmWaybill.setAddressSender((String) map.get("发件人地址编码:"));
//				 zmWaybill.setNameSender((String) map.get("发件人姓名:"));
//				 zmWaybill.setCompanySender((String) map.get("发件人公司:"));
//				 zmWaybill.setCitySender((String) map.get("发件人城市:"));
//				 zmWaybill.setPostcodeSender((String) map.get("发件人邮编:"));
//				 zmWaybill.setCitySender((String) map.get("发件人国家代码(二字代码):"));
//				 zmWaybill.setProvinceSender((String) map.get("发件人省份/州:"));
//				 zmWaybill.setTelSender((String) map.get("发件人电话:"));
//				 zmWaybill.setEmailSender((String) map.get("发件人邮箱:"));
				zmWaybill.setWaybillId(shopOrderRule.autoOderid(""));
				 ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
				 zmLogisticsInformation.setOrderId(zmWaybill.getWaybillId());
				 String msg = "已下单-"+TimeUtils.getNowTime()+"@";  //@结束符
				 zmLogisticsInformation.setMsg(msg);
				 zmLogisticsInformationService.save(zmLogisticsInformation);
				 zmWaybillService.saveMain(zmWaybill, importGoodList);
//				BeanUtils.copyProperties(page, po);
				 //插入产品
				 List<ZmProduct> zmProductList = new ArrayList<>();
				 for (ZmImportGood zmImportGood : importGoodList) {
					 ZmProduct zmProduct = new ZmProduct();
					 zmProduct.setCnName(zmImportGood.getCnName());
					 zmProduct.setApplication((zmImportGood.getApplication()));
					 zmProduct.setBrand((zmImportGood.getBrand()));
					 zmProduct.setCreateBy(zmImportGood.getCreateBy());
					 zmProduct.setCreateTime(zmImportGood.getCreateTime());
					 zmProduct.setDeclaredPrice(Double.parseDouble(zmImportGood.getDeclaredPrice()));
					 zmProduct.setEnName(zmImportGood.getEnName());
					 zmProduct.setHscode(zmImportGood.getHscode());
					 zmProduct.setLink(zmImportGood.getLink());
					 zmProduct.setMaterial(zmImportGood.getMaterial());
					 zmProduct.setModel(zmImportGood.getModel());
					 zmProduct.setPicture(zmImportGood.getPicture());
					 zmProduct.setType(1);
					 zmProduct.setUpdateBy(zmImportGood.getUpdateBy());
					 zmProduct.setUpdateTime(zmImportGood.getUpdateTime());
					 zmProduct.setId(zmImportGood.getId());
					 zmProductList.add(zmProduct);
				 }
				 try {
					 insertUtils.insertProduct(zmProductList);
					 insertUtils.insertHscode(importGoodList);
				 } catch (Exception e) {
					 log.error(e.getMessage(), e);
				 }

				 return Result.OK("文件导入成功！");
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 if (e.getMessage().contains("fbaid"))
					 return Result.error("文件导入失败:fbaid重复");
				 return Result.error("文件导入失败:" + e.getMessage());
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

	 /**
	  * 修改订单状态 取消
	  *
	  * @param ids
	  * @return
	  */
	 @PutMapping(value = "/cancel")
	 public Result<?> cancel(@RequestBody String ids) {
		 //判断当前订单状态 和 需要转换的状态
		 ids = ids.replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 System.out.println(Arrays.asList(ids.trim().split(" ")));
		 for (String id : list) {
			 ZmWaybill zmWaybill = zmWaybillService.getById(id);
			 List<ZmImportGood> zmImportGoods = zmImportGoodService.selectByMainId(id);

			 if (zmWaybill == null) {
				 return Result.error("未找到对应数据");
			 }
			 zmWaybill.setStatus(5 + "");
			 zmWaybillService.updateMain(zmWaybill, zmImportGoods);
		 }
		 //切换状态
		 //存储状态
		 System.out.println("状态更新成功！");
		 return Result.OK("状态更新成功！");
	 }

	 /**x
	  * 放入提单 billlodin中
	  *
	  * @param id 运单id
	  * @param billId 提单id
	  * @return
	  */
	 @PutMapping(value = "/addToLading")
	 public Result<?> addToLading(@RequestParam(name = "id", required = true) String id,
								  @RequestParam(name = "billId", required = true) String billId) {
		 ZmWaybill zmWaybill = zmWaybillService.getById(id);
		 if (zmWaybill==null){
			 return Result.error("没有此运单！");
		 }
		 zmWaybill.setBillId(billId);
		 return Result.OK("加入提单成功！");
	 }
	 /**
	  * 放入出货单中
	  *
	  * @param id 运单id
	  * @param deliveryOrderNo 出货单id
	  * @return
	  */
	 @PutMapping(value = "/addToDeliveryOrder")
	 public Result<?> addToWaybill(@RequestParam(name = "id", required = true) String id,
								   @RequestParam(name = "deliveryOrderNo", required = true) String deliveryOrderNo) {
		 ZmWaybill zmWaybill = zmWaybillService.getById(id);
		 if (zmWaybill==null){
			 return Result.error("没有此运单！");
		 }
		 zmWaybill.setDeliveryOrderId(deliveryOrderNo);
		 zmWaybillService.updateById(zmWaybill);
		 return Result.OK("加入提单成功！");
	 }
	 /**
	  * 修改状态
	  * 判断此时运单的状态，根据传入操作决定下一步操作
	  * 通过waybillid从数据库中货箱数据返回数据
	  * @param id
	  * @param operate 将会设置成的状态
	  * @return
	  */
	 @PutMapping(value = "/changeStatus")
	 public  Result<?> changeStatus(@RequestParam(name = "id",required = true)String id,
									@RequestParam(name = "operate",required = true)String operate){
		 ZmWaybill zmWaybill = zmWaybillService.getById(id);
		 zmWaybill.setStatus(operate);
		 boolean res = zmWaybillService.updateById(zmWaybill);
		 return Result.OK("状态转换成功");
	 }

 }
