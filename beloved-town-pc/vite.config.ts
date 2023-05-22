import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
// import path = require("path");
// const path = require('path');
// @ts-ignore
import {resolve} from "path";

// @ts-ignore
export default defineConfig({
    base: './',
    plugins: [vue()],
    server: {
        hmr: {
            overlay: false,
        },
        proxy: {
            '/api': {
                target: 'http://127.0.0.1:8081/',
                changeOrigin: true,
                rewrite: path => path.replace(/^\/api/, '')
            }
        }
    },
    resolve: {
        alias: {
            // @ts-ignore
            '@': resolve(__dirname, './src'),
        }
    }
})
