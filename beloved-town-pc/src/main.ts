import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { createPinia } from 'pinia'
import {router} from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import mitt from "mitt";
import * as echarts from "echarts"

console.log('>>>>>>>>>>>',import.meta.env.VITE_NAME)
const app = createApp(App);
const pinia = createPinia();
app.use(pinia)
app.use(router)
app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.config.globalProperties.miffBus = mitt();

app.config.globalProperties.$echarts = echarts
app.mount('#app');
