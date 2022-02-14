package org.jeecg.modules.demo.zmexpress.controller;

import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.zmexpress.entity.*;
import org.jeecg.modules.demo.zmexpress.rules.ShopOrderRule;
import org.jeecg.modules.demo.zmexpress.service.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.zmexpress.vo.*;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 出货单
 * @Author: jeecg-boot
 * @Date:   2022-01-01
 * @Version: V1.0
 */
@Api(tags="出货单")
@RestController
@RequestMapping("/zmexpress/zmDeliveryOrder")
@Slf4j
public class ZmDeliveryOrderController extends JeecgController<ZmDeliveryOrder, IZmDeliveryOrderService> {
	@Autowired
	private IZmDeliveryOrderService zmDeliveryOrderService;
	@Autowired
	private ShopOrderRule shopOrderRule;
	@Autowired
	private IZmWaybillsService zmWaybillsService;
	@Autowired
	private IZmGoodCaseService zmGoodCaseService;
	@Autowired
	private IZmBillloadingService zmBillloadingService;
	@Autowired
	private IZmLogisticsInformationService zmLogisticsInformationService;
	@Autowired
	private IZmSplitCabinetService zmSplitCabinetService;
	/**
	 * 分页列表查询
	 *
	 * @param zmDeliveryOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "出货单-分页列表查询")
	@ApiOperation(value="出货单-分页列表查询", notes="出货单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmDeliveryOrder zmDeliveryOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmDeliveryOrder> queryWrapper = QueryGenerator.initQueryWrapper(zmDeliveryOrder, req.getParameterMap());
		Page<ZmDeliveryOrder> page = new Page<ZmDeliveryOrder>(pageNo, pageSize);
		IPage<ZmDeliveryOrder> pageList = zmDeliveryOrderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmDeliveryOrder
	 * @return
	 */
	@AutoLog(value = "出货单-添加")
	@ApiOperation(value="出货单-添加", notes="出货单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmDeliveryOrder zmDeliveryOrder) {

		zmDeliveryOrder.setDeliveryOrderNo(shopOrderRule.autoPickNo());
		zmDeliveryOrderService.save(zmDeliveryOrder);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmDeliveryOrder
	 * @return
	 */
	@AutoLog(value = "出货单-编辑")
	@ApiOperation(value="出货单-编辑", notes="出货单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmDeliveryOrder zmDeliveryOrder) {
		zmDeliveryOrderService.updateById(zmDeliveryOrder);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "出货单-通过id删除")
	@ApiOperation(value="出货单-通过id删除", notes="出货单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmDeliveryOrderService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "出货单-批量删除")
	@ApiOperation(value="出货单-批量删除", notes="出货单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmDeliveryOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "出货单-通过id查询")
	@ApiOperation(value="出货单-通过id查询", notes="出货单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmDeliveryOrder zmDeliveryOrder = zmDeliveryOrderService.getById(id);
		if(zmDeliveryOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmDeliveryOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmDeliveryOrder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmDeliveryOrder zmDeliveryOrder) {
        return super.exportXls(request, zmDeliveryOrder, ZmDeliveryOrder.class, "出货单");
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
        return super.importExcel(request, response, ZmDeliveryOrder.class);
    }

	 /**
	  * @title queryByStatus
	  * @description 获取所有出货单列表
	  * @author zzh
	  * @updateTime 2022/1/1 21:19
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @AutoLog(value = "出货单-通过状态查询")
	 @ApiOperation(value="出货单-通过状态查询", notes="出货单-通过状态查询")
	 @GetMapping(value = "/queryByStatus")
	 public Result<?> queryByStatus(){
		 QueryWrapper<ZmDeliveryOrder> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("status","待出货");
		 List<ZmDeliveryOrder> list = zmDeliveryOrderService.list(queryWrapper);
		 if(list==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(list);
	 }

	 @AutoLog(value = "出货单-统计")
	 @ApiOperation(value="出货单-统计", notes="出货单-统计")
	 @GetMapping(value = "/statistic")
	 public Result<?> statistic (@RequestParam(value = "id")String id){
		 ZmStatistics zmStatistics = new ZmStatistics();

		 /** 货箱数据统计*/
		 QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
		 zmGoodCaseQueryWrapper.eq("delivery_order_id",id);
		 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);
		 DecimalFormat df = new DecimalFormat("0.000");
		 double weight=0,volumeWeight=0,volume=0;
		 for (ZmGoodCase zmGoodCase : zmGoodCases) {
			 weight+=zmGoodCase.getWeight();
			 volumeWeight+=Double.parseDouble(zmGoodCase.getWeightVolume());
			 volume+=zmGoodCase.getWidth()*zmGoodCase.getHeight()*zmGoodCase.getLength()/1000000;
		 }
		 //根据货箱查找所有的运单
		 Set<ZmWaybills> waybillSet = new HashSet<>();
		 //todo 后期需要优化读取数据库次数太多了
		 for (ZmGoodCase zmGoodCase : zmGoodCases) {
			 ZmWaybills zmWaybill = zmWaybillsService.getById(zmGoodCase.getFbaid());
			 waybillSet.add(zmWaybill);
		 }
		 zmStatistics.setWaybillCount(waybillSet.size());
		 zmStatistics.setCaseCount(zmGoodCases.size());
		 zmStatistics.setVolume(Double.parseDouble(df.format(volume)));
		 zmStatistics.setVolumeWeight(Double.parseDouble(df.format(volumeWeight)));
		 zmStatistics.setWeight(weight);
		 ZmDeliveryOrder zmDeliveryOrder = zmDeliveryOrderService.getById(id);
		 zmDeliveryOrder.setCaseNum(zmGoodCases.size()+"");
		 zmDeliveryOrder.setWeight(String.valueOf(weight));
		 zmDeliveryOrderService.updateById(zmDeliveryOrder);
		 return Result.OK(zmStatistics);
	 }

	 /**
	  * @title queryByStatusChange
	  * @description
	  * @author zzh
	  * @param: status
	  * @updateTime 2022/1/4 9:24
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping(value = "/queryByStatusChange")
	 public Result<?> queryByStatusChange(@RequestParam(value = "status")String status){
		 QueryWrapper<ZmDeliveryOrder> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("status",status);
		 List<ZmDeliveryOrder> list = zmDeliveryOrderService.list(queryWrapper);
		 if(list==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(list);
	 }

	 /**
	  * @title inToLading
	  * @description 将出货单加入提单
	  * @author zzh
	  * @param: zmReceipt
	  * @updateTime 2022/1/6 9:57
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @PutMapping(value = "/inToLading")
	 public Result<?> inToLading(@RequestBody ZmReceipt zmReceipt) {
		 //获取运单id
		 String id = zmReceipt.getIds().replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(id.trim().split(" "));
		 ZmBillloading zmBillloading =zmBillloadingService.getById(zmReceipt.getId());
		 for (String deliveryId : list) {

			 //获取运单列表
			 ZmDeliveryOrder zmDeliveryOrder = zmDeliveryOrderService.getById(deliveryId);
			 if (zmDeliveryOrder == null) {
				 return Result.error("未找到对应数据");
			 }

//			 zmLogisticsInformation.setOrderId(zmWaybill.getId());
//			 zmLogisticsInformation.setMsg("从 "+zmBillloading.getDeparturePoint()+" 发往 "
//					 +zmBillloading.getSendSite()+" 预计 "+zmBillloading.getCustomsClearanceTime()+"开船");
//			 zmLogisticsInformationService.save(zmLogisticsInformation);
//			 zmDeliveryOrder.setLadingBillId(zmReceipt.getId());
//			 zmDeliveryOrderService.updateById(zmDeliveryOrder);

			 //将出货单的所有货箱加入提单
			 QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
			 queryWrapper.eq("delivery_order_id", zmDeliveryOrder.getId());
			 List<ZmWaybills> zmWaybills = zmWaybillsService.list(queryWrapper);
			 List<ZmSplitCabinet> zmSplitCabinetList = zmSplitCabinetService.list(new QueryWrapper<ZmSplitCabinet>()
					 .eq("delivery_id",zmDeliveryOrder.getId()));
			 for (ZmSplitCabinet zmSplitCabinet : zmSplitCabinetList) {
				zmWaybills.add(zmWaybillsService.getById(zmSplitCabinet.getWaybillId())) ;
			 }
			 for (ZmWaybills zmWaybill : zmWaybills) {
				 ZmLogisticsInformation zmLogisticsInformation = new ZmLogisticsInformation();
				 zmWaybill.setBillId(zmReceipt.getId());
				 zmWaybill.setStatus("转运中");
				 zmLogisticsInformation.setOrderId(zmWaybill.getWaybillId());
				 zmLogisticsInformation.setMsg("从 "+zmBillloading.getDeparturePoint()+" 发往 "
					 +zmBillloading.getSendSite()+" 预计 "+zmBillloading.getCustomsClearanceTime()+"开船");
				QueryWrapper<ZmLogisticsInformation> zmLogisticsInformationQueryWrapper = new QueryWrapper<>();
				//更新提单中的货箱
				 QueryWrapper<ZmGoodCase> queryWrapperCase = new QueryWrapper<>();
//				 queryWrapperCase.eq("fbaid",zmWaybill.getId());
				 queryWrapperCase.eq("delivery_order_id", zmDeliveryOrder.getId());
				 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(queryWrapperCase);
				 for (ZmGoodCase zmGoodCase : zmGoodCases) {
					 zmGoodCase.setBillId(zmReceipt.getId());
					 zmGoodCaseService.updateById(zmGoodCase);
				 }
			 zmLogisticsInformationService.save(zmLogisticsInformation);
			 zmWaybillsService.updateById(zmWaybill);
			 }
		 }
		 return Result.OK("加入提单成功");
	 }

	 @AutoLog(value = "出货单全表-通过id查询")
	 @ApiOperation(value = "出货单全表-通过id查询", notes = "出货单全表-通过id查询")
	 @GetMapping(value = "/queryListById")
	 public Result<?> queryListById(@RequestParam(name = "ids", required = true) String ids) {
		 ids = ids.replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 List<ZmDeliveryOrder> zmDeliveryOrders = new ArrayList<>();
		 for (String s : list) {
			 ZmDeliveryOrder zmDeliveryOrder = zmDeliveryOrderService.getById(s);
			 zmDeliveryOrders.add(zmDeliveryOrder);
		 }
		 return Result.OK(zmDeliveryOrders);
	 }

	 /**
	  * @title transfer
	  * @description 出货
	  * @author zzh
	  * @param: ids
	  * @updateTime 2022/1/4 10:41
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @PutMapping(value = "/transfer")
	 public Result<?> transfer (@RequestBody String ids ) {
		 //判断当前订单状态 和 需要转换的状态
		 ids = ids.replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 System.out.println(Arrays.asList(ids.trim().split(" ")));
		 for (String id : list) {
			 ZmDeliveryOrder zmDeliveryOrder = zmDeliveryOrderService.getById(id);
//			 List<ZmGoodCase> zmImportGoods = zmGoodCaseService.selectByMainId(id);
			 if (zmDeliveryOrder == null) {
				 return Result.error("未找到对应数据");
			 }
			 zmDeliveryOrder.setStatus("已出货");
			 zmDeliveryOrderService.updateById(zmDeliveryOrder);
		 }
		 return Result.OK("出货成功！");
	 }
	 /**
	  * @title deliveryByCaseId
	  * @description 通过货箱号拣货
	  * @author zzh
	  * @param: zmDeliveryIn
	  * @updateTime 2022/1/4 15:49
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @PutMapping(value = "/deliveryByIds")
	 public Result<?> deliveryByIds (@RequestBody ZmDeliveryIn zmDeliveryIn ) {
		 if (zmDeliveryIn.getType().equals("1")){
			 QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
			 for (String id : zmDeliveryIn.getIds()) {
				 zmGoodCaseQueryWrapper.eq("caseid", id);
				 ZmGoodCase zmGoodCase =zmGoodCaseService.getOne(zmGoodCaseQueryWrapper);
				 zmGoodCaseQueryWrapper.clear();
				 if (zmGoodCase == null) {
					 return Result.error("未找到对应数据");
				 }
				 ZmWaybills zmWaybills = zmWaybillsService.getById(zmGoodCase.getFbaid());
				 zmWaybills.setBinning("1");
				 //拆柜处理
				 ZmSplitCabinet zmSplitCabinet = new ZmSplitCabinet();
				 zmSplitCabinet.setDeliveryId(zmDeliveryIn.getId());
				 zmSplitCabinet.setWaybillId(zmWaybills.getId());
				 zmSplitCabinet.setSplitCase(zmDeliveryIn.getIds().length);
				 zmSplitCabinet.setTotalCase(zmWaybills.getPieces());
				 zmSplitCabinetService.save(zmSplitCabinet);

				 zmWaybillsService.updateById(zmWaybills);
				 zmGoodCase.setDeliveryOrderId(zmDeliveryIn.getId());
				 zmGoodCaseService.updateById(zmGoodCase);
			 }
		 }else{
			 QueryWrapper<ZmWaybills> zmWaybillsQueryWrapper = new QueryWrapper<>();
			 QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
			 for (String id : zmDeliveryIn.getIds()) {
				 zmWaybillsQueryWrapper.eq("waybill_id",id);
				 ZmWaybills zmWaybills = zmWaybillsService.getOne(zmWaybillsQueryWrapper);
				 if (zmWaybills == null) {
					 return Result.error("未找到对应数据");
				 }
				 zmWaybillsService.updateById(zmWaybills);
				 zmGoodCaseQueryWrapper.eq("fbaid",zmWaybills.getId());
				 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);
				 for (ZmGoodCase zmGoodCase : zmGoodCases) {
					 zmGoodCase.setDeliveryOrderId(zmDeliveryIn.getId());
					 zmGoodCaseService.updateById(zmGoodCase);
				 }
			 }
		 }
		 return Result.OK("出货成功！");
	 }



	 /**
	  * @title statisticsByStatus
	  * @description 根据出货单转状态统计
	  * @author zzh
	  * @updateTime 2022/1/10 16:51
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping("/statisticsByStatus")
	 public Result<?> statisticsByStatus() {
		 String[] res  = new String[3];
		 int count = 0;
		 QueryWrapper<ZmDeliveryOrder> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("status","待出货");
		 count = zmDeliveryOrderService.count(queryWrapper);
		 res[0]="待出货("+count+")";
		 queryWrapper.clear();
		 queryWrapper.eq("status","已出货");
		 count = zmDeliveryOrderService.count(queryWrapper);
		 res[1]="已出货("+count+")";
		 count = zmDeliveryOrderService.count();
		 res[2]="全部("+count+")";
		 return  Result.OK(res);
	 }

	 /**
	  * @title getCaseListByDeliveryId
	  * @description 通过出货单获取货箱
	  * @author zzh
	  * @param: id
	  * @updateTime 2022/1/11 10:49
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping("/getCaseListByDeliveryId")
	 public Result<?> getCaseListByDeliveryId(@RequestParam(value = "id")String id){

		 //构建货箱查询条件
		 QueryWrapper<ZmGoodCase> zmGoodCaseQueryWrapper = new QueryWrapper<>();
		 zmGoodCaseQueryWrapper.eq("delivery_order_id",id);
		 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(zmGoodCaseQueryWrapper);

		 //取出该货箱的运单
		 List<ZmCaseDetail> zmCaseDetails = new ArrayList<>();

		 String pickData ;
		 String customerData;
		 String carrierData;
		 for (ZmGoodCase zmGoodCase : zmGoodCases) {
			 ZmWaybills zmWaybills = zmWaybillsService.getById(zmGoodCase.getFbaid());
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

	 @PutMapping("/moveOutDelivery")
	 public Result<?> moveOutDelivery (@RequestBody String ids ) {
		 ids = ids.replaceAll("[^-?0-9]+", " ");
		 List<String> list = Arrays.asList(ids.trim().split(" "));
		 for (String s : list) {
			 ZmGoodCase zmGoodCase = zmGoodCaseService.getById(s);
			 zmGoodCase.setDeliveryOrderId("");
			 zmGoodCaseService.updateById(zmGoodCase);
		 }
		 return Result.OK("移除成功");
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
			 ZmWaybills waybills = zmWaybillsService.getById(s);
			 waybills.setDeliveryOrderId("");

			 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(new QueryWrapper<ZmGoodCase>().eq("fbaid", s));
			 for (ZmGoodCase zmGoodCase : zmGoodCases) {
				 zmGoodCase.setDeliveryOrderId("");
				 zmGoodCaseService.updateById(zmGoodCase);
			 }
			 zmWaybillsService.updateById(waybills);
		 }
		 return Result.OK("移除成功!");
	 }
	 /**
	  * @title getTotalWaybillByDelivery
	  * @description 出货单中的运单列表
	  * @author zzh
	  * @param: id
	  * @updateTime 2022/1/17 20:26
	  * @return: org.jeecg.common.api.vo.Result<?>
	  * @throws
	  */
	 @GetMapping(value = "/getTotalWaybillByDelivery")
	 public Result<?> getTotalWaybillByDelivery(@RequestParam(name = "id", required = true)String id) {
		 QueryWrapper<ZmWaybills> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("delivery_order_id",id);

		 //先获取所有货箱
		 List<ZmGoodCase> zmGoodCases = zmGoodCaseService.list(new QueryWrapper<ZmGoodCase>().eq("delivery_order_id", id));

		 //根据货箱查找所有的运单
		 Set<ZmWaybills> waybillSet = new HashSet<>();
		 //todo 后期需要优化读取数据库次数太多了
		 for (ZmGoodCase zmGoodCase : zmGoodCases) {
			 ZmWaybills zmWaybill = zmWaybillsService.getById(zmGoodCase.getFbaid());
			 waybillSet.add(zmWaybill);
		 }

		 //根据运单中的货箱  和出货单中该运单的货箱组成比值
		 List<ZmDeliveryWaybill> zmWaybills = new ArrayList<>();
		 for (ZmWaybills waybills : waybillSet) {
			 ZmDeliveryWaybill zmDeliveryWaybill  = new ZmDeliveryWaybill();
			 BeanUtils.copyProperties(waybills,zmDeliveryWaybill);
			 if (waybills.getBinning().equals("1")){
				 String total = waybills.getPieces();
				 int binning = zmGoodCaseService.count(new QueryWrapper<ZmGoodCase>().eq("delivery_order_id",id).eq("fbaid",waybills.getId()));
				 String pieces = binning+"/"+total;
				 zmDeliveryWaybill.setPieces(pieces);
			 }
			 zmWaybills.add(zmDeliveryWaybill);
		 }

		 return Result.OK(zmWaybills);
	 }


 }
