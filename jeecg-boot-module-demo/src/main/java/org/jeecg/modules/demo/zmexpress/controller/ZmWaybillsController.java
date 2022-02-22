package org.jeecg.modules.demo.zmexpress.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import org.jeecg.common.api.dto.LogDTO;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.demo.zmexpress.entity.*;
import org.jeecg.modules.demo.zmexpress.rules.ShopOrderRule;
import org.jeecg.modules.demo.zmexpress.service.*;
import org.jeecg.modules.demo.zmexpress.utils.ExcelReadHelper;
import org.jeecg.modules.demo.zmexpress.utils.InsertUtils;
import org.jeecg.modules.demo.zmexpress.utils.TimeUtils;
import org.jeecg.modules.demo.zmexpress.vo.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
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

import static org.jeecg.modules.demo.zmexpress.rules.ShopOrderRule.shopOrderRule;

/**
 * @Description: 运单全表
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@Api(tags="运单全表")
@RestController
@RequestMapping("/zmexpress/zmWaybills")
@Slf4j
public class ZmWaybillsController {
	@Autowired
	private IZmWaybillsService zmWaybillsService;
	@Autowired
	private IZmGoodCaseService zmGoodCaseService;
	@Autowired
	private IZmLogisticsInformationService zmLogisticsInformationService;
	@Autowired
	private ShopOrderRule shopOrderRule;
	@Autowired
	private IZmDeliveryOrderService zmDeliveryOrderService;
	@Autowired
	private IZmBillloadingService zmBillloadingService;
	@Autowired
	private IZmServiceService zmServiceService;
	@Autowired
	private IZmFilePathService zmFilePathService;
	@Autowired
	private BaseCommonService baseCommonService;

	@Value(value = "${jeecg.path.upload}")
	private String uploadpath;
	/**
	 * 分页列表查询
	 *
	 * @param zmWaybills
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "运单全表-分页列表查询")
	@ApiOperation(value = "运单全表-分页列表查询", notes = "运单全表-分页列表查询")
	@GetMapping(value = "/list")
	@PermissionData(pageComponent="zm/waybills/ZmWaybillsList")
	public Result<?> queryPageList(ZmWaybills zmWaybills,
								   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
								   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmWaybills> queryWrapper = QueryGenerator.initQueryWrapper(zmWaybills, req.getParameterMap());
		Page<ZmWaybills> page = new Page<ZmWaybills>(pageNo, pageSize);
		IPage<ZmWaybills> pageList = zmWaybillsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param zmWaybillsPage
	 * @return
	 */
	@AutoLog(value = "运单全表-添加")
	@ApiOperation(value = "运单全表-添加", notes = "运单全表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmWaybillsPage zmWaybillsPage) {
		ZmWaybills zmWaybills = new ZmWaybills();
		BeanUtils.copyProperties(zmWaybillsPage, zmWaybills);
		double totalWeight = 0;
		double totalVolume = 0;
		int caseNum = zmWaybillsPage.getZmGoodCaseList().size();
		double volumeWeight = 0;
		double chargeWeight = 0;
		for (ZmGoodCase zmGoodCase : zmWaybillsPage.getZmGoodCaseList()) {

			totalWeight += zmGoodCase.getWeight();
			totalVolume += zmGoodCase.getLength()*zmGoodCase.getHeight()*zmGoodCase.getWidth()/1000;

		}
		DecimalFormat df = new DecimalFormat("0.00");
		volumeWeight = Double.parseDouble(df.format(totalVolume/Double.parseDouble(zmWaybillsPage.getFoamingFactor())));
		zmWaybills.setWeightActual(totalWeight);
		zmWaybills.setVolume(totalVolume);
		zmWaybills.setWeightVolume(volumeWeight+"");
		zmWaybillsService.saveMain(zmWaybills, zmWaybillsPage.getZmGoodCaseList());
		return Result.OK("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param zmWaybillsPage
	 * @return
	 */
	@AutoLog(value = "运单全表-编辑")
	@ApiOperation(value = "运单全表-编辑", notes = "运单全表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmWaybillsPage zmWaybillsPage) {
		ZmWaybills zmWaybills = new ZmWaybills();
		BeanUtils.copyProperties(zmWaybillsPage, zmWaybills);
		ZmWaybills zmWaybillsEntity = zmWaybillsService.getById(zmWaybills.getId());
		if (zmWaybillsEntity == null) {
			return Result.error("未找到对应数据");
		}
		zmWaybillsService.updateMain(zmWaybills, zmWaybillsPage.getZmGoodCaseList());
		return Result.OK("编辑成功!");
	}


	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "运单全表-通过id删除")
	@ApiOperation(value = "运单全表-通过id删除", notes = "运单全表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		zmWaybillsService.delMain(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "运单全表-批量删除")
	@ApiOperation(value = "运单全表-批量删除", notes = "运单全表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		this.zmWaybillsService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "运单全表-通过id查询")
	@ApiOperation(value = "运单全表-通过id查询", notes = "运单全表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		ZmWaybills zmWaybills = zmWaybillsService.getById(id);
		if (zmWaybills == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmWaybills);
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货柜详情-通过主表ID查询")
	@ApiOperation(value = "货柜详情-通过主表ID查询", notes = "货柜详情-通过主表ID查询")
	@GetMapping(value = "/queryZmGoodCaseByMainId")
	public Result<?> queryZmGoodCaseListByMainId(@RequestParam(name = "id", required = true) String id) {
		List<ZmGoodCase> zmGoodCaseList = zmGoodCaseService.selectByMainId(id);
		IPage<ZmGoodCase> page = new Page<>();
		page.setRecords(zmGoodCaseList);
		page.setTotal(zmGoodCaseList.size());
		return Result.OK(page);
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param zmWaybills
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, ZmWaybills zmWaybills) {
		// Step.1 组装查询条件查询数据
		QueryWrapper<ZmWaybills> queryWrapper = QueryGenerator.initQueryWrapper(zmWaybills, request.getParameterMap());
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		//Step.2 获取导出数据
		List<ZmWaybills> queryList = zmWaybillsService.list(queryWrapper);
		// 过滤选中数据
		String selections = request.getParameter("selections");
		List<ZmWaybills> zmWaybillsList = new ArrayList<ZmWaybills>();
		if (oConvertUtils.isEmpty(selections)) {
			zmWaybillsList = queryList;
		} else {
			List<String> selectionList = Arrays.asList(selections.split(","));
			zmWaybillsList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		}

		// Step.3 组装pageList
		List<ZmWaybillsPage> pageList = new ArrayList<ZmWaybillsPage>();
		for (ZmWaybills main : zmWaybillsList) {
			ZmWaybillsPage vo = new ZmWaybillsPage();
			BeanUtils.copyProperties(main, vo);
//			List<ZmGoodCase> zmGoodCaseList = zmGoodCaseService.selectByMainId(main.getId());
//			vo.setZmGoodCaseList(zmGoodCaseList);
			pageList.add(vo);
		}

		// Step.4 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "运单全表列表");
		mv.addObject(NormalExcelConstants.CLASS, ZmWaybillsPage.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("运单全表数据", "导出人:" + sysUser.getRealname(), "运单全表"));
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
//			params.setTitleRows(2);
//			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<ZmWaybillsPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmWaybillsPage.class, params);
				for (ZmWaybillsPage page : list) {
					ZmWaybills po = new ZmWaybills();
					BeanUtils.copyProperties(page, po);
					zmWaybillsService.saveMain(po, page.getZmGoodCaseList());
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

			//获取订单号
			String orderId = shopOrderRule.autoOderid("");

			try {
				ExcelImportResult<Map> result = cn.afterturn.easypoi.excel.ExcelImportUtil.importExcelMore(file.getInputStream(), Map.class, params);
				List<ZmGoodCase> zmGoodCases = new ArrayList<>();
				Map<String, Object> map = result.getMap();
				Map map1 = result.getList().get(0);

				for (int i = 0; i < result.getList().size(); i++) {
					ZmGoodCase zmGoodCase = new ZmGoodCase();
					String caseNo = String.valueOf(i+1);
					zmGoodCase.setWaybillId(orderId+"U00"+caseNo);
					zmGoodCase.setFbaid((String) map.get("FBA ID*:"));
					zmGoodCase.setApplication((String) result.getList().get(i).get("产品用途*"));
					zmGoodCase.setCaseid((String) result.getList().get(i).get("货箱编号*"));
					zmGoodCase.setWeight(Double.parseDouble(result.getList().get(i).get("货箱重量(KG)*").toString()));
					zmGoodCase.setLength(Double.parseDouble(result.getList().get(i).get("货箱长度(CM)*").toString()));
					zmGoodCase.setWidth( Double.parseDouble(result.getList().get(i).get("货箱宽度(CM)*").toString()));
					zmGoodCase.setHeight(Double.parseDouble(result.getList().get(i).get("货箱高度(CM)*").toString()));
					zmGoodCase.setEnName((String) result.getList().get(i).get("产品英文品名*"));
					zmGoodCase.setCnName((String) result.getList().get(i).get("产品中文品名*"));
					zmGoodCase.setDeclaredPrice(result.getList().get(i).get("产品申报单价*").toString());
					zmGoodCase.setDeclaredNumber((Integer) result.getList().get(i).get("产品申报数量*"));
					zmGoodCase.setMaterial(result.getList().get(i).get("产品材质*").toString());
					try {
						Object o = result.getList().get(i).get("产品海关编码*");
						double v = Double.parseDouble(o.toString());
						NumberFormat numberFormat = NumberFormat.getInstance();
						numberFormat.setGroupingUsed(false);
						String format = numberFormat.format(v);
						zmGoodCase.setHscode(format);
					} catch (NumberFormatException e) {
						zmGoodCase.setHscode(result.getList().get(i).get("产品海关编码*").toString());
					}
					zmGoodCase.setBrand((String) result.getList().get(i).get("产品品牌*"));
					zmGoodCase.setType((String) result.getList().get(i).get("品牌类型*"));
					zmGoodCase.setModel((String) result.getList().get(i).get("产品型号*"));
//					zmImportGood.set((String) map.get("PO Number*"));
					zmGoodCase.setLink((String) result.getList().get(i).get("产品销售链接"));
					zmGoodCase.setPrice( (String)result.getList().get(i).get("产品销售价格"));
					zmGoodCase.setPicture((String) result.getList().get(i).get("产品图片链接"));
					zmGoodCases.add(zmGoodCase);
				}

				ZmWaybills zmWaybill = new ZmWaybills();
				ZmWaybillsPage zmWaybillsPage = new ZmWaybillsPage();
				zmWaybill.setWaybillId(orderId);
				zmWaybill.setRecipientAddressOne((String) map.get("收件人地址一*:"));
				zmWaybill.setFbaId((String) map.get("FBA ID*:"));
				zmWaybill.setOrderId((String) map.get("客户订单号:"));
				zmWaybill.setWarehouseId((String) map.get("地址库编码*:"));
				zmWaybill.setService((String) map.get("服务*:"));
				zmWaybill.setRecipient((String) map.get("收件人姓名*:"));
				zmWaybill.setRecipientCity((String) map.get("收件人城市*:"));
				zmWaybill.setRecipientState((String) map.get("收件人省份/州*(二字代码):"));
				zmWaybill.setRecipientZipOde((String) map.get("收件人邮编*:"));
				zmWaybill.setRecipientCountry((String) map.get("收件人国家代码(二字代码)*:"));
				zmWaybill.setRecipientTel((String) map.get("收件人电话:"));
				zmWaybill.setRecipientEmail((String) map.get("收件人邮箱:"));
				String itemAttributes = "";
				zmWaybill.setPieces((String) map.get("总箱数*:"));
				if (map.get("带电*:").equals("是")) {
					itemAttributes += "带电,";
				}
				if (map.get("带磁*:").equals("是")) {
					itemAttributes += "带磁,";
				}
				if (map.get("液体*:").equals("是")) {
					itemAttributes += "液体,";
				}
				if (map.get("粉末*:").equals("是")) {
					itemAttributes += "粉末,";
				}
				if (map.get("危险品*:").equals("是")) {
					itemAttributes += "危险品,";
				}
				zmWaybill.setItemAttributes(itemAttributes);
				if (map.get("报关方式*:").equals("买单报关")) {
					zmWaybill.setCustomsDeclaration("0");
				} else if (map.get("报关方式*:").equals("单独报关")) {
					zmWaybill.setCustomsDeclaration("1");
				} else {
					zmWaybill.setCustomsDeclaration("2");
				}
				if (map.get("清关方式:").equals("买单报关")) {
					zmWaybill.setCustomsClearance("0");
				} else if (map.get("清关方式:").equals("单独报关")) {
					zmWaybill.setCustomsClearance("1");
				}
				if (map.get("交税方式*:").equals("包税")) {
					zmWaybill.setTaxPayment("1");
				} else {
					zmWaybill.setTaxPayment("0");
				}
				zmWaybill.setVat((String) map.get("VAT号:"));
				zmWaybill.setReferenceNumberOne((String) map.get("参考号一:"));
				zmWaybill.setReferenceNumberTwo((String) map.get("参考号二:"));
				zmWaybill.setInRemark((String) map.get("备注:"));
				zmWaybill.setWarehouseId((String) map.get("发件人地址编码:"));
//				 zmWaybill.setNameSender((String) map.get("发件人姓名:"));
//				 zmWaybill.setCompanySender((String) map.get("发件人公司:"));
//				 zmWaybill.setCitySender((String) map.get("发件人城市:"));
//				 zmWaybill.setPostcodeSender((String) map.get("发件人邮编:"));
//				 zmWaybill.setCitySender((String) map.get("发件人国家代码(二字代码):"));
//				 zmWaybill.setProvinceSender((String) map.get("发件人省份/州:"));
//				 zmWaybill.setTelSender((String) map.get("发件人电话:"));
//				 zmWaybill.setEmailSender((String) map.get("发件人邮箱:"));
//				 zmWaybill.setWaybillId(shopOrderRule.autoOderid(""));
				ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
				zmLogisticsInformation.setOrderId(shopOrderRule.autoOderid(""));
				String msg = "已下单-" + TimeUtils.getNowTime();  //@结束符
				zmLogisticsInformation.setMsg(msg);
				zmLogisticsInformationService.save(zmLogisticsInformation);

				//查询服务
				QueryWrapper<ZmService> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("name",zmWaybill.getService());
				ZmService serviceServiceOne = zmServiceService.getOne(queryWrapper);
				zmWaybill.setFoamingFactor(serviceServiceOne.getBubble());
				zmWaybill.setBubbleSplittingRatio(serviceServiceOne.getBubbleSplittingRatio());
				double totalWeight = 0;
				double totalVolume = 0;
				int caseNum =zmGoodCases.size();
				double volumeWeight = 0;
				double chargeWeight = 0;
				for (ZmGoodCase zmGoodCasea : zmGoodCases) {
					totalWeight += zmGoodCasea.getWeight();
					totalVolume += zmGoodCasea.getLength()*zmGoodCasea.getHeight()*zmGoodCasea.getWidth()/1000000;
				}
				DecimalFormat df = new DecimalFormat("0.00");
				volumeWeight = Double.parseDouble(df.format(totalVolume/Double.parseDouble(zmWaybill.getFoamingFactor())));


				zmWaybill.setWeightActual(totalWeight);
				zmWaybill.setVolume(Double.parseDouble(String.format("%.2f",totalVolume)));
				zmWaybill.setWeightVolume(volumeWeight+"");
				zmWaybill.setPieces(caseNum+"");
				zmWaybill.setStatus("已下单");
				zmWaybillsService.saveMain(zmWaybill, zmGoodCases);
//				BeanUtils.copyProperties(page, po);
				//插入产品
				InsertUtils insertUtils = new InsertUtils();
				List<ZmProduct> zmProductList = new ArrayList<>();
				for (ZmGoodCase goodCase : zmGoodCases) {
					ZmProduct zmProduct = new ZmProduct();
					zmProduct.setCnName(goodCase.getCnName());
					zmProduct.setApplication((goodCase.getApplication()));
					zmProduct.setBrand((goodCase.getBrand()));
					zmProduct.setCreateBy(goodCase.getCreateBy());
					zmProduct.setCreateTime(goodCase.getCreateTime());
					zmProduct.setDeclaredPrice(Double.parseDouble(goodCase.getDeclaredPrice()));
					zmProduct.setEnName(goodCase.getEnName());
					zmProduct.setHscode(goodCase.getHscode());
					zmProduct.setLink(goodCase.getLink());
					zmProduct.setMaterial(goodCase.getMaterial());
					zmProduct.setModel(goodCase.getModel());
					zmProduct.setPicture(goodCase.getPicture());
					zmProduct.setPrice(goodCase.getPrice());
					zmProduct.setType(1);
					zmProduct.setUpdateBy(goodCase.getUpdateBy());
					zmProduct.setUpdateTime(goodCase.getUpdateTime());
					zmProduct.setId(goodCase.getId());
					zmProductList.add(zmProduct);
				}
				try {
//					insertUtils.insertProduct(zmProductList);
//					 insertUtils.insertHscode(zmGoodCases);
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
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			zmWaybill.setStatus("已取消");
			baseCommonService.addLog("取消订单",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}

		return Result.OK("订单取消成功！");
	}

	/**
	 * 重新发货
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/redeliver")
	public Result<?> redeliver(@RequestBody String ids) {
		//判断当前订单状态 和 需要转换的状态
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			zmWaybill.setStatus("已下单");
			baseCommonService.addLog("重新发货",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("发货成功！");
	}

	/**
	 * 拦截
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/intercept")
	public Result<?> intercept(@RequestBody String ids, @RequestParam(name = "remark") String remark) {
		//判断当前订单状态 和 需要转换的状态
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		System.out.println(Arrays.asList(ids.trim().split(" ")));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			zmWaybill.setStatus("已取消");
			remark+="\n"+zmWaybill.getRemark();
			zmWaybill.setRemark("拦截原因" + remark);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("发货成功！");
	}

	/**
	 * 按客户数据拣货
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/picking")
	public Result<?> picking(@RequestBody String ids) {
		//判断当前订单状态 和 需要转换的状态
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		System.out.println(Arrays.asList(ids.trim().split(" ")));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			double bubble = Double.parseDouble(zmServiceService.getOne(new QueryWrapper<ZmService>()
					.eq("name",zmWaybill.getService())).getBubble());
			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);
			double weightVolumn = 0;
			for (int i = 0; i < zmImportGoods.size(); i++) {
				zmImportGoods.get(i).setHeightActually(zmImportGoods.get(i).getHeight().toString());
				zmImportGoods.get(i).setWidthActually(zmImportGoods.get(i).getWeight().toString());
				zmImportGoods.get(i).setLengthActually(zmImportGoods.get(i).getLength().toString());
				zmImportGoods.get(i).setWeightActually(zmImportGoods.get(i).getWeight().toString());
				weightVolumn = zmImportGoods.get(i).getHeight()*zmImportGoods.get(i).getWidth()*zmImportGoods.get(i).getLength()/bubble;
				zmImportGoods.get(i).setWeightVolume(String.format("%.2f",weightVolumn));
			}

			zmWaybill.setStatus("已收货");
			baseCommonService.addLog("拣货成功",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);

		}
		return Result.OK("按客户数据拣货成功!");
	}
	/**
	 * 退件
	 *
	 * @param zmReason
	 * @return
	 */
	@PutMapping(value = "/rejection")
	public Result<?> rejection (@RequestBody ZmReason zmReason) {
		//判断当前订单状态 和 需要转换的状态
		String ids = zmReason.getIds().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			zmWaybill.setStatus("退件");
			zmWaybill.setRemark("退件原因:" +zmReason.getRemark());
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("退件成功！");
	}
	/**
	 * 转运
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/transfer")
	public Result<?> transfer (@RequestBody String ids ) {
		//判断当前订单状态 和 需要转换的状态
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		System.out.println(Arrays.asList(ids.trim().split(" ")));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);
			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			zmWaybill.setStatus("转运中");
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("状态调整为转运中成功！");
	}


	/**
	 * @title inToShip
	 * @description  加入出货单
	 * @author zzh
	 * @param: zmReason
	 * @updateTime 2021/12/23 10:17
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@PutMapping(value = "/inToShip")
	public Result<?> inToShip (@RequestBody ZmReceipt zmReceipt) {
		//获取运单id
		String ids = zmReceipt.getIds().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		//获取出货单
		ZmDeliveryOrder zmDeliveryOrder = zmDeliveryOrderService.getById(zmReceipt.getId());
		if (zmDeliveryOrder==null){
			return Result.error("暂时没有此出货单,请添加出货单");
		}
		for (String id : list) {
			//获取运单列表
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			baseCommonService.addLog("加入出货单，出货单号："+zmDeliveryOrder.getDeliveryOrderNo(),CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);

			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			zmWaybill.setDeliveryOrderId(zmReceipt.getId());
			zmWaybillsService.updateById(zmWaybill);
			//获取运单列表中的商品分箱信息·
			List<ZmGoodCase> zmGoodCases = zmGoodCaseService.selectByMainId(id);
			//将箱子放进出货单
			for (ZmGoodCase zmGoodCase : zmGoodCases) {
				zmGoodCase.setDeliveryOrderId(zmReceipt.getId());
				zmGoodCaseService.updateById(zmGoodCase);
			}
		}
		return Result.OK("加入出货单成功");
	}
	/**
	 * @title inToLading
	 * @description 放入提单
	 * @author zzh
	 * @param: zmReceipt
	 * @updateTime 2021/12/23 14:07
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@PutMapping(value = "/inToLading")
	public Result<?> inToLading(@RequestBody ZmReceipt zmReceipt) {
		//获取运单id
		String id = zmReceipt.getId().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(id.trim().split(" "));

		//获取提单  后期可能需要更改查询条件
		ZmBillloading zmBillloading = zmBillloadingService.getById(zmReceipt.getIds());
		if (zmBillloading==null){
			return Result.error("暂时没有此提单,请添加提单");
		}
		for (String waybillId : list) {

			ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
			//获取运单列表
			ZmWaybills zmWaybill = zmWaybillsService.getById(waybillId);
			if (zmWaybill == null) {
				return Result.error("未找到对应数据");
			}
			baseCommonService.addLog("加入提单，提单号："+zmBillloading.getBillnum(),CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);

			zmWaybill.setStatus("转运中");
			zmLogisticsInformation.setOrderId(zmWaybill.getId());
			zmLogisticsInformation.setMsg("从 "+zmBillloading.getDeparturePoint()+" 发往 "
					+zmBillloading.getSendSite()+" 预计 "+zmBillloading.getCustomsClearanceTime()+"开船");
			zmLogisticsInformationService.save(zmLogisticsInformation);
			zmWaybill.setBillId(zmBillloading.getId());
			//更新提单中的货箱
			QueryWrapper<ZmGoodCase> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("fbaid",zmWaybill.getId());
			List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(queryWrapper);
			for (ZmGoodCase zmGoodCase : zmGoodCases) {
				zmGoodCase.setBillId(zmReceipt.getIds());
				zmGoodCaseService.updateById(zmGoodCase);
			}
			zmWaybillsService.updateById(zmWaybill);
		}

		return Result.OK("加入提单成功");
	}

	/**
	 * @title getTotalWaybill
	 * @description 获取全部运单
	 * @author zzh 
	 * @updateTime 2021/12/25 16:38 
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@GetMapping(value = "/getTotalWaybill")
	public Result<?> getTotalWaybill() {
		int res = zmWaybillsService.count();
		return Result.OK(res);
	}


	/**
	 * @title getTotalWaybillByBill
	 * @description  根据提单号查找运单
	 * @author zzh
	 * @param: id
	 * @updateTime 2021/12/26 20:20
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@GetMapping(value = "/getTotalWaybillByBill")
	public Result<?> getTotalWaybillByBill(@RequestParam(name = "id", required = true)String id) {
		QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("bill_id",id);
		int res = zmWaybillsService.count(queryWrapper);
		return Result.OK(res);
	}

	/**
	 * @title getTotalWaybillByDelivery
	 * @description 通过出货单号查找运单
	 * @author zzh
	 * @param: id
	 * @updateTime 2022/1/2 11:27
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@GetMapping(value = "/getTotalWaybillByDelivery")
	public Result<?> getTotalWaybillByDelivery(@RequestParam(name = "id", required = true)String id) {
		QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("delivery_order_id",id);
		List<ZmWaybills> zmWaybills = zmWaybillsService.list(queryWrapper);
		return Result.OK(zmWaybills);
	}

	/**
	 * @title getWaybillByWaybillId
	 * @description 通过提单id查找运单表
	 * @author zzh
	 * @param: id
	 * @updateTime 2021/12/27 10:39
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@GetMapping(value = "/getWaybillByWaybillId")
	public Result<?> getWaybillByWaybillId(@RequestParam(name = "id", required = true)String id) {
		QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("waybill_id",id);
		List<ZmWaybills> list = zmWaybillsService.list(queryWrapper);
		return Result.OK(list);
	}

	/**
	 * @title queryListById
	 * @description 通过id获取运单全表
	 * @author zzh
	 * @param: ids
	 * @updateTime 2022/1/4 15:34
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@AutoLog(value = "运单全表-通过id查询")
	@ApiOperation(value = "运单全表-通过id查询", notes = "运单全表-通过id查询")
	@GetMapping(value = "/queryListById")
	public Result<?> queryListById(@RequestParam(name = "ids", required = true) String ids) {
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		List<ZmWaybills> zmWaybillsList = new ArrayList<>();
		for (String s : list) {
			ZmWaybills zmWaybills = zmWaybillsService.getById(s);
			zmWaybillsList.add(zmWaybills);
		}

		return Result.OK(zmWaybillsList);
	}

	/**
	 * @title statisticsById
	 * @description 统计
	 * @author zzh
	 * @param: id
	 * @updateTime 2022/1/6 22:37
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@GetMapping("/statisticsById")
	public Result<?> statisticsById(@RequestParam(name = "id", required = true) String id) {
		QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
		zmGoodCaseQueryWrapper.eq("fbaid",id);
		List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);
		ZmWaybills zmWaybills = zmWaybillsService.getById(id);

		QueryWrapper<ZmService> zmServiceQueryWrapper = new QueryWrapper<>();
		zmServiceQueryWrapper.eq("name",zmWaybills.getService());
		ZmService zmService = zmServiceService.getOne(zmServiceQueryWrapper);
		ZmWaybillStatistics zmWaybillStatistics = new ZmWaybillStatistics();
		double weight = 0;
		double volumn = 0;
		BigDecimal weightCharge = new BigDecimal("0");
		double volumeWeight = 0;
		double weightB = 0;
		DecimalFormat df = new DecimalFormat("0.000");
		if (!zmWaybills.getStatus().equals("已下单")){
			for (ZmGoodCase zmGoodCase : zmGoodCases) {
				weightB += Double.parseDouble(zmGoodCase.getWeightActually());
				weight += Double.parseDouble(zmGoodCase.getWeightActually())<zmService.getMinBoxWeight()?zmService.getMinBoxWeight():Double.parseDouble(zmGoodCase.getWeightActually());
				volumn += Double.parseDouble(zmGoodCase.getLengthActually())
						 *Double.parseDouble(zmGoodCase.getHeightActually())
						 *Double.parseDouble(zmGoodCase.getWidthActually())/1000000;
				volumeWeight += Double.parseDouble(zmGoodCase.getWeightVolume());
				weightCharge=weightCharge.add(zmGoodCase.getWeightCharge()) ;
			}
			if (weight < zmService.getMinTicketChargeWeight()){
//				double vm = Math.ceil(volumeWeight);
//				weightCharge.add(new BigDecimal(vm - (vm - zmService.getMinTicketChargeWeight()) * zmService.getBubbleSplittingRatio()) );
				weightCharge = new BigDecimal(zmService.getMinTicketChargeWeight());
			}

			if (weightCharge.compareTo(new BigDecimal(weightB))==-1){
				weightCharge=new BigDecimal(weightB);
			}
//			volumeWeight = Double.parseDouble(df.format(volumn*1000000/Double.parseDouble(zmService.getBubble())));
//			weightCharge = (volumeWeight-zmService.getMinBoxWeight()*zmGoodCases.size())*(1-zmService.getBubbleSplittingRatio())+zmService.getMinBoxWeight()*zmGoodCases.size();
		}
		double valueVolumeWeight = BigDecimal.valueOf(volumeWeight)
				.setScale(1, RoundingMode.UP)
				.doubleValue();
		zmWaybillStatistics.setVolume(Double.parseDouble(df.format(volumn)));
		zmWaybillStatistics.setVolumnWeight(valueVolumeWeight);
		zmWaybillStatistics.setWeight(weightB);
		zmWaybillStatistics.setFoamingFactor(zmService.getBubble());
		zmWaybillStatistics.setWeightCharge(Math.ceil(Double.parseDouble(df.format(weightCharge))));
		zmWaybillStatistics.setPieces(zmGoodCases.size());
		zmWaybillStatistics.setBubbleSplittingRatio(zmService.getBubbleSplittingRatio());
		zmWaybillStatistics.setMinBoxWeight(zmService.getMinBoxWeight());
		zmWaybillStatistics.setMinTicketChargeWeight(zmService.getMinTicketChargeWeight());
		zmWaybills.setWeightActual(weightB);
		zmWaybills.setWeightCharge(String.valueOf(Math.ceil(Double.parseDouble(df.format(weightCharge)))));
		zmWaybills.setVolume(Double.parseDouble(df.format(volumn)));
		zmWaybills.setWeightVolume(String.valueOf(valueVolumeWeight));
		zmWaybillsService.updateById(zmWaybills);
		return Result.OK(zmWaybillStatistics);
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
		String[] res  = new String[7];
		int count = 0;
		QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("status","已下单");
		count = zmWaybillsService.count(queryWrapper);
		res[0]="已下单("+count+")";
		queryWrapper.clear();
		queryWrapper.eq("status","已收货");
		count = zmWaybillsService.count(queryWrapper);
		res[1]="已收货("+count+")";
		queryWrapper.clear();
		queryWrapper.eq("status","转运中");
		count = zmWaybillsService.count(queryWrapper);
		res[2]="转运中("+count+")";
		queryWrapper.clear();
		queryWrapper.eq("status","已签收");
		count = zmWaybillsService.count(queryWrapper);
		res[3]="已签收("+count+")";
		queryWrapper.clear();
		queryWrapper.eq("status","退件");
		count = zmWaybillsService.count(queryWrapper);
		res[4]="退件("+count+")";
		queryWrapper.clear();
		queryWrapper.eq("status","已取消");
		count = zmWaybillsService.count(queryWrapper);
		res[5]="已取消("+count+")";
		count = zmWaybillsService.count();
		res[6]="全部("+count+")";
		return  Result.OK(res);
	}


	@RequestMapping("/import")
	/**
	 * @title statisticsByStatus
	 * @description 导入
	 * @author zzh
	 * @param: request
	 * @param: response
	 * @updateTime 2022/2/11 16:14
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	public Result<?> statisticsByStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String[] name = multipartRequest.getParameterValues("name");


		ExcelReadHelper readHelper = new ExcelReadHelper(fileMap.get("files[]"));
		ZmWaybillsPage zmWaybillsPage = new ZmWaybillsPage();
		zmWaybillsPage.setUsername(name[0]);
		zmWaybillsPage.setOrderId(readHelper.getValueAt("B",1));
		zmWaybillsPage.setService(readHelper.getValueAt("B",2));
		zmWaybillsPage.setWarehouseId(readHelper.getValueAt("B",3));
		zmWaybillsPage.setRecipient(readHelper.getValueAt("B",4));
		zmWaybillsPage.setCompany(readHelper.getValueAt("B",5));
		zmWaybillsPage.setRecipientAddressOne(readHelper.getValueAt("B",6));
		zmWaybillsPage.setReferenceNumberTwo(readHelper.getValueAt("B",7));
		zmWaybillsPage.setRecipientAddressTri(readHelper.getValueAt("B",8));
		zmWaybillsPage.setRecipientCity(readHelper.getValueAt("B",9));
		zmWaybillsPage.setRecipientState(readHelper.getValueAt("B",10));
		zmWaybillsPage.setRecipientZipOde(readHelper.getValueAt("B",11));
		zmWaybillsPage.setRecipientCountry(readHelper.getValueAt("B",12));
		zmWaybillsPage.setRecipientTel(readHelper.getValueAt("B",13));
		zmWaybillsPage.setRecipientEmail(readHelper.getValueAt("B",14));
		zmWaybillsPage.setPoNumber(readHelper.getValueAt("B",15));
		zmWaybillsPage.setPieces(readHelper.getValueAt("B",16));
		String property = "";
		String electric = readHelper.getValueAt("F",1);
		if (electric.equals("是"))
			property.concat("带电、");
		String magnetic = readHelper.getValueAt("F",2);
		if (magnetic.equals("是"))
			property.concat("带磁、");
		String liquid = readHelper.getValueAt("F",3);
		if (liquid.equals("是"))
			property.concat("液体、");
		String powder = readHelper.getValueAt("F",4);
		if (powder.equals("是"))
			property.concat("粉末、");
		String dangerous  = readHelper.getValueAt("F",5);
		if (dangerous.equals("是"))
			property.concat("危险品、");
		zmWaybillsPage.setAttributes(property);
		zmWaybillsPage.setCustomsDeclaration(readHelper.getValueAt("F",6));
		zmWaybillsPage.setCustomsClearance(readHelper.getValueAt("F",7));
		zmWaybillsPage.setTaxPayment(readHelper.getValueAt("F",8));
		zmWaybillsPage.setTermsDelivery(readHelper.getValueAt("F",9));
		zmWaybillsPage.setVat(readHelper.getValueAt("F",10));
		zmWaybillsPage.setReferenceNumberOne(readHelper.getValueAt("F",11));
		zmWaybillsPage.setReferenceNumberTwo(readHelper.getValueAt("F",12));
		zmWaybillsPage.setRemark(readHelper.getValueAt("F",13));

		zmWaybillsPage.setShop(readHelper.getValueAt("J",1));
		zmWaybillsPage.setSenderAddressCode(readHelper.getValueAt("J",2));
		zmWaybillsPage.setSenderName(readHelper.getValueAt("J",3));
		zmWaybillsPage.setSenderCompany(readHelper.getValueAt("J",4));
		zmWaybillsPage.setSenderAddressOne(readHelper.getValueAt("J",5));
		zmWaybillsPage.setSenderAddressTwo(readHelper.getValueAt("J",6));
		zmWaybillsPage.setSenderAddressTri(readHelper.getValueAt("J",7));
		zmWaybillsPage.setSenderCity(readHelper.getValueAt("J",8));
		zmWaybillsPage.setSenderState(readHelper.getValueAt("J",9));
		zmWaybillsPage.setSenderZipCode(readHelper.getValueAt("J",10));
		zmWaybillsPage.setSenderCountry(readHelper.getValueAt("J",11));
		zmWaybillsPage.setSenderTel(readHelper.getValueAt("J",12));
		zmWaybillsPage.setSenderEmail(readHelper.getValueAt("J",13));

		try {
			List<ZmGoodCase> zmGoodCases = new ArrayList<>();
			int vaildRows = readHelper.getVaildRows();
			for (int i = 18; i <= vaildRows; i++) {
				ZmGoodCase zmGoodCase = new ZmGoodCase();
				zmGoodCase.setCaseid(readHelper.getValueAt("A", i));
				zmGoodCase.setWeight(Double.parseDouble(readHelper.getValueAt("B", i)));
				zmGoodCase.setLength(Double.parseDouble(readHelper.getValueAt("C", i)));
				zmGoodCase.setWidth(Double.parseDouble(readHelper.getValueAt("D", i)));
				zmGoodCase.setHeight(Double.parseDouble(readHelper.getValueAt("E", i)));
				zmGoodCase.setEnName(readHelper.getValueAt("F", i));
				zmGoodCase.setCnName(readHelper.getValueAt("G", i));
				zmGoodCase.setDeclaredPrice( readHelper.getValueAt("H", i) );
				zmGoodCase.setDeclaredNumber(Integer.parseInt(readHelper.getValueAt("I", i)));
				zmGoodCase.setMaterial(readHelper.getValueAt("J", i));
				zmGoodCase.setHscode(readHelper.getValueAt("K", i));
				zmGoodCase.setApplication(readHelper.getValueAt("L", i));
				zmGoodCase.setBrand( readHelper.getValueAt("M", i));
				zmGoodCase.setType( readHelper.getValueAt("N", i) );
				zmGoodCase.setModel(readHelper.getValueAt("O", i));
				zmGoodCase.setLink(readHelper.getValueAt("P", i));
				zmGoodCase.setPrice( readHelper.getValueAt("Q", i).isEmpty()?readHelper.getValueAt("Q", i):"0");
//				zmGoodCase.setPicture(readHelper.getValueAt("R", i))	;
//				zmGoodCase.setWeight(Double.parseDouble(readHelper.getValueAt("S", i)));
				zmGoodCases.add(zmGoodCase);
			}
			String id = String.valueOf(System.currentTimeMillis());
			String orderId = shopOrderRule.autoOderid("");
			//处理货箱号简写
			if (zmGoodCases.get(0).getCaseid().length()>10){
				if (zmGoodCases.get(0).getCaseid().contains("-")){
					List<ZmGoodCase> newZmGoodCase = new ArrayList<>();
					for (ZmGoodCase zmGoodCase : zmGoodCases) {
						String[] us = zmGoodCase.getCaseid().split("U");
						String fba = us[0];
						String[] caseArea = us[1].split("-");
						int start = Integer.parseInt(caseArea[0]);
						int end = Integer.parseInt(caseArea[1]);
						for (int i = start; i <= end; i++) {
							ZmGoodCase zmGoodCase1 = new ZmGoodCase();
							BeanUtils.copyProperties(zmGoodCase,zmGoodCase1);
							String caseId = fba+"U"+String.format("%06d",i);
							zmGoodCase1.setCaseid(caseId);
							newZmGoodCase.add(zmGoodCase1);
						}
					}
					zmGoodCases=newZmGoodCase;
				}
			}else{
				List<ZmGoodCase> newZmGoodCase = new ArrayList<>();
				for (ZmGoodCase zmGoodCase : zmGoodCases) {
					String[] caseArea = zmGoodCase.getCaseid().split("-");
					int start = Integer.parseInt(caseArea[0]);
					int end = Integer.parseInt(caseArea[1]);
					for (int i = start; i <= end; i++) {
						ZmGoodCase zmGoodCase1 = new ZmGoodCase();
						BeanUtils.copyProperties(zmGoodCase,zmGoodCase1);
						String caseId = "";
						zmGoodCase1.setCaseid(caseId);
						newZmGoodCase.add(zmGoodCase1);
					}
				}
				zmGoodCases=newZmGoodCase;
			}



			int caseNum=1;

			String caseId = null;
			for (int i = 0; i < zmGoodCases.size(); i++) {
				zmGoodCases.get(i).setWaybillId(orderId+String.format("%03d",caseNum++));
			}

			//判断服务是否存在
			int serviceCount = zmServiceService.count(new QueryWrapper<ZmService>()
					.eq("name",zmWaybillsPage.getService()));
			if (serviceCount==0){
				return Result.error("导入失败，服务: "+zmWaybillsPage.getService()+" 不存在，请更新服务数据");
			}
			ZmWaybills po = new ZmWaybills();
			BeanUtils.copyProperties(zmWaybillsPage, po);
			po.setStatus("已下单");
			po.setMainProductName(zmGoodCases.get(0).getCnName());
			po.setPieces(String.valueOf(zmGoodCases.size()));
			po.setWaybillId(orderId);
			po.setId(id);
			zmWaybillsService.saveMain(po, zmGoodCases);
			QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
			ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
			zmLogisticsInformation.setOrderId(po.getOrderId());
			String msg = "已下单-" + TimeUtils.getNowTime();
			zmLogisticsInformation.setMsg(msg);
			zmLogisticsInformationService.save(zmLogisticsInformation);
			baseCommonService.addLog("创建运单",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,id,null);

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
				filePath.setWaybillId(id);
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

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Result.error("文件导入失败:" + e.getMessage());
		} finally {
			try {
				fileMap.get("files[]").getInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return  Result.OK(name[0]+" 下单成功");
	}


	@PutMapping(value = "/toStatus")
	/**
	 * @title toStatus
	 * @description 切换状态
	 * @author zzh
	 * @param: zmToStatus
	 * @updateTime 2022/2/11 16:13
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	public Result<?> toStatus(@RequestBody ZmToStatus zmToStatus) {
		//判断当前订单状态 和 需要转换的状态
		String ids = zmToStatus.getIds().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		String status = "";
		String[] str = {"已收货","转运中","已签收","退件","已取消","已下单"};
		for (String id : list) {
			ZmWaybills zmWayBills = zmWaybillsService.getById(id);
			if (zmWayBills == null) {
				return Result.error("未找到对应数据");
			}
			ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();

			status = str[Integer.parseInt(zmToStatus.getStatus())];
			if (status.equals("已作废"))
				zmWayBills.setStatus("待定仓");
			else
				zmWayBills.setStatus(status);

			zmWaybillsService.updateById(zmWayBills);
		}
		return Result.OK(status+"成功！");
	}
	/**
	 * @title
	 * @description 统计运单
	 * @author zzh
	 * @updateTime 2022/2/15 20:14
	 * @throws
	 */
	@AutoLog(value = "运单全表-统计运单")
	@ApiOperation(value = "运单全表-统计运单", notes = "运单全表-统计运单")
	@GetMapping(value = "/statics")
	public Result<?> statics(@RequestParam(name = "id", required = true) String id) {
		ZmWaybills zmWaybills = zmWaybillsService.getById(id);
		if (zmWaybills == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmWaybills);
	}

}