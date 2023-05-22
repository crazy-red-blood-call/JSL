import * as apiUrl from './url'
import axios from "axios"
import md5 from "js-md5";
import {ElMessage} from "element-plus";
import {router} from "@/router";

const server = axios.create({
    baseURL: apiUrl.baseURL,
    timeout: 15000,
    headers: {
        "Content-Type": "application/json;charset=utf-8"
    }
});

server.interceptors.request.use(config => {
    console.log('--->>',config)
    config.headers = config.headers || {};
    if (localStorage.getItem("token")) {
        config.headers.setAuthorization(localStorage.getItem("token"));
    }
    const cipherStr: string = `${JSON.stringify(config.data).replaceAll('"', '')}${config.url}PiD8p2d2pOI9==`;
    config.headers.cipher = md5(cipherStr)
    return config;
})

server.interceptors.response.use(res => {
    const code: string = res.data.code;
    if ("000000" !== code) {
        ElMessage({
            message: res.data.msg,
            type: 'error',
        })
    } else {
        // ElMessage({
        //     message: res.data.msg,
        //     type: "success",
        // })
    }

    return res.data
}, error => {
    console.log('error ===>', error);
    if (error.response?.status) {
        switch (error.response.status) {
            // 接口签名不对
            case 417:
                ElMessage({
                    message: '接口签名失败',
                    type: 'error',
                });
                break;
            // 重新登录
            case 401:
                ElMessage({
                    message: '登录过期，请重新登录',
                    type: 'error',
                });
                // router.push({name: 'login'});
                break;
            default:
                break;
        }
    } else {
        ElMessage({
            message: '网络超时',
            type: 'error',
        });
    }
    return Promise.reject(error.response?.data)
})

export const POST = async function (url: string, param: object) {
    return await server.post(url, param);
};

export const POST_SINGLE = async function (url: string, param: string) {
    return await server.post(url, param, {
        headers: {
            "Content-Type": "text/plain",
        }
    });
};

export const GET = async function (url: string) {
    return await server.get(url);
}
