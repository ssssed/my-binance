<template>
  <header class="header">
    <router-link to="/"
      ><img alt="logo" class="header__logo" src="@/assets/logo.svg"
    /></router-link>
    <nav class="header__nav">
      <router-link
        active-class="header__link_active"
        class="header__link"
        to="/"
        >Главная</router-link
      >
      <router-link
        active-class="header__link_active"
        class="header__link"
        to="/about"
        >О нас</router-link
      >
      <router-link
        v-if="getAuthStatus"
        active-class="header__link_active"
        class="header__link"
        to="/markets"
        >Рынок</router-link
      >
      <router-link
        v-if="getAuthStatus"
        active-class="header__link_active"
        class="header__link"
        to="/convert"
        >Торговля</router-link
      >
      <router-link
        v-if="!getAuthStatus"
        active-class="header__link_active"
        class="header__link"
        to="/login"
        >Войти</router-link
      >
      <router-link
        v-if="getAuthStatus"
        active-class="header__link_active"
        class="header__link"
        to="/profile"
        >Личный кабинет</router-link
      >
    </nav>
  </header>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  name: "Header",
  computed: mapGetters(["getAuthStatus"]),
};
</script>

<style lang="scss" scoped>
.header {
  max-width: 1280px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: white;

  &__logo {
    width: 120px;
    height: 24px;
  }

  &__nav {
    display: flex;
    gap: 20px;
  }

  &__link {
    text-decoration: none;
    color: black;

    &_active {
      text-decoration: underline;
      text-underline-offset: 5px;
    }
  }
}
</style>