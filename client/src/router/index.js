import {createRouter, createWebHistory} from "vue-router";
import HomeView from "../views/HomeView.vue";

const routes = [
    {
        path: "/",
        name: "home",
        component: HomeView
    },
    {
        path: "/about",
        name: "about",
        component: () =>
            import(/* webpackChunkName: "about" */ "../views/AboutView.vue")
    },
    {
        path: "/markets",
        name: "markets",
        component: () =>
            import(/* webpackChunkName: "about" */ "../views/MarketsView.vue")
    },
    {
        path: "/convert",
        name: "convert",
        component: () =>
            import(/* webpackChunkName: "about" */ "../views/ConvertView.vue")
    },
    {
        path: "/profile",
        name: "profile",
        component: () =>
            import(/* webpackChunkName: "about" */ "../views/AboutView.vue")
    },
    {
        path: "/register",
        name: "register",
        component: () => import("../views/RegisterView.vue")
    },
    {
        path: "/login",
        name: "login",
        component: () => import("../views/LoginView.vue")
    },
    {
        path: "/admin",
        name: "admin",
        component: () =>
            import(/* webpackChunkName: "about" */ "../views/AdminView.vue")
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
