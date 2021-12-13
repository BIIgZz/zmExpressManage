<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="8" >
            <a-form-model-item label="运单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="waybillId">
              <a-input v-model="model.waybillId" placeholder="请输入运单号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="客户订单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="orderId">
              <a-input v-model="model.orderId" placeholder="请输入客户订单号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
              <j-dict-select-tag type="list" v-model="model.name" dictCode="zm_client_main,username,username" placeholder="请选择客户名称" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="公司名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="company">
              <a-input v-model="model.company" placeholder="请输入公司名" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="柜号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="caseId">
              <a-input v-model="model.caseId" placeholder="请输入柜号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="FBA号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fbaId">
              <a-input v-model="model.fbaId" placeholder="请输入FBA号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="工作号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="jobId">
              <a-input v-model="model.jobId" placeholder="请输入工作号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="服务" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="service">
              <j-search-select-tag v-model="model.service" dict="zm_service,name,name"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="派送方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deliveryMethod">
              <a-input v-model="model.deliveryMethod" placeholder="请输入派送方式" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="目的港" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="destination">
              <a-input v-model="model.destination" placeholder="请输入目的港" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="仓库代码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="warehouseId">
              <j-search-select-tag v-model="model.warehouseId" dict="zm_fba_warehouse_detail,code,code"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="收件人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="recipient">
              <a-input v-model="model.recipient" placeholder="请输入收件人" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="收件人地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="address">
              <a-input v-model="model.address" placeholder="请输入收件人地址" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="件数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="number">
              <a-input v-model="model.number" placeholder="请输入件数" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="中文名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cnname">
              <a-input v-model="model.cnname" placeholder="请输入中文名" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="英文名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="enname">
              <a-input v-model="model.enname" placeholder="请输入英文名" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="收费重量(KG)" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="chargedWeight">
              <a-input v-model="model.chargedWeight" placeholder="请输入收费重量(KG)" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="实际重量(KG)" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="actualWeight">
              <a-input v-model="model.actualWeight" placeholder="请输入实际重量(KG)" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="材积重" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="volumeWeight">
              <a-input v-model="model.volumeWeight" placeholder="请输入材积重" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="计泡系数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="foamingFactor">
              <a-input v-model="model.foamingFactor" placeholder="请输入计泡系数" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="体积(m³)" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="volume">
              <a-input v-model="model.volume" placeholder="请输入体积(m³)" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="申报价值" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="declaredValue">
              <a-input v-model="model.declaredValue" placeholder="请输入申报价值" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="报关方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="customsDeclarationMethod">
              <j-dict-select-tag type="list" v-model="model.customsDeclarationMethod" dictCode="customs_eclaration" placeholder="请选择报关方式" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="交税方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="taxPaymentMethod">
              <j-dict-select-tag type="list" v-model="model.taxPaymentMethod" dictCode="tax_payment" placeholder="请选择交税方式" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="VAT号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="vat">
              <a-input v-model="model.vat" placeholder="请输入VAT号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="承运商" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carrier">
              <j-search-select-tag v-model="model.carrier" dict="zm_partner,company,company"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="跟踪号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="trackingNumber">
              <a-input v-model="model.trackingNumber" placeholder="请输入跟踪号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="问题件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="problemPiece">
              <a-input v-model="model.problemPiece" placeholder="请输入问题件" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="内部备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="inRemark">
              <a-input v-model="model.inRemark" placeholder="请输入内部备注" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" placeholder="请输入备注" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="退件原因" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="reasonReturn">
              <a-input v-model="model.reasonReturn" placeholder="请输入退件原因" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="应付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="handle">
              <a-input v-model="model.handle" placeholder="请输入应付" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="已付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="paid">
              <a-input v-model="model.paid" placeholder="请输入已付" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="未付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="unpaid">
              <a-input v-model="model.unpaid" placeholder="请输入未付" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="计费方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billingMethod">
              <a-input v-model="model.billingMethod" placeholder="请输入计费方式" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="物品属性" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="itemProperties">
              <j-multi-select-tag type="checkbox" v-model="model.itemProperties" dictCode="item_properties" placeholder="请选择物品属性" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="计费时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billableTime">
              <j-date placeholder="请选择计费时间" v-model="model.billableTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="出货时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deliveryTime">
              <j-date placeholder="请选择出货时间" v-model="model.deliveryTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="签收时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="submissionTime">
              <j-date placeholder="请选择签收时间" v-model="model.submissionTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="货柜详情" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="zmImportGoodTable.loading"
          :columns="zmImportGoodTable.columns"
          :dataSource="zmImportGoodTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'ZmWaybillForm',
    mixins: [JEditableTableModelMixin],
    components: {
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        model:{
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
        },
        refKeys: ['zmImportGood', ],
        tableKeys:['zmImportGood', ],
        activeKey: 'zmImportGood',
        // 货柜详情
        zmImportGoodTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '货箱编号',
              key: 'caseid',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '货箱重量',
              key: 'weight',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '货箱长度',
              key: 'length',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '货箱宽度',
              key: 'width',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '货箱高度',
              key: 'height',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '中文名称',
              key: 'cnName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '英文名称',
              key: 'enName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '申报单价',
              key: 'declaredPrice',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '申报数量',
              key: 'declaredNumber',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '产品海关编码',
              key: 'hscode',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '产品材料',
              key: 'material',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '产品用途',
              key: 'application',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '品牌',
              key: 'brand',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '品牌类型',
              key: 'type',
              type: FormTypes.select,
              dictCode:"type",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:"0",
            },
            {
              title: '产品型号',
              key: 'model',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '销售链接',
              key: 'link',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '产品售价',
              key: 'price',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '图片链接',
              key: 'picture',
              type: FormTypes.image,
              token:true,
              responseName:"message",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
          ]
        },
        url: {
          add: "/zmexpress/zmWaybill/add",
          edit: "/zmexpress/zmWaybill/edit",
          queryById: "/zmexpress/zmWaybill/queryById",
          zmImportGood: {
            list: '/zmexpress/zmWaybill/queryZmImportGoodByMainId'
          },
        }
      }
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
    },
    methods: {
      addBefore(){
        this.zmImportGoodTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        this.$nextTick(() => {
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.zmImportGood.list, params, this.zmImportGoodTable)
        }
      },
      //校验所有一对一子表表单
      validateSubForm(allValues){
          return new Promise((resolve,reject)=>{
            Promise.all([
            ]).then(() => {
              resolve(allValues)
            }).catch(e => {
              if (e.error === VALIDATE_NO_PASSED) {
                // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
                this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
              } else {
                console.error(e)
              }
            })
          })
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          zmImportGoodList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },

    }
  }
</script>

<style scoped>
</style>