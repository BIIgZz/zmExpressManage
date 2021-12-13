package org.jeecg.modules.demo.zmexpress.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import org.jeecg.modules.demo.zmexpress.entity.ZmProduct;
import org.jeecg.modules.demo.zmexpress.utils.InsertUtils;
import org.jeecgframework.poi.excel.def.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportFba;
import org.jeecg.modules.demo.zmexpress.vo.ZmImportFbaPage;
import org.jeecg.modules.demo.zmexpress.service.IZmImportFbaService;
import org.jeecg.modules.demo.zmexpress.service.IZmImportGoodService;
import org.jeecgframework.poi.excel.view.JeecgTemplateExcelView;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 导入FBA表
 * @Author: jeecg-boot
 * @Date: 2021-07-13
 * @Version: V1.0
 */
@Api(tags = "导入FBA表")
@RestController
@RequestMapping("/zmexpress/zmImportFba")
@Slf4j
public class ZmImportFbaController {
    @Autowired
    private IZmImportFbaService zmImportFbaService;
    @Autowired
    private IZmImportGoodService zmImportGoodService;
    @Autowired
    private InsertUtils insertUtils;


    /**
     * 分页列表查询
     *
     * @param zmImportFba
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "导入FBA表-分页列表查询")
    @ApiOperation(value = "导入FBA表-分页列表查询", notes = "导入FBA表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ZmImportFba zmImportFba,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<ZmImportFba> queryWrapper = QueryGenerator.initQueryWrapper(zmImportFba, req.getParameterMap());
        Page<ZmImportFba> page = new Page<ZmImportFba>(pageNo, pageSize);
        IPage<ZmImportFba> pageList = zmImportFbaService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param zmImportFbaPage
     * @return
     */
    @AutoLog(value = "导入FBA表-添加")
    @ApiOperation(value = "导入FBA表-添加", notes = "导入FBA表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ZmImportFbaPage zmImportFbaPage) {
        ZmImportFba zmImportFba = new ZmImportFba();
        BeanUtils.copyProperties(zmImportFbaPage, zmImportFba);
        zmImportFbaService.saveMain(zmImportFba, zmImportFbaPage.getZmImportGoodList());
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param zmImportFbaPage
     * @return
     */
    @AutoLog(value = "导入FBA表-编辑")
    @ApiOperation(value = "导入FBA表-编辑", notes = "导入FBA表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ZmImportFbaPage zmImportFbaPage) {
        ZmImportFba zmImportFba = new ZmImportFba();
        BeanUtils.copyProperties(zmImportFbaPage, zmImportFba);
        ZmImportFba zmImportFbaEntity = zmImportFbaService.getById(zmImportFba.getId());
        if (zmImportFbaEntity == null) {
            return Result.error("未找到对应数据");
        }
        zmImportFbaService.updateMain(zmImportFba, zmImportFbaPage.getZmImportGoodList());
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "导入FBA表-通过id删除")
    @ApiOperation(value = "导入FBA表-通过id删除", notes = "导入FBA表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        zmImportFbaService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "导入FBA表-批量删除")
    @ApiOperation(value = "导入FBA表-批量删除", notes = "导入FBA表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.zmImportFbaService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "导入FBA表-通过id查询")
    @ApiOperation(value = "导入FBA表-通过id查询", notes = "导入FBA表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ZmImportFba zmImportFba = zmImportFbaService.getById(id);
        if (zmImportFba == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(zmImportFba);
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
     * @param zmImportFba
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmImportFba zmImportFba) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<ZmImportFba> queryWrapper = QueryGenerator.initQueryWrapper(zmImportFba, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<ZmImportFba> queryList = zmImportFbaService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<ZmImportFba> zmImportFbaList = new ArrayList<ZmImportFba>();
        if (oConvertUtils.isEmpty(selections)) {
            zmImportFbaList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            zmImportFbaList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<ZmImportFbaPage> pageList = new ArrayList<ZmImportFbaPage>();
        for (ZmImportFba main : zmImportFbaList) {
            ZmImportFbaPage vo = new ZmImportFbaPage();
            BeanUtils.copyProperties(main, vo);
            List<ZmImportGood> zmImportGoodList = zmImportGoodService.selectByMainId(main.getId());
            vo.setZmImportGoodList(zmImportGoodList);
            pageList.add(vo);
        }
//      // Step.3.1 创建模板
        Map<String, Object> map = new HashMap<>();
        map.put("fbaid", pageList.get(0).getFbaid());
        map.put("orderid", pageList.get(0).getOrderid());
        map.put("serviceId", pageList.get(0).getServiceId());
        map.put("code", pageList.get(0).getCode());
        map.put("name", pageList.get(0).getName());
        map.put("company", pageList.get(0).getCompany());
        map.put("address", pageList.get(0).getAddress());
        map.put("city", pageList.get(0).getCity());
        map.put("province", pageList.get(0).getProvince());
        map.put("countryCode", pageList.get(0).getCountryCode());
        map.put("tel", pageList.get(0).getTel());
        map.put("email", pageList.get(0).getEmail());
        map.put("caseNumber", pageList.get(0).getCaseNumber());
        map.put("electrical", pageList.get(0).getElectrical());
        map.put("magnetic", pageList.get(0).getMagnetic());
        map.put("liquid", pageList.get(0).getLiquid());
        map.put("powder", pageList.get(0).getPowder());
        map.put("dangerous", pageList.get(0).getDangerous());
        map.put("customs_eclaration", pageList.get(0).getCustomsEclaration());
        map.put("customs_clearance", pageList.get(0).getCustomsClearance());
        map.put("tax_payment", pageList.get(0).getTaxPayment());
        map.put("deliveryTerm", pageList.get(0).getDeliveryTerm());
        map.put("referenceNumber1", pageList.get(0).getReferenceNumber1());
        map.put("referenceNumber2", pageList.get(0).getReferenceNumber2());
        map.put("note", pageList.get(0).getNote());
        map.put("companySender", pageList.get(0).getCompanySender());
        map.put("nameSender", pageList.get(0).getNameSender());
        map.put("addressSender", pageList.get(0).getAddressSender());
        map.put("provinceSender", pageList.get(0).getPostcodeSender());
        map.put("countryCodeSender", pageList.get(0).getCountryCodeSender());
        map.put("telSender", pageList.get(0).getTelSender());
        map.put("emailSender", pageList.get(0).getEmailSender());

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (ZmImportGood zmImportGood : pageList.get(0).getZmImportGoodList()) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("caseid", zmImportGood.getCaseid());
            m.put("weight", zmImportGood.getWeight());
            m.put("length", zmImportGood.getLength());
            m.put("height", zmImportGood.getHeight());
            m.put("enName", zmImportGood.getEnName());
            m.put("cnName", zmImportGood.getCnName());
            m.put("declaredPrice", zmImportGood.getDeclaredPrice());
            m.put("declaredNumber", zmImportGood.getDeclaredNumber());
            m.put("material", zmImportGood.getMaterial());
            m.put("hscode", zmImportGood.getHscode());
            m.put("application", zmImportGood.getApplication());
            m.put("brand", zmImportGood.getBrand());
            m.put("type", zmImportGood.getType());
            m.put("model", zmImportGood.getModel());
            m.put("link", zmImportGood.getLink());
            m.put("hscode", zmImportGood.getHscode());
            m.put("price", zmImportGood.getPrice());
            m.put("picture", zmImportGood.getPicture());
            listMap.add(m);
        }
        map.put("maplist", listMap);
        TemplateExportParams params = new TemplateExportParams();
        params.setTemplateUrl("E:\\postGraduate\\express\\zm-project\\jeecg-boot-module-demo\\src\\main\\resources\\exportTemplate\\template.xls");

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgTemplateExcelView());
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      mv.addObject(TemplateExcelConstants.FILE_NAME, map.get("fbaid"));
//      mv.addObject(NormalExcelConstants.CLASS, ZmImportFbaPage.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("导入FBA表数据", "导出人:"+sysUser.getRealname(), "导入FBA表"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        // map导出
        params.setSheetName(map.get("fbaid").toString());
        mv.addObject(TemplateExcelConstants.PARAMS, params);
        mv.addObject(TemplateExcelConstants.MAP_DATA, map);
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

        //key-value

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            cn.afterturn.easypoi.excel.entity.ImportParams params = new cn.afterturn.easypoi.excel.entity.ImportParams();
            params.setKeyMark(":");
            params.setReadSingleCell(true);
            params.setTitleRows(17);

            try {
                ExcelImportResult<Map> result = ExcelImportUtil.importExcelMore(file.getInputStream(), Map.class, params);
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

                ZmImportFba zmImportFbaPage = new ZmImportFba();
//				ZmImportFbaPage zmImportFbaPage = new ZmImportFbaPage();
                zmImportFbaPage.setAddress((String) map.get("收件人地址一*:"));
                zmImportFbaPage.setFbaid((String) map.get("FBA ID*:"));
                zmImportFbaPage.setOrderid((String) map.get("客户订单号:"));
                zmImportFbaPage.setCode((String) map.get("地址库编码*:"));
                zmImportFbaPage.setServiceId((String) map.get("服务*:"));
                zmImportFbaPage.setName((String) map.get("收件人姓名*:"));
                zmImportFbaPage.setCity((String) map.get("收件人城市*:"));
                zmImportFbaPage.setProvince((String) map.get("收件人省份/州*(二字代码):"));
                zmImportFbaPage.setPostcode((String) map.get("收件人邮编*:"));
                zmImportFbaPage.setCountryCode((String) map.get("收件人国家代码(二字代码)*:"));
                zmImportFbaPage.setTel((String) map.get("收件人电话:"));
                zmImportFbaPage.setEmail((String) map.get("收件人邮箱:"));
//				zmImportFbaPage.setCaseNumber(Integer.parseInt((String) map.get("总箱数*:")) );
                if (map.get("带电*:").equals("是")) {
                    zmImportFbaPage.setElectrical(1);
                } else {
                    zmImportFbaPage.setElectrical(0);
                }
                if (map.get("带磁*:").equals("是")) {
                    zmImportFbaPage.setMagnetic(1);
                } else {
                    zmImportFbaPage.setMagnetic(0);
                }
                if (map.get("液体*:").equals("是")) {
                    zmImportFbaPage.setLiquid(1);
                } else {
                    zmImportFbaPage.setLiquid(0);
                }
                if (map.get("粉末*:").equals("是")) {
                    zmImportFbaPage.setPowder(1);
                } else {
                    zmImportFbaPage.setPowder(0);
                }
                if (map.get("危险品*:").equals("是")) {
                    zmImportFbaPage.setDangerous(1);
                } else {
                    zmImportFbaPage.setDangerous(0);
                }
                if (map.get("报关方式*:").equals("买单报关")) {
                    zmImportFbaPage.setCustomsEclaration(0);
                } else if (map.get("报关方式*:").equals("单独报关")) {
                    zmImportFbaPage.setCustomsEclaration(1);
                } else {
                    zmImportFbaPage.setCustomsEclaration(2);
                }
                if (map.get("清关方式:").equals("买单报关")) {
                    zmImportFbaPage.setCustomsEclaration(0);
                } else if (map.get("清关方式:").equals("单独报关")) {
                    zmImportFbaPage.setCustomsClearance(1);
                } else {
                    zmImportFbaPage.setCustomsClearance(2);
                }
                if (map.get("交税方式*:").equals("包税")) {
                    zmImportFbaPage.setTaxPayment(1);
                } else {
                    zmImportFbaPage.setTaxPayment(0);
                }
                zmImportFbaPage.setVat((String) map.get("VAT号:"));
                zmImportFbaPage.setReferenceNumber1((String) map.get("参考号一:"));
                zmImportFbaPage.setReferenceNumber2((String) map.get("参考号二:"));
                zmImportFbaPage.setNote((String) map.get("备注:"));
                zmImportFbaPage.setAddressSender((String) map.get("发件人地址编码:"));
                zmImportFbaPage.setNameSender((String) map.get("发件人姓名:"));
                zmImportFbaPage.setCompanySender((String) map.get("发件人公司:"));
                zmImportFbaPage.setCitySender((String) map.get("发件人城市:"));
                zmImportFbaPage.setPostcodeSender((String) map.get("发件人邮编:"));
                zmImportFbaPage.setCitySender((String) map.get("发件人国家代码(二字代码):"));
                zmImportFbaPage.setProvinceSender((String) map.get("发件人省份/州:"));
                zmImportFbaPage.setTelSender((String) map.get("发件人电话:"));
                zmImportFbaPage.setEmailSender((String) map.get("发件人邮箱:"));

                zmImportFbaService.saveMain(zmImportFbaPage, importGoodList);
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
                    zmProduct.setPrice(zmImportGood.getPrice());
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
                return Result.OK("文件导入成功！数据行数:" + result.getMap());
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

        //实体导入
//      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//          MultipartFile file = entity.getValue();// 获取上传文件对象
//          ImportParams params = new ImportParams();
//          params.setTitleRows(2);
//          params.setHeadRows(1);
//		  params.setNeedSave(true);
//
//          try {
//
//              List<ZmImportFbaPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmImportFbaPage.class, params);
//              for (ZmImportFbaPage page : list) {
//                  ZmImportFba po = new ZmImportFba();
//                  BeanUtils.copyProperties(page, po);
//                  zmImportFbaService.saveMain(po, page.getZmImportGoodList());
//                  List<ZmProduct> zmProductList = new ArrayList<>();
//				  for (ZmImportGood zmImportGood:page.getZmImportGoodList()) {
//					  ZmProduct zmProduct = new ZmProduct();
//					  zmProduct.setCnName(zmImportGood.getCnName());
//					  zmProduct.setApplication((zmImportGood.getApplication()));
//					  zmProduct.setBrand((zmImportGood.getBrand()));
//					  zmProduct.setCreateBy(zmImportGood.getCreateBy());
//					  zmProduct.setCreateTime(zmImportGood.getCreateTime());
//					  zmProduct.setDeclaredPrice(Double.parseDouble(zmImportGood.getDeclaredPrice()));
//					  zmProduct.setEnName(zmImportGood.getEnName());
//					  zmProduct.setHscode(zmImportGood.getHscode());
//					  zmProduct.setLink(zmImportGood.getLink());
//					  zmProduct.setMaterial(zmImportGood.getMaterial());
//					  zmProduct.setModel(zmImportGood.getModel());
//					  zmProduct.setPicture(zmImportGood.getPicture());
//					  zmProduct.setPrice(zmImportGood.getPrice());
//					  zmProduct.setType(1);
//					  zmProduct.setUpdateBy(zmImportGood.getUpdateBy());
//					  zmProduct.setUpdateTime(zmImportGood.getUpdateTime());
//					  zmProduct.setId(zmImportGood.getId());
//					  zmProductList.add(zmProduct);
//				  }
//				  insertUtils.insertProduct(zmProductList);
//				  insertUtils.insertHscode(page.getZmImportGoodList());
//              }
//              return Result.OK("文件导入成功！数据行数:" + list.size());
//          } catch (Exception e) {
//              log.error(e.getMessage(),e);
//              return Result.error("文件导入失败:"+e.getMessage());
//          } finally {
//              try {
//                  file.getInputStream().close();
//              } catch (IOException e) {
//                  e.printStackTrace();
//              }
//          }
//      }
        return Result.OK("文件导入失败！");
    }
    /**
     * 放入出货单
     * 先获取订单，然后新建一个出货单放入出货单
     * 修改订单状态
     * @param request
     * @param response
     * @return
     */
    @PutMapping(value = "/change")
    public Result<?> change(@RequestBody String ids) {
       //判断当前订单状态 和 需要转换的状态
        ids = ids.replaceAll("[^-?0-9]+", " ");
        List<String> list = Arrays.asList(ids.trim().split(" "));
        System.out.println(Arrays.asList(ids.trim().split(" ")));
        for (String id : list) {
            ZmImportFba zmImportFba = zmImportFbaService.getById(id);
            List<ZmImportGood> zmImportGoods = zmImportGoodService.selectByMainId(id);

            if (zmImportFba == null) {
                return Result.error("未找到对应数据");
            }
            zmImportFba.setStatus(5+"");
            zmImportFbaService.updateMain(zmImportFba,zmImportGoods);
        }
        //切换状态

        //存储状态
        System.out.println("状态更新成功！");
        return Result.OK("状态更新成功！");
    }

}
