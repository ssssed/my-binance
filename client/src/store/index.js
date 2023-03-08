import { createStore } from "vuex";

export default createStore({
  state: {
    isAuth: false,
    userInfo: {},
  },
  getters: {
    getAuthStatus(state) {
      return state.isAuth;
    },
    getUserInfo(state) {
      return state.userInfo;
    },
  },
  mutations: {
    updateAuthStatus(state, payload) {
      state.isAuth = payload;
    },
    updateUserInfo(state, payload) {
      state.userInfo = payload;
    },
  },
  actions: {
    async changeAuthStatus({ commit }, payload) {
      commit('updateAuthStatus', payload);
    },
    async changeUserInfo({ commit }, payload) {
      commit('updateUserInfo', payload);
    },
  },
  modules: {},
});
