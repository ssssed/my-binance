import { createStore } from "vuex";

export default createStore({
  state: {
    isAuth: false,
    isAdminAuth: false,
    userInfo: {},
  },
  getters: {
    getAuthStatus(state) {
      return state.isAuth;
    },
    getUserInfo(state) {
      return state.userInfo;
    },
    getAdminAuthStatus(state) {
      return state.isAdminAuth;
    }
  },
  mutations: {
    updateAuthStatus(state, payload) {
      state.isAuth = payload;
    },
    updateUserInfo(state, payload) {
      state.userInfo = payload;
    },
    updateAdminAuthStatus(state, payload) {
      state.isAdminAuth = payload;
    }
  },
  actions: {
    async changeAuthStatus({ commit }, payload) {
      commit('updateAuthStatus', payload);
    },
    async changeUserInfo({ commit }, payload) {
      commit('updateUserInfo', payload);
    },
    async changeAdminAuthStatus({commit}, payload) {
      commit('updateAdminAuthStatus', payload);
    }
  },
  modules: {},
});
