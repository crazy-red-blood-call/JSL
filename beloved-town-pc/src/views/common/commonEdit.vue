<template>
    <div>
        <el-dialog v-model="editForm.visible" :title="editForm.tile" :width="editForm.width" @closed="closeVisible">
            <template #header="{ close, titleId, titleClass }">
                <div class="my-header">
                    <h4 :id="titleId" :class="titleClass">{{ editForm.tile }}</h4>
                </div>
            </template>
            <el-form :model="editForm.form" :rules="editForm.rules" ref="ruleFormRef">
                <el-form-item v-for="(item,index) in editForm.props" :label="item.label" :prop="item.prop"
                              :label-width="item.width" :key="index">
                    <!-- 输入框-->
                    <el-input v-if="!item.type||item.type==='input'" v-model="editForm.form[item.filed]"
                              :disabled="editForm.disabled.includes(item.filed)" clearable/>

<!--                    文本域-->
                    <el-input v-else-if="item.type==='textarea'"
                              v-model="editForm.form[item.filed]"
                              :disabled="editForm.disabled.includes(item.filed)"
                              type="textarea" clearable />

                    <!-- 选择框 -->
                    <el-select v-else-if="item.type==='selector'"
                               v-model="editForm.form[item.filed]"
                               :disabled="editForm.disabled.includes(item.filed)"
                               style="width: 100%"
                               placeholder="请选择" clearable>
                        <el-option
                                v-for="item in item.selectOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        />
                    </el-select>

<!--                    开关-->
                        <el-switch  v-else-if="item.type==='switch'"
                                    :disabled="editForm.disabled.includes(item.filed)"
                                    v-model="editForm.form[item.filed]" />

<!--                    多选-->
                    <el-checkbox-group v-else-if="item.type==='checkbox'"
                                       :disabled="editForm.disabled.includes(item.filed)"
                                       v-model="editForm.form[item.filed]">
                        <el-checkbox v-for="(checkboxItem,checkboxIndex) in item.options" :key="checkboxIndex" :label="checkboxItem.label" :name="checkboxItem.name" />
                    </el-checkbox-group>

<!--                    单选-->
                    <el-radio-group v-else-if="item.type==='radio'"
                                    :disabled="editForm.disabled.includes(item.filed)"
                                    v-model="editForm.form[item.filed]">
                        <el-radio v-for="(radioItem,radioIndex) in item.options" :key="radioIndex" :label="radioItem.name" >{{radioItem.label}}</el-radio>
                    </el-radio-group>

<!--                    时间选择器-->
                    <el-time-picker
                        v-else-if="item.type==='time'"
                        :disabled="editForm.disabled.includes(item.filed)"
                        v-model="editForm.form[item.filed]"
                        :placeholder="item.placeholder" clearable/>


<!--                    日期选择-->
                    <el-date-picker
                        v-else-if="item.type==='date'"
                        v-model="editForm.form[item.filed]"
                        :disabled="editForm.disabled.includes(item.filed)"
                        type="date"
                        :placeholder="item.placeholder"
                        clearable
                    />


<!--                    日期+时间-->
                    <el-date-picker
                        v-else-if="item.type==='dateTime'"
                        v-model="editForm.form[item.filed]"
                        :disabled="editForm.disabled.includes(item.filed)"
                        type="datetime"
                        :placeholder="item.placeholder"
                        clearable
                    />


<!--                    选择文件-->
                    <el-upload
                        v-else-if="item.type==='picture'"
                        v-model:file-list="item.fileList"
                        :action="item.action"
                        list-type="picture-card"
                        :limit="1"
                        :on-preview="item.handlePictureCardPreview"
                        :on-remove="item.handleRemove"
                        :on-success="
                                (response, file, fileList) => {
                                  return item.success(response, file, fileList, item);
                                }
                              "
                        :disabled="editForm.disabled.includes(item.filed)"
                    >
                        <el-icon><Plus /></el-icon>
                    </el-upload>

                    <el-dialog v-model="item.dialogVisible">
                        <img w-full :src="item.dialogImageUrl" :alt="item.alt" />
                    </el-dialog>

                </el-form-item>
            </el-form>
            <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="closeVisible">取消</el-button>
                    <el-button type="primary" @click="updateEdit(ruleFormRef)">
                      确认
                    </el-button>
                  </span>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import {ruleFormRef} from "@/hooks/user/editUser.js";
import {ref} from "vue";
import {FormInstance} from "element-plus";

const {editForm} = defineProps(['editForm']);
const emits = defineEmits(['updateEdit', 'closeVisible']);
const ruleFormRef = ref<FormInstance>();

const updateEdit = function (ruleFormRef: any) {
    emits('updateEdit', ruleFormRef);
};

const closeVisible = function () {
    emits('closeVisible', false);
};

</script>

<style scoped>

</style>
