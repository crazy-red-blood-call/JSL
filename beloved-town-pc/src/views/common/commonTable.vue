<template>
    <div>
        <div>
            <el-table
                    ref="multipleTableRef"
                    :data="tableInfo.tableList"
                    style="width: 100%"
                    @selection-change="selectChange"
                    class="user-table"
                    :stripe="true"
                    :border="true"
                    :highlight-current-row="true"
                    empty-text="无数据"
                    :header-cell-style="userHeader"
                    v-loading="tableInfo.loading"
            >
                <el-table-column type="selection" width="55" v-if="tableInfo.selectionFlag"/>
                <el-table-column v-for="(item,index) in tableInfo.props" :property="item.filed" :label="item.label"
                                 :min-width="item.minWidth" :key="index" :formatter="item.formatter">
                    <template #default="scope" v-if="item.type">

                        <div v-if="item.type==='img'">
                            <el-image style="width: 100px; height: 100px" :src="scope.row[item.filed]" fit="fill" />
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="操作" min-width="80" v-if="tableInfo.operationFlag">
                    <template #default="scope">
                        <slot name="operation" :row="scope.row"></slot>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="user-table-page">
            <el-config-provider :locale="zhCn">
                <el-pagination
                        background
                        v-model:current-page="tableInfo.pageInfo.currentPage"
                        v-model:page-size="tableInfo.pageInfo.pageSize"
                        :page-sizes="[10, 20, 50]"
                        :small="false"
                        :disabled="false"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="tableInfo.pageInfo.total"
                        @size-change="sizeChange"
                        @current-change="currentChange"
                />
            </el-config-provider>
        </div>

    </div>
</template>

<script lang="ts" setup>
import {ElConfigProvider, ElTable} from "element-plus";
import zhCn from "element-plus/lib/locale/lang/zh-cn";

let {tableInfo} = defineProps(['tableInfo']);
let emits = defineEmits(['selectChange','sizeChange','currentChange'])

const userHeader = function () {
    return {background: 'var(--table-header-bg-color)', 'font-size': '13px'}
};

const selectChange = (val: any[]) => {
    emits('selectChange', val);
}
const sizeChange = (pageSize: number) => {
    emits('sizeChange', pageSize);
}
const currentChange = (pageNo: number) => {
    emits('currentChange', pageNo);
}

</script>

<style scoped lang="less">

.user-table {
    min-height: 300px;
    height: calc(100vh - 226px)
}

.user-table-page {
    height: 50px;
    display: flex;
//border: 1px solid red;
    justify-content: flex-end;
    align-items: flex-end;
}

.el-button--text {
    margin-right: 15px;
}

.el-select {
    width: 300px;
}

.el-input {
    width: 300px;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>
