<template>
  <form
    class="form"
    @keydown.enter="handleSubmit"
    @submit.prevent="handleSubmit"
  >
    <span v-if="isError" class="form__error"
      >Неправильный логин или пароль</span
    >
    <div class="form__controll">
      Логин:
      <input
        class="form__input"
        v-model="login"
        placeholder="логин"
        type="text"
      />
    </div>
    <div class="form__controll">
      Пароль:
      <input
        class="form__input"
        v-model="password"
        placeholder="пароль"
        type="password"
      />
    </div>
    <button class="form__submit" type="submit">Войти</button>
  </form>
</template>

<script>
import { axiosService } from "@/api";
import { mapActions } from "vuex";
export default {
  name: "AdminForm",
  data() {
    return {
      login: "",
      password: "",
      isError: false,
    };
  },
  methods: {
    ...mapActions(["changeAdminAuthStatus"]),
    async handleSubmit() {
      try {
        const response = await axiosService.post("/admin/auth", {
          username: this.login,
          password: this.password,
        });
        if (response.status === 200) {
          this.isError = false;
          this.changeAdminAuthStatus(true);
          const adminToken =
            response.data.username + ":" + response.data.password;
          sessionStorage.setItem("adminToken", adminToken);
        }
      } catch (e) {
        this.changeAdminAuthStatus(false);
        sessionStorage.clear();
        this.isError = true;
        console.error(e);
      } finally {
        this.password = this.login = "";
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.form {
  max-width: 300px;
  margin: 40px auto 0;
  display: flex;
  flex-direction: column;
  gap: 20px;

  &__controll {
    display: flex;
    align-items: center;
    text-align: center;
    justify-content: space-between;
  }

  &__input {
    max-width: 230px;
    width: 100%;
    color: rgb(30, 35, 41);
    font-size: 14px;
    padding-left: 12px;
    padding-right: 12px;
    outline: none;
    background-color: inherit;
    opacity: 1;
    line-height: 1.6;
    height: 48px;
    border: 2px solid rgb(234, 236, 239);
    border-radius: 4px;

    &:focus {
      border-color: rgb(240, 185, 11);
    }
  }

  &__error {
    color: red;
    opacity: 0.8;
  }

  &__submit {
    margin: 0px;
    appearance: none;
    user-select: none;
    cursor: pointer;
    overflow: hidden;
    text-overflow: ellipsis;
    display: inline-flex;
    -moz-box-align: center;
    align-items: center;
    -moz-box-pack: center;
    justify-content: center;
    box-sizing: border-box;
    font-size: 16px;
    font-family: inherit;
    font-weight: 500;
    text-align: center;
    text-decoration: none;
    outline: none;
    padding: 12px 24px;
    line-height: 24px;
    min-width: 80px;
    word-break: keep-all;
    color: rgb(24, 26, 32);
    border-radius: 4px;
    min-height: 24px;
    border: medium none;
    background-image: none;
    background-color: rgb(252, 213, 53);
    white-space: normal;
    width: 100%;
    height: 48px;
  }
}
</style>