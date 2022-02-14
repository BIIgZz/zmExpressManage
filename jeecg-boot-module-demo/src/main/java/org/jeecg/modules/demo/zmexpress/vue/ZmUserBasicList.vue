<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('用户基础信息')">导出</a-button>
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
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
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

    <zm-user-basic-modal ref="modalForm" @ok="modalFormOk"></zm-user-basic-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ZmUserBasicModal from './modules/ZmUserBasicModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'ZmUserBasicList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ZmUserBasicModal
    },
    data () {
      return {
        description: '用户基础信息管理页面',
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
            title:'用户名',
            align:"center",
            dataIndex: 'username'
          },
          {
            title:'昵称',
            align:"center",
            dataIndex: 'nickname'
          },
          {
            title:'结算方式',
            align:"center",
            dataIndex: 'pay_dictText'
          },
          {
            title:'联系人',
            align:"center",
            dataIndex: 'linkman'
          },
          {
            title:'公司',
            align:"center",
            dataIndex: 'company'
          },
          {
            title:'手机',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'客户等级',
            align:"center",
            dataIndex: 'level'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/zmexpress/zmUserBasic/list",
          delete: "/zmexpress/zmUserBasic/delete",
          deleteBatch: "/zmexpress/zmUserBasic/deleteBatch",
          exportXlsUrl: "/zmexpress/zmUserBasic/exportXls",
          importExcelUrl: "zmexpress/zmUserBasic/importExcel",
          
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
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'codeId',text:'用户编号',dictCode:''})
        fieldList.push({type:'string',value:'username',text:'用户名',dictCode:''})
        fieldList.push({type:'string',value:'nickname',text:'昵称',dictCode:''})
        fieldList.push({type:'string',value:'pay',text:'结算方式',dictCode:'pay_method'})
        fieldList.push({type:'string',value:'linkman',text:'联系人',dictCode:''})
        fieldList.push({type:'string',value:'company',text:'公司',dictCode:''})
        fieldList.push({type:'string',value:'email',text:'邮箱',dictCode:''})
        fieldList.push({type:'string',value:'phone',text:'手机',dictCode:''})
        fieldList.push({type:'string',value:'tel',text:'电话',dictCode:''})
        fieldList.push({type:'string',value:'qq',text:'QQ',dictCode:''})
        fieldList.push({type:'string',value:'level',text:'客户等级',dictCode:''})
        fieldList.push({type:'sel_user',value:'financialRepresentative',text:'财务代表'})
        fieldList.push({type:'sel_user',value:'customsService',text:'客服代表'})
        fieldList.push({type:'sel_user',value:'saleRepresentative',text:'销售代表'})
        fieldList.push({type:'sel_user',value:'salesSource',text:'销售来源'})
        fieldList.push({type:'sel_depart',value:'branchOffice',text:'分公司'})
        fieldList.push({type:'sel_search',value:'area',text:'收货区域',dictTable:'zm_area_receiving', dictText:'area_name', dictCode:'area_name'})
        fieldList.push({type:'string',value:'station',text:'站点',dictCode:''})
        fieldList.push({type:'string',value:'note',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>