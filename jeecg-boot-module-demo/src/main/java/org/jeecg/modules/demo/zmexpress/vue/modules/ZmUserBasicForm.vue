<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="用户编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="codeId">
              <a-input v-model="model.codeId" placeholder="请输入用户编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="username">
              <a-input v-model="model.username" placeholder="请输入用户名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="昵称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="nickname">
              <a-input v-model="model.nickname" placeholder="请输入昵称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="结算方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pay">
              <j-dict-select-tag type="list" v-model="model.pay" dictCode="pay_method" placeholder="请选择结算方式" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="linkman">
              <a-input v-model="model.linkman" placeholder="请输入联系人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="company">
              <a-input v-model="model.company" placeholder="请输入公司"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="email">
              <a-input v-model="model.email" placeholder="请输入邮箱"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="手机" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="phone">
              <a-input v-model="model.phone" placeholder="请输入手机"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tel">
              <a-input v-model="model.tel" placeholder="请输入电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="QQ" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="qq">
              <a-input v-model="model.qq" placeholder="请输入QQ"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="客户等级" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="level">
              <a-input v-model="model.level" placeholder="请输入客户等级"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="财务代表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="financialRepresentative">
              <j-select-user-by-dep v-model="model.financialRepresentative" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="客服代表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="customsService">
              <j-select-user-by-dep v-model="model.customsService" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="销售代表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="saleRepresentative">
              <j-select-user-by-dep v-model="model.saleRepresentative" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="销售来源" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="salesSource">
              <j-select-user-by-dep v-model="model.salesSource" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="分公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="branchOffice">
              <j-select-depart v-model="model.branchOffice" multi  />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="收货区域" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="area">
              <j-search-select-tag v-model="model.area" dict="zm_area_receiving,area_name,area_name"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="站点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="station">
              <a-input v-model="model.station" placeholder="请输入站点"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="note">
              <a-input v-model="model.note" placeholder="请输入备注"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'ZmUserBasicForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
           codeId: [
              { required: true, message: '请输入用户编号!'},
           ],
           username: [
              { required: true, message: '请输入用户名!'},
           ],
           email: [
              { required: false},
              { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
           ],
           phone: [
              { required: false},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
           ],
        },
        url: {
          add: "/zmexpress/zmUserBasic/add",
          edit: "/zmexpress/zmUserBasic/edit",
          queryById: "/zmexpress/zmUserBasic/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>