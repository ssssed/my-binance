<template>
  <Header />
  <router-view />
  <Footer />
</template>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  min-height: 100vh;
  margin: 0;
  position: relative;
}

.page {
  min-height: calc(100vh - 70px - 85px);
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
<script setup>
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer/Footer.vue";
import { useStore } from "vuex";
function setup() {
  const store = useStore();
  const mySessionData = JSON.parse(localStorage.getItem("userInfo"));
  const isAdminAuth = sessionStorage.getItem("adminToken");
  if (mySessionData) {
    store.dispatch("changeAuthStatus", mySessionData);
  }

  if(isAdminAuth) {
    store.dispatch("changeAdminAuthStatus", true);
  }
}
setup();
</script>