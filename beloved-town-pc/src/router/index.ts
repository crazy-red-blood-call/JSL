import {createRouter, createWebHashHistory} from "vue-router";
import routers from "./routers";

export const router = createRouter({
    history: createWebHashHistory(),
    routes: routers
});

// export default router;