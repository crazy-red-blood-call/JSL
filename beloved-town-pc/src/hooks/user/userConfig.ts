import {reactive, ref} from "vue";
import {FormInstance} from "element-plus";

export const userEditForm = reactive({
    visible: false,
    tile: '',
    width: 500,
    form: {},
    disabled: [] as string[],
    props: [
        {label: '登录账号', prop: 'accountName', width: 80, filed: 'accountName'},
        {label: '姓名', prop: 'userName', width: 80, filed: 'userName'},
        {label: '手机号码', prop: 'phone', width: 80, filed: 'phone'},
        {
            label: '性别', prop: 'gender', width: 80, filed: 'gender', type: 'selector', selectOptions: [
                {label:'男',value:'F'},
                {label:'女',value:'M'},
            ]
        },
    ],
    rules: {
        accountName: [
            {required: true, message: '登录账号不能为空', trigger: 'blur'},
        ],
        userName: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
        ],
        phone: [
            {required: true, message: '手机号码不能为空', trigger: 'blur'},
        ],
        gender: [
            {required: true, message: '性别不能为空', trigger: 'blur'},
        ],
    }
})

const genderFormatter = function (row: any) {
    return 'F' === row.gender ? '男' : '女';
};

export const userTableInfo = reactive({
    props: [
        {filed: 'accountName', label: '账户名称', minWidth: 40},
        {filed: 'userName', label: '姓名', minWidth: 30},
        {filed: 'gender', label: '性别', minWidth: 20, formatter: genderFormatter},
        {filed: 'createTime', label: '创建时间', minWidth: 50},
        {filed: 'updateTime', label: '更新时间', minWidth: 50},
    ],
    operationFlag: true,
    tableList: [],
    selectionFlag: true,
    pageInfo: {
        currentPage: 1,
        pageSize: 10,
        total: 10,
    },
    loading: false
})

