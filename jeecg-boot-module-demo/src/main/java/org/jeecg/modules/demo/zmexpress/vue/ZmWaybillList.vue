<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="运单号">
              <a-input placeholder="请输入运单号" v-model="queryParam.waybillId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户订单号">
              <a-input placeholder="请输入客户订单号" v-model="queryParam.orderId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="客户名称">
                <j-dict-select-tag placeholder="请选择客户名称" v-model="queryParam.name" dictCode="zm_client_main,username,username"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="公司名">
                <a-input placeholder="请输入公司名" v-model="queryParam.company"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="柜号">
                <a-input placeholder="请输入柜号" v-model="queryParam.caseId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="FBA号">
                <a-input placeholder="请输入FBA号" v-model="queryParam.fbaId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="工作号">
                <a-input placeholder="请输入工作号" v-model="queryParam.jobId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="服务">
                <j-search-select-tag placeholder="请选择服务" v-model="queryParam.service" dict="zm_service,name,name"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="目的港">
                <a-input placeholder="请输入目的港" v-model="queryParam.destination"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="仓库代码">
                <j-search-select-tag placeholder="请选择仓库代码" v-model="queryParam.warehouseId" dict="zm_fba_warehouse_detail,code,code"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收件人">
                <a-input placeholder="请输入收件人" v-model="queryParam.recipient"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="承运商">
                <j-search-select-tag placeholder="请选择承运商" v-model="queryParam.carrier" dict="zm_partner,company,company"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="跟踪号">
                <a-input placeholder="请输入跟踪号" v-model="queryParam.trackingNumber"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="计费时间">
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust" v-model="queryParam.billableTime_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust" v-model="queryParam.billableTime_end"></j-date>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="出货时间">
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust" v-model="queryParam.deliveryTime_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust" v-model="queryParam.deliveryTime_end"></j-date>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="签收时间">
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust" v-model="queryParam.submissionTime_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust" v-model="queryParam.submissionTime_end"></j-date>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('运单表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <zm-waybill-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ZmWaybillModal from './modules/ZmWaybillModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import '@/assets/less/TableExpand.less'

  export default {
    name: "ZmWaybillList",
    mixins:[JeecgListMixin],
    components: {
      ZmWaybillModal
    },
    data () {
      return {
        description: '运单表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'运单号',
            align:"center",
            dataIndex: 'waybillId'
          },
          {
            title:'客户订单号',
            align:"center",
            dataIndex: 'orderId'
          },
          {
            title:'客户名称',
            align:"center",
            dataIndex: 'name_dictText'
          },
          {
            title:'公司名',
            align:"center",
            dataIndex: 'company'
          },
          {
            title:'柜号',
            align:"center",
            dataIndex: 'caseId'
          },
          {
            title:'FBA号',
            align:"center",
            dataIndex: 'fbaId'
          },
          {
            title:'工作号',
            align:"center",
            dataIndex: 'jobId'
          },
          {
            title:'服务',
            align:"center",
            dataIndex: 'service_dictText'
          },
          {
            title:'派送方式',
            align:"center",
            dataIndex: 'deliveryMethod'
          },
          {
            title:'目的港',
            align:"center",
            dataIndex: 'destination'
          },
          {
            title:'仓库代码',
            align:"center",
            dataIndex: 'warehouseId_dictText'
          },
          {
            title:'收件人',
            align:"center",
            dataIndex: 'recipient'
          },
          {
            title:'收件人地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title:'件数',
            align:"center",
            dataIndex: 'number'
          },
          {
            title:'中文名',
            align:"center",
            dataIndex: 'cnname'
          },
          {
            title:'英文名',
            align:"center",
            dataIndex: 'enname'
          },
          {
            title:'收费重量(KG)',
            align:"center",
            dataIndex: 'chargedWeight'
          },
          {
            title:'实际重量(KG)',
            align:"center",
            dataIndex: 'actualWeight'
          },
          {
            title:'材积重',
            align:"center",
            dataIndex: 'volumeWeight'
          },
          {
            title:'计泡系数',
            align:"center",
            dataIndex: 'foamingFactor'
          },
          {
            title:'体积(m³)',
            align:"center",
            dataIndex: 'volume'
          },
          {
            title:'申报价值',
            align:"center",
            dataIndex: 'declaredValue'
          },
          {
            title:'报关方式',
            align:"center",
            dataIndex: 'customsDeclarationMethod_dictText'
          },
          {
            title:'交税方式',
            align:"center",
            dataIndex: 'taxPaymentMethod_dictText'
          },
          {
            title:'VAT号',
            align:"center",
            dataIndex: 'vat'
          },
          {
            title:'承运商',
            align:"center",
            dataIndex: 'carrier_dictText'
          },
          {
            title:'跟踪号',
            align:"center",
            dataIndex: 'trackingNumber'
          },
          {
            title:'问题件',
            align:"center",
            dataIndex: 'problemPiece'
          },
          {
            title:'内部备注',
            align:"center",
            dataIndex: 'inRemark'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'退件原因',
            align:"center",
            dataIndex: 'reasonReturn'
          },
          {
            title:'应付',
            align:"center",
            dataIndex: 'handle'
          },
          {
            title:'已付',
            align:"center",
            dataIndex: 'paid'
          },
          {
            title:'未付',
            align:"center",
            dataIndex: 'unpaid'
          },
          {
            title:'计费方式',
            align:"center",
            dataIndex: 'billingMethod'
          },
          {
            title:'物品属性',
            align:"center",
            dataIndex: 'itemProperties_dictText'
          },
          {
            title:'计费时间',
            align:"center",
            dataIndex: 'billableTime'
          },
          {
            title:'出货时间',
            align:"center",
            dataIndex: 'deliveryTime'
          },
          {
            title:'签收时间',
            align:"center",
            dataIndex: 'submissionTime'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/zmexpress/zmWaybill/list",
          delete: "/zmexpress/zmWaybill/delete",
          deleteBatch: "/zmexpress/zmWaybill/deleteBatch",
          exportXlsUrl: "/zmexpress/zmWaybill/exportXls",
          importExcelUrl: "zmexpress/zmWaybill/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
         fieldList.push({type:'string',value:'waybillId',text:'运单号',dictCode:''})
         fieldList.push({type:'string',value:'orderId',text:'客户订单号',dictCode:''})
         fieldList.push({type:'string',value:'name',text:'客户名称',dictCode:'zm_client_main,username,username'})
         fieldList.push({type:'string',value:'company',text:'公司名',dictCode:''})
         fieldList.push({type:'string',value:'caseId',text:'柜号',dictCode:''})
         fieldList.push({type:'string',value:'fbaId',text:'FBA号',dictCode:''})
         fieldList.push({type:'string',value:'jobId',text:'工作号',dictCode:''})
         fieldList.push({type:'sel_search',value:'service',text:'服务',dictTable:'zm_service', dictText:'name', dictCode:'name'})
         fieldList.push({type:'string',value:'deliveryMethod',text:'派送方式',dictCode:''})
         fieldList.push({type:'string',value:'destination',text:'目的港',dictCode:'zm_airport,name,name'})
         fieldList.push({type:'sel_search',value:'warehouseId',text:'仓库代码',dictTable:'zm_fba_warehouse_detail', dictText:'code', dictCode:'code'})
         fieldList.push({type:'string',value:'recipient',text:'收件人',dictCode:''})
         fieldList.push({type:'string',value:'address',text:'收件人地址',dictCode:''})
         fieldList.push({type:'string',value:'number',text:'件数',dictCode:''})
         fieldList.push({type:'string',value:'cnname',text:'中文名',dictCode:''})
         fieldList.push({type:'string',value:'enname',text:'英文名',dictCode:''})
         fieldList.push({type:'string',value:'chargedWeight',text:'收费重量(KG)',dictCode:''})
         fieldList.push({type:'string',value:'actualWeight',text:'实际重量(KG)',dictCode:''})
         fieldList.push({type:'string',value:'volumeWeight',text:'材积重',dictCode:''})
         fieldList.push({type:'string',value:'foamingFactor',text:'计泡系数',dictCode:''})
         fieldList.push({type:'string',value:'volume',text:'体积(m³)',dictCode:''})
         fieldList.push({type:'string',value:'declaredValue',text:'申报价值',dictCode:''})
         fieldList.push({type:'string',value:'customsDeclarationMethod',text:'报关方式',dictCode:'customs_eclaration'})
         fieldList.push({type:'string',value:'taxPaymentMethod',text:'交税方式',dictCode:'tax_payment'})
         fieldList.push({type:'string',value:'vat',text:'VAT号',dictCode:''})
         fieldList.push({type:'sel_search',value:'carrier',text:'承运商',dictTable:'zm_partner', dictText:'company', dictCode:'company'})
         fieldList.push({type:'string',value:'trackingNumber',text:'跟踪号',dictCode:''})
         fieldList.push({type:'string',value:'problemPiece',text:'问题件',dictCode:''})
         fieldList.push({type:'string',value:'inRemark',text:'内部备注',dictCode:''})
         fieldList.push({type:'string',value:'remark',text:'备注',dictCode:''})
         fieldList.push({type:'string',value:'reasonReturn',text:'退件原因',dictCode:''})
         fieldList.push({type:'string',value:'handle',text:'应付',dictCode:''})
         fieldList.push({type:'string',value:'paid',text:'已付',dictCode:''})
         fieldList.push({type:'string',value:'unpaid',text:'未付',dictCode:''})
         fieldList.push({type:'string',value:'billingMethod',text:'计费方式',dictCode:''})
         fieldList.push({type:'string',value:'itemProperties',text:'物品属性',dictCode:'item_properties'})
         fieldList.push({type:'datetime',value:'billableTime',text:'计费时间'})
         fieldList.push({type:'datetime',value:'deliveryTime',text:'出货时间'})
         fieldList.push({type:'datetime',value:'submissionTime',text:'签收时间'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>