import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import store from '@/store';

const requireAuth = (to, from, next) => {
  if (to.meta.requiresAuth && !store.getters.getAuthStatus) {
    next({ name: 'login' });
  } else {
    next();
  }
};

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/about',
    name: 'about',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
  },
  {
    path: '/markets',
    name: 'markets',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/MarketsView.vue'),
    beforeEnter: requireAuth,
    meta: { requiresAuth: true },
  },
  {
    path: '/convert',
    name: 'convert',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/ConvertView.vue'),
    beforeEnter: requireAuth,
    meta: { requiresAuth: true },
  },
  {
    path: '/profile',
    name: 'profile',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/ProfileView.vue'),
    beforeEnter: requireAuth,
    meta: { requiresAuth: true },
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue'),
  },
  {
    path: '/admin',
    name: 'admin',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/AdminView.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
