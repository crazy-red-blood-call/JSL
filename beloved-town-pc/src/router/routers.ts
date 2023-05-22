import login from '@/views/login/index.vue'
import home from '@/views/home/index.vue'
import user from '@/views/system/user/index.vue'
import role from '@/views/system/role/index.vue'
import navigation from '@/views/navigation/index.vue'
import bigscreen from '@/views/bigscreen/index.vue'
import ipscreen from '@/views/ipscreen/index.vue'
import common from '@/views/common/index.vue'
import manicure from '@/views/manicure/index.vue'

export default [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'login',
        component: login
    },
    {
        path: '/bigscreen',
        name: 'bigscreen',
        component: bigscreen
    },
    {
        path: '/common',
        name: 'common',
        component: common
    },
    {
        path: '/ipscreen',
        name: 'ipscreen',
        component: ipscreen
    },
    {
        path: '/navigation',
        name: 'navigation',
        component: navigation,
        children: [
            {
                path: 'home',
                name: 'home',
                component: home
            },
            {
                path: 'user',
                name: 'user',
                component: user,
                meta: {
                    breadcrumb: ['用户管理']
                }
            },
            {
                path: 'role',
                name: 'role',
                component: role,
                meta: {
                    breadcrumb: ['角色管理']
                }
            },
            {
                path: 'manicure',
                name: 'manicure',
                component: manicure,
                meta: {
                    breadcrumb: ['美甲管理']
                }
            },
            {
                path: 'tryit',
                name: 'tryit',
                component: manicure,
                meta: {
                    breadcrumb: ['试戴效果'],
                    isTryFlag: true,
                }
            }
        ]
    }
]
