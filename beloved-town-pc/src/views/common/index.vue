<script setup lang="ts">
import CommonEdit from "@/views/common/commonEdit.vue";
import {reactive} from "vue";

const editForm = reactive({
    visible: true,
    tile: '',
    width: 500,
    form: {
        'remark3':'SH'
    },
    action: '',
    disabled: [] as string[],
    props: [
        {label: '输入框', type: 'input', prop: 'code', width: 80, filed: 'code'},
        {label: '文本域', type: 'textarea', prop: 'name', width: 80, filed: 'name'},
        {
            label: '选择框', type: 'selector', prop: 'status', width: 80, filed: 'status', selectOptions: [
                {
                    label: '正常',
                    value: 1,
                }, {
                    label: '待激活',
                    value: 2,
                }, {
                    label: '已停用',
                    value: 0,
                }
            ],
        },
        {label: '开关', type: 'switch', prop: 'fullName', width: 80, filed: 'fullName'},
        {
            label: '复选框', type: 'checkbox', prop: 'remark', width: 80, filed: 'remark', options: [{
                label: '北京',
                name: 'BJ',
            }, {
                label: '上海',
                name: 'SH',
            }]
        },
        {
            label: '单选', type: 'radio', prop: 'remark3', width: 80, filed: 'remark3', options: [{
                label: '北京',
                name: 'BJ',
            }, {
                label: '上海',
                name: 'SH',
            }]
        },
        {label: '时间选择器', type: 'time', prop: 'codse', width: 100, filed: 'codse'},
        {label: '日期选择', type: 'date', prop: 'coddse', width: 100, filed: 'coddse'},
        {label: '日期+时间', type: 'dateTime', prop: 'coddsee', width: 100, filed: 'coddsee'},
        {label: '图片', type: 'picture', fileList: [], action: '', prop: 'coddsee', width: 100, filed: 'coddsee',
            handlePictureCardPreview(){},
            handleRemove(){},
        },


    ],
    rules: {
        code: [
            {required: true, message: '编码不能为空', trigger: 'blur'},
        ],
        name: [
            {required: true, message: '角色名称不能为空', trigger: 'blur'},
        ]
    }
})

</script>


<template>

  <div>
      <common-edit :edit-form="editForm"/>
  </div>
</template>

<style scoped lang="less">

</style>
