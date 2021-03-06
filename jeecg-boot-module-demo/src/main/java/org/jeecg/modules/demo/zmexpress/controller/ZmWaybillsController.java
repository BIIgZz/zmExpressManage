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
import org.springframework.cache.annotation.Cacheable;
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
 * @Description: ????????????
 * @Author: jeecg-boot
 * @Date:   2021-12-22
 * @Version: V1.0
 */
@Api(tags="????????????")
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
		 * ??????????????????
		 *
		 * @param zmWaybills
		 * @param pageNo
		 * @param pageSize
		 * @param req
		 * @return
		 */
		@AutoLog(value = "????????????-??????????????????")
		@ApiOperation(value = "????????????-??????????????????", notes = "????????????-??????????????????")
		@GetMapping(value = "/list")
		@PermissionData(pageComponent="zm/waybills/ZmWaybillsList")
		@Cacheable(cacheNames="queryPageList")
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
		 * ??????
		 *
		 * @param zmWaybillsPage
		 * @return
		 */
		@AutoLog(value = "????????????-??????")
		@ApiOperation(value = "????????????-??????", notes = "????????????-??????")
		@PostMapping(value = "/add")
		public Result<?> add(@RequestBody ZmWaybillsPage zmWaybillsPage) {
			ZmWaybills zmWaybills = new ZmWaybills();
			BeanUtils.copyProperties(zmWaybillsPage, zmWaybills);
			double totalWeight = 0;
			double totalVolume = 0;
			int caseNum = zmWaybillsPage.getZmGoodCaseList().size();
			double volumeWeight = 0;
			double chargeWeight = 0;
			zmWaybills.setStatus("?????????");
//			for (ZmGoodCase zmGoodCase : zmWaybillsPage.getZmGoodCaseList()) {
//
//				totalWeight += zmGoodCase.getWeight();
//				totalVolume += zmGoodCase.getLength()*zmGoodCase.getHeight()*zmGoodCase.getWidth()/1000;
//
//			}
//			DecimalFormat df = new DecimalFormat("0.00");
//			volumeWeight = Double.parseDouble(df.format(totalVolume/Double.parseDouble(zmWaybillsPage.getFoamingFactor())));
//			zmWaybills.setWeightActual(totalWeight);
//		zmWaybills.setVolume(totalVolume);
		zmWaybills.setWeightVolume(volumeWeight+"");
		zmWaybillsService.saveMain(zmWaybills, zmWaybillsPage.getZmGoodCaseList());
		return Result.OK("???????????????");
	}

	/**
	 * ??????
	 *
	 * @param zmWaybillsPage
	 * @return
	 */
	@AutoLog(value = "????????????-??????")
	@ApiOperation(value = "????????????-??????", notes = "????????????-??????")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmWaybillsPage zmWaybillsPage) {
		ZmWaybills zmWaybills = new ZmWaybills();
		BeanUtils.copyProperties(zmWaybillsPage, zmWaybills);
		ZmWaybills zmWaybillsEntity = zmWaybillsService.getById(zmWaybills.getId());
		if (zmWaybillsEntity == null) {
			return Result.error("?????????????????????");
		}
		zmWaybillsService.updateMain(zmWaybills, zmWaybillsPage.getZmGoodCaseList());
		return Result.OK("????????????!");
	}


	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "????????????-??????id??????")
	@ApiOperation(value = "????????????-??????id??????", notes = "????????????-??????id??????")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		ZmWaybills byId = zmWaybillsService.getById(id);
		zmWaybillsService.delMain(id);
		try {
			zmLogisticsInformationService.remove(new QueryWrapper<ZmLogisticsInformation>().eq("order_id",byId.getOrderId()));
		}catch (Exception e){

		}
		return Result.OK("????????????!");
	}

	/**
	 * ????????????
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "????????????-????????????")
	@ApiOperation(value = "????????????-????????????", notes = "????????????-????????????")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		this.zmWaybillsService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("?????????????????????");
	}

	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "????????????-??????id??????")
	@ApiOperation(value = "????????????-??????id??????", notes = "????????????-??????id??????")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		ZmWaybills zmWaybills = zmWaybillsService.getById(id);
		if (zmWaybills == null) {
			return Result.error("?????????????????????");
		}
		return Result.OK(zmWaybills);
	}

	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "????????????-????????????ID??????")
	@ApiOperation(value = "????????????-????????????ID??????", notes = "????????????-????????????ID??????")
	@GetMapping(value = "/queryZmGoodCaseByMainId")
	public Result<?> queryZmGoodCaseListByMainId(@RequestParam(name = "id", required = true) String id) {
		List<ZmGoodCase> zmGoodCaseList = zmGoodCaseService.selectByMainId(id);
		IPage<ZmGoodCase> page = new Page<>();
		page.setRecords(zmGoodCaseList);
		page.setTotal(zmGoodCaseList.size());
		return Result.OK(page);
	}

	/**
	 * ??????excel
	 *
	 * @param request
	 * @param zmWaybills
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, ZmWaybills zmWaybills) {
		// Step.1 ??????????????????????????????
		QueryWrapper<ZmWaybills> queryWrapper = QueryGenerator.initQueryWrapper(zmWaybills, request.getParameterMap());
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		//Step.2 ??????????????????
		List<ZmWaybills> queryList = zmWaybillsService.list(queryWrapper);
		// ??????????????????
		String selections = request.getParameter("selections");
		List<ZmWaybills> zmWaybillsList = new ArrayList<ZmWaybills>();
		if (oConvertUtils.isEmpty(selections)) {
			zmWaybillsList = queryList;
		} else {
			List<String> selectionList = Arrays.asList(selections.split(","));
			zmWaybillsList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		}

		// Step.3 ??????pageList
		List<ZmWaybillsPage> pageList = new ArrayList<ZmWaybillsPage>();
		for (ZmWaybills main : zmWaybillsList) {
			ZmWaybillsPage vo = new ZmWaybillsPage();
			BeanUtils.copyProperties(main, vo);
//			List<ZmGoodCase> zmGoodCaseList = zmGoodCaseService.selectByMainId(main.getId());
//			vo.setZmGoodCaseList(zmGoodCaseList);
			pageList.add(vo);
		}

		// Step.4 AutoPoi ??????Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "??????????????????");
		mv.addObject(NormalExcelConstants.CLASS, ZmWaybillsPage.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "?????????:" + sysUser.getRealname(), "????????????"));
		mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		return mv;
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
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// ????????????????????????
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
				return Result.OK("?????????????????????????????????:" + list.size());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Result.error("??????????????????:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Result.OK("?????????????????????");
	}

	/**
	 * ??????excel??????????????????
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
			MultipartFile file = entity.getValue();// ????????????????????????
			cn.afterturn.easypoi.excel.entity.ImportParams params = new cn.afterturn.easypoi.excel.entity.ImportParams();
			params.setKeyMark(":");
			params.setReadSingleCell(true);
			params.setTitleRows(17);

			//???????????????
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
					zmGoodCase.setApplication((String) result.getList().get(i).get("????????????*"));
					zmGoodCase.setCaseid((String) result.getList().get(i).get("????????????*"));
					zmGoodCase.setWeight(Double.parseDouble(result.getList().get(i).get("????????????(KG)*").toString()));
					zmGoodCase.setLength(Double.parseDouble(result.getList().get(i).get("????????????(CM)*").toString()));
					zmGoodCase.setWidth( Double.parseDouble(result.getList().get(i).get("????????????(CM)*").toString()));
					zmGoodCase.setHeight(Double.parseDouble(result.getList().get(i).get("????????????(CM)*").toString()));
					zmGoodCase.setEnName((String) result.getList().get(i).get("??????????????????*"));
					zmGoodCase.setCnName((String) result.getList().get(i).get("??????????????????*"));
					zmGoodCase.setDeclaredPrice(result.getList().get(i).get("??????????????????*").toString());
					zmGoodCase.setDeclaredNumber((Integer) result.getList().get(i).get("??????????????????*"));
					zmGoodCase.setMaterial(result.getList().get(i).get("????????????*").toString());
					try {
						Object o = result.getList().get(i).get("??????????????????*");
						double v = Double.parseDouble(o.toString());
						NumberFormat numberFormat = NumberFormat.getInstance();
						numberFormat.setGroupingUsed(false);
						String format = numberFormat.format(v);
						zmGoodCase.setHscode(format);
					} catch (NumberFormatException e) {
						zmGoodCase.setHscode(result.getList().get(i).get("??????????????????*").toString());
					}
					zmGoodCase.setBrand((String) result.getList().get(i).get("????????????*"));
					zmGoodCase.setType((String) result.getList().get(i).get("????????????*"));
					zmGoodCase.setModel((String) result.getList().get(i).get("????????????*"));
//					zmImportGood.set((String) map.get("PO Number*"));
					zmGoodCase.setLink((String) result.getList().get(i).get("??????????????????"));
					zmGoodCase.setPrice( (String)result.getList().get(i).get("??????????????????"));
					zmGoodCase.setPicture((String) result.getList().get(i).get("??????????????????"));
					zmGoodCases.add(zmGoodCase);
				}

				ZmWaybills zmWaybill = new ZmWaybills();
				ZmWaybillsPage zmWaybillsPage = new ZmWaybillsPage();
				zmWaybill.setWaybillId(orderId);
				zmWaybill.setRecipientAddressOne((String) map.get("??????????????????*:"));
				zmWaybill.setFbaId((String) map.get("FBA ID*:"));
				zmWaybill.setOrderId((String) map.get("???????????????:"));
				zmWaybill.setWarehouseId((String) map.get("???????????????*:"));
				zmWaybill.setService((String) map.get("??????*:"));
				zmWaybill.setRecipient((String) map.get("???????????????*:"));
				zmWaybill.setRecipientCity((String) map.get("???????????????*:"));
				zmWaybill.setRecipientState((String) map.get("???????????????/???*(????????????):"));
				zmWaybill.setRecipientZipOde((String) map.get("???????????????*:"));
				zmWaybill.setRecipientCountry((String) map.get("?????????????????????(????????????)*:"));
				zmWaybill.setRecipientTel((String) map.get("???????????????:"));
				zmWaybill.setRecipientEmail((String) map.get("???????????????:"));
				String itemAttributes = "";
				zmWaybill.setPieces((String) map.get("?????????*:"));
				if (map.get("??????*:").equals("???")) {
					itemAttributes += "??????,";
				}
				if (map.get("??????*:").equals("???")) {
					itemAttributes += "??????,";
				}
				if (map.get("??????*:").equals("???")) {
					itemAttributes += "??????,";
				}
				if (map.get("??????*:").equals("???")) {
					itemAttributes += "??????,";
				}
				if (map.get("?????????*:").equals("???")) {
					itemAttributes += "?????????,";
				}
				zmWaybill.setItemAttributes(itemAttributes);
				if (map.get("????????????*:").equals("????????????")) {
					zmWaybill.setCustomsDeclaration("0");
				} else if (map.get("????????????*:").equals("????????????")) {
					zmWaybill.setCustomsDeclaration("1");
				} else {
					zmWaybill.setCustomsDeclaration("2");
				}
				if (map.get("????????????:").equals("????????????")) {
					zmWaybill.setCustomsClearance("0");
				} else if (map.get("????????????:").equals("????????????")) {
					zmWaybill.setCustomsClearance("1");
				}
				if (map.get("????????????*:").equals("??????")) {
					zmWaybill.setTaxPayment("1");
				} else {
					zmWaybill.setTaxPayment("0");
				}
				zmWaybill.setVat((String) map.get("VAT???:"));
				zmWaybill.setReferenceNumberOne((String) map.get("????????????:"));
				zmWaybill.setReferenceNumberTwo((String) map.get("????????????:"));
				zmWaybill.setInRemark((String) map.get("??????:"));
				zmWaybill.setWarehouseId((String) map.get("?????????????????????:"));
//				 zmWaybill.setNameSender((String) map.get("???????????????:"));
//				 zmWaybill.setCompanySender((String) map.get("???????????????:"));
//				 zmWaybill.setCitySender((String) map.get("???????????????:"));
//				 zmWaybill.setPostcodeSender((String) map.get("???????????????:"));
//				 zmWaybill.setCitySender((String) map.get("?????????????????????(????????????):"));
//				 zmWaybill.setProvinceSender((String) map.get("???????????????/???:"));
//				 zmWaybill.setTelSender((String) map.get("???????????????:"));
//				 zmWaybill.setEmailSender((String) map.get("???????????????:"));
//				 zmWaybill.setWaybillId(shopOrderRule.autoOderid(""));
				ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
				zmLogisticsInformation.setOrderId(shopOrderRule.autoOderid(""));
				String msg = "?????????-" + TimeUtils.getNowTime();  //@?????????
				zmLogisticsInformation.setMsg(msg);
				zmLogisticsInformationService.save(zmLogisticsInformation);

				//????????????
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
				zmWaybill.setStatus("?????????");
				zmWaybillsService.saveMain(zmWaybill, zmGoodCases);
//				BeanUtils.copyProperties(page, po);
				//????????????
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

				return Result.OK("?????????????????????");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				if (e.getMessage().contains("fbaid")) {
					return Result.error("??????????????????:fbaid??????");
				}
				return Result.error("??????????????????:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Result.OK("?????????????????????");
	}

	/**
	 * ?????????????????? ??????
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/cancel")
	public Result<?> cancel(@RequestBody String ids) {
		//???????????????????????? ??? ?????????????????????
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("?????????????????????");
			}
			zmWaybill.setStatus("?????????");
			baseCommonService.addLog("????????????",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}

		return Result.OK("?????????????????????");
	}

	/**
	 * ????????????
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/redeliver")
	public Result<?> redeliver(@RequestBody String ids) {
		//???????????????????????? ??? ?????????????????????
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("?????????????????????");
			}
			zmWaybill.setStatus("?????????");
			baseCommonService.addLog("????????????",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("???????????????");
	}

	/**
	 * ??????
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/intercept")
	public Result<?> intercept(@RequestBody String ids, @RequestParam(name = "remark") String remark) {
		//???????????????????????? ??? ?????????????????????
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		System.out.println(Arrays.asList(ids.trim().split(" ")));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("?????????????????????");
			}
			zmWaybill.setStatus("?????????");
			remark+="\n"+zmWaybill.getRemark();
			zmWaybill.setRemark("????????????" + remark);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("???????????????");
	}

	/**
	 * ?????????????????????
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/picking")
	public Result<?> picking(@RequestBody String ids) {
		//???????????????????????? ??? ?????????????????????
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		System.out.println(Arrays.asList(ids.trim().split(" ")));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			double bubble = Double.parseDouble(zmServiceService.getOne(new QueryWrapper<ZmService>()
					.eq("name",zmWaybill.getService())).getBubble());
			if (zmWaybill == null) {
				return Result.error("?????????????????????");
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

			zmWaybill.setStatus("?????????");
			baseCommonService.addLog("????????????",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);

		}
		return Result.OK("???????????????????????????!");
	}
	/**
	 * ??????
	 *
	 * @param zmReason
	 * @return
	 */
	@PutMapping(value = "/rejection")
	public Result<?> rejection (@RequestBody ZmReason zmReason) {
		//???????????????????????? ??? ?????????????????????
		String ids = zmReason.getIds().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);

			if (zmWaybill == null) {
				return Result.error("?????????????????????");
			}
			zmWaybill.setStatus("??????");
			zmWaybill.setRemark("????????????:" +zmReason.getRemark());
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("???????????????");
	}
	/**
	 * ??????
	 *
	 * @param ids
	 * @return
	 */
	@PutMapping(value = "/transfer")
	public Result<?> transfer (@RequestBody String ids ) {
		//???????????????????????? ??? ?????????????????????
		ids = ids.replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		System.out.println(Arrays.asList(ids.trim().split(" ")));
		for (String id : list) {
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);
			if (zmWaybill == null) {
				return Result.error("?????????????????????");
			}
			zmWaybill.setStatus("?????????");
			zmWaybillsService.updateMain(zmWaybill, zmImportGoods);
		}
		return Result.OK("?????????????????????????????????");
	}


	/**
	 * @title inToShip
	 * @description  ???????????????
	 * @author zzh
	 * @param: zmReason
	 * @updateTime 2021/12/23 10:17
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@PutMapping(value = "/inToShip")
	public Result<?> inToShip (@RequestBody ZmReceipt zmReceipt) {
		//????????????id
		String ids = zmReceipt.getIds().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		//???????????????
		ZmDeliveryOrder zmDeliveryOrder = zmDeliveryOrderService.getById(zmReceipt.getId());
		if (zmDeliveryOrder==null){
			return Result.error("????????????????????????,??????????????????");
		}
		for (String id : list) {
			//??????????????????
			ZmWaybills zmWaybill = zmWaybillsService.getById(id);
			baseCommonService.addLog("?????????????????????????????????"+zmDeliveryOrder.getDeliveryOrderNo(),CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);

			if (zmWaybill == null) {
				return Result.error("?????????????????????");
			}
			zmWaybill.setDeliveryOrderId(zmReceipt.getId());
			zmWaybillsService.updateById(zmWaybill);
			//????????????????????????????????????????????
			List<ZmGoodCase> zmGoodCases = zmGoodCaseService.selectByMainId(id);
			//????????????????????????
			for (ZmGoodCase zmGoodCase : zmGoodCases) {
				zmGoodCase.setDeliveryOrderId(zmReceipt.getId());
				zmGoodCaseService.updateById(zmGoodCase);
			}
		}
		return Result.OK("?????????????????????");
	}
	/**
	 * @title inToLading
	 * @description ????????????
	 * @author zzh
	 * @param: zmReceipt
	 * @updateTime 2021/12/23 14:07
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@PutMapping(value = "/inToLading")
	public Result<?> inToLading(@RequestBody ZmReceipt zmReceipt) {
		//????????????id
		String id = zmReceipt.getId().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(id.trim().split(" "));

		//????????????  ????????????????????????????????????
		ZmBillloading zmBillloading = zmBillloadingService.getById(zmReceipt.getIds());
		if (zmBillloading==null){
			return Result.error("?????????????????????,???????????????");
		}
		for (String waybillId : list) {

			ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
			//??????????????????
			ZmWaybills zmWaybill = zmWaybillsService.getById(waybillId);
			if (zmWaybill == null) {
				return Result.error("?????????????????????");
			}
			baseCommonService.addLog("???????????????????????????"+zmBillloading.getBillnum(),CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,zmWaybill.getId(),null);

			zmWaybill.setStatus("?????????");
			zmLogisticsInformation.setOrderId(zmWaybill.getId());
			zmLogisticsInformation.setMsg("??? "+zmBillloading.getDeparturePoint()+" ?????? "
					+zmBillloading.getSendSite()+" ?????? "+zmBillloading.getCustomsClearanceTime()+"??????");
			zmLogisticsInformationService.save(zmLogisticsInformation);
			zmWaybill.setBillId(zmBillloading.getId());
			//????????????????????????
			QueryWrapper<ZmGoodCase> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("fbaid",zmWaybill.getId());
			List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(queryWrapper);
			for (ZmGoodCase zmGoodCase : zmGoodCases) {
				zmGoodCase.setBillId(zmReceipt.getIds());
				zmGoodCaseService.updateById(zmGoodCase);
			}
			zmWaybillsService.updateById(zmWaybill);
		}

		return Result.OK("??????????????????");
	}

	/**
	 * @title getTotalWaybill
	 * @description ??????????????????
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
	 * @description  ???????????????????????????
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
	 * @description ??????????????????????????????
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
	 * @description ????????????id???????????????
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
	 * @description ??????id??????????????????
	 * @author zzh
	 * @param: ids
	 * @updateTime 2022/1/4 15:34
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@AutoLog(value = "????????????-??????id??????")
	@ApiOperation(value = "????????????-??????id??????", notes = "????????????-??????id??????")
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
	 * @description ??????
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
		if (!zmWaybills.getStatus().equals("?????????")){
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
	 * @description ???????????????????????????
	 * @author zzh
	 * @updateTime 2022/1/6 23:02
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	@GetMapping("/statisticsByStatus")
	public Result<?> statisticsByStatus(@RequestParam(name = "type", required = true) int type,
										@RequestParam(name = "name", required = true) String name) {
		String[] res  = new String[7];
		int count = 0;
		if (type==1||type==2){
			QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
			List<ZmWaybills> list = zmWaybillsService.list();
			int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0;
			for (ZmWaybills zmWaybills : list) {
				switch (zmWaybills.getStatus()) {
					case "?????????":
						a++;
						break;
					case "?????????":
						b++;
						break;
					case "?????????":
						c++;
						break;
					case "?????????":
						d++;
						break;
					case "??????":
						e++;
						break;
					case "?????????":
						f++;
						break;
				}
			}
			res[0] = "?????????(" + a + ")";
			res[1] = "?????????(" + b + ")";
			res[2] = "?????????(" + c + ")";
			res[3] = "?????????(" + d + ")";
			res[4] = "??????(" + e + ")";
			res[5] = "?????????(" + f + ")";
			res[6] = "??????(" + list.size() + ")";
			return  Result.OK(res);
		}else {
			QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("username", name);
			List<ZmWaybills> list = zmWaybillsService.list(queryWrapper);
			int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0;
			for (ZmWaybills zmWaybills : list) {
				switch (zmWaybills.getStatus()) {
					case "?????????":
						a++;
						break;
					case "?????????":
						b++;
						break;
					case "?????????":
						c++;
						break;
					case "?????????":
						d++;
						break;
					case "??????":
						e++;
						break;
					case "?????????":
						f++;
						break;
				}
			}

			res[0] = "?????????(" + a + ")";
			res[1] = "?????????(" + b + ")";
			res[2] = "?????????(" + c + ")";
			res[3] = "?????????(" + d + ")";
			res[4] = "??????(" + e + ")";
			res[5] = "?????????(" + f + ")";
			res[6] = "??????(" + list.size() + ")";
			return  Result.OK(res);
		}


	}


	@RequestMapping("/import")
	/**
	 * @title statisticsByStatus
	 * @description ??????
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
		if (electric.equals("???"))
			property.concat("?????????");
		String magnetic = readHelper.getValueAt("F",2);
		if (magnetic.equals("???"))
			property.concat("?????????");
		String liquid = readHelper.getValueAt("F",3);
		if (liquid.equals("???"))
			property.concat("?????????");
		String powder = readHelper.getValueAt("F",4);
		if (powder.equals("???"))
			property.concat("?????????");
		String dangerous  = readHelper.getValueAt("F",5);
		if (dangerous.equals("???"))
			property.concat("????????????");
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
			//?????????????????????
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

			//????????????????????????
			int serviceCount = zmServiceService.count(new QueryWrapper<ZmService>()
					.eq("name",zmWaybillsPage.getService()));
			if (serviceCount==0){
				return Result.error("?????????????????????: "+zmWaybillsPage.getService()+" ?????????????????????????????????");
			}
			ZmWaybills po = new ZmWaybills();
			BeanUtils.copyProperties(zmWaybillsPage, po);
			po.setStatus("?????????");
			po.setMainProductName(zmGoodCases.get(0).getCnName());
			po.setPieces(String.valueOf(zmGoodCases.size()));
			po.setWaybillId(orderId);
			po.setId(id);
			zmWaybillsService.saveMain(po, zmGoodCases);
			QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
			ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
			zmLogisticsInformation.setOrderId(po.getId());
			String msg = "?????????-" + TimeUtils.getNowTime();
			zmLogisticsInformation.setMsg(msg);
			zmLogisticsInformationService.save(zmLogisticsInformation);
			baseCommonService.addLog("????????????",CommonConstant.LOG_TYPE_2,CommonConstant.OPERATE_TYPE_3,id,null);

			//????????????
			MultipartFile multipartFile = fileMap.get("files[]");// ????????????????????????
			try {
				String bizPath = "";
				String ctxPath = uploadpath;
				String fileName = null;
				File file = new File(ctxPath + File.separator + bizPath + File.separator );
				if (!file.exists()) {
					file.mkdirs();// ?????????????????????
				}
				String orgName = multipartFile.getOriginalFilename();// ???????????????
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
			return Result.error("??????????????????:" + e.getMessage());
		} finally {
			try {
				fileMap.get("files[]").getInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return  Result.OK(name[0]+" ????????????");
	}


	@PutMapping(value = "/toStatus")
	/**
	 * @title toStatus
	 * @description ????????????
	 * @author zzh
	 * @param: zmToStatus
	 * @updateTime 2022/2/11 16:13
	 * @return: org.jeecg.common.api.vo.Result<?>
	 * @throws
	 */
	public Result<?> toStatus(@RequestBody ZmToStatus zmToStatus) {
		//???????????????????????? ??? ?????????????????????
		String ids = zmToStatus.getIds().replaceAll("[^-?0-9]+", " ");
		List<String> list = Arrays.asList(ids.trim().split(" "));
		String status = "";
		String[] str = {"?????????","?????????","?????????","??????","?????????","?????????"};
		for (String id : list) {
			ZmWaybills zmWayBills = zmWaybillsService.getById(id);
			if (zmWayBills == null) {
				return Result.error("?????????????????????");
			}
			ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();

			status = str[Integer.parseInt(zmToStatus.getStatus())];
			if (status.equals("?????????"))
				zmWayBills.setStatus("?????????");
			else
				zmWayBills.setStatus(status);

			zmWaybillsService.updateById(zmWayBills);
		}
		return Result.OK(status+"?????????");
	}
	/**
	 * @title
	 * @description ????????????
	 * @author zzh
	 * @updateTime 2022/2/15 20:14
	 * @throws
	 */
	@AutoLog(value = "????????????-????????????")
	@ApiOperation(value = "????????????-????????????", notes = "????????????-????????????")
	@GetMapping(value = "/statics")
	public Result<?> statics(@RequestParam(name = "id", required = true) String id) {
		ZmWaybills zmWaybills = zmWaybillsService.getById(id);
		if (zmWaybills == null) {
			return Result.error("?????????????????????");
		}
		return Result.OK(zmWaybills);
	}

	/**
	 * @title ??????????????????
	 * @description ???????????????????????????????????????
	 * @author zzh
	 * @updateTime 2022/3/9 16:53
	 * @throws
	 */
	@GetMapping(value = "/getSortNum")
	public Result<?> getSortNum(@RequestParam(name = "name", required = true) String name) {
		List<ZmWaybills> list = zmWaybillsService.list(new QueryWrapper<ZmWaybills>().eq("username", name));
		Map<String ,Integer> res = new HashMap<>();
		res.put("ordered",0);res.put("received",0);res.put("transit",0);res.put("sign",0);res.put("return",0);res.put("cancel",0);
		for (ZmWaybills zmWaybills : list) {
			switch (zmWaybills.getStatus()){
				case "?????????":
					res.put("ordered",res.get("ordered")+1);
					break;
				case "?????????":
					res.put("received",res.get("received")+1);
					break;
				case "?????????":
					res.put("transit",res.get("transit")+1);
					break;
				case "?????????":
					res.put("sign",res.get("sign")+1);
					break;
				case "??????":
					res.put("return",res.get("return")+1);
					break;
				case "??????":
					res.put("cancel",res.get("cancel")+1);
					break;
			}
		}
		return Result.OK(res);
	}

}