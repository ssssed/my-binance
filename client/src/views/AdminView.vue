<template>
  <div class="page admin">
    <form v-if="!isLogin" @keydown.enter="handleSubmit" @submit.prevent="handleSubmit">
      <span v-if="isError" class="form__error">Неправильный логин или пароль</span>
      Логин: <input v-model="login" placeholder="логин" type="text" />
      Пароль: <input v-model="password" placeholder="пароль" type="password" />
      <button type="submit">Войти</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "AdminView",
  data() {
    return {
      isLogin: false,
      login: "",
      password: "",
      isError: false,
    };
  },
  methods: {
    handleSubmit() {
      if (this.login === "root" && this.password === "root") {
        this.isLogin = true;
        this.isError = false;
        this.password = this.login = "";
      }
      else {
        this.isError = true;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.admin {
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
}

.form {

  &__error {
    color: red;
    opacity: 0.8;
  }
}
</style>