<template>
  <div class="page conteiner">
    <form class="form" @submit.prevent="handleSubmit">
      <h2 class="container__title">Создание личного аккаунта</h2>
      <div class="form__input-container">
        <label class="lable">Адрес личной эл.почты</label>
        <input
          v-model.trim="login"
          :class="{
            input_error: loginError.isError,
          }"
          @focus="loginFocus = true"
          type="email"
          class="input"
          required
          minlength="5"
        />
        <span class="error">{{ loginError.text }}</span>
      </div>
      <div class="form__input-container">
        <label class="lable">Пароль</label>
        <input
          v-model.trim="password"
          @focus="passwordFocus = true"
          :class="{
            input_error: passwordError.isError,
          }"
          type="password"
          class="input"
          required
          minlength="1"
        />
        <span class="error">{{ passwordError.text }}</span>
      </div>
      <div class="form__agree">
        <input
          v-model="isAgree"
          type="checkbox"
          value="Я согласен получать маркетинговые материалы от Binance."
        />
        <p class="text">
          Я согласен получать маркетинговые материалы от My Binance.
        </p>
      </div>
      <button
        type="submit"
        class="button"
        :disabled="buttonDisabled"
        :class="{
          button_disable: buttonDisabled,
        }"
      >
        Создание личного аккаунта
      </button>
      <div class="need-account">
        Уже есть аккаунт?
        <router-link class="need-account__link" to="/login">Войти</router-link>
      </div>
    </form>
  </div>
</template>
<script>
import { axiosService } from "@/api";
export default {
  name: "RegisterView",
  data() {
    return {
      login: "",
      loginFocus: false,
      passwordFocus: false,
      password: "",
      isAgree: false,
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await axiosService.post("/user/create", {
          username: this.login,
          password: this.password,
        });
        if (response.status === 200) {
          console.log(response.data);
          this.$router.push({ name: "login" });
        }
      } catch (err) {}
    },
  },
  computed: {
    loginError() {
      const login = this.login.trim();
      if (this.loginFocus) {
        if (!login)
          return { text: "Поле обязательно к заполнению", isError: true };
        if (login.length < 5)
          return { text: "Минимальная длинна 5 символов", isError: true };
        if (!login.includes("@"))
          return { text: "Это поле должно быть почтой", isError: true };
        return { text: "", isError: false };
      }
      return { text: "", isError: false };
    },
    passwordError() {
      const password = this.password.trim();
      if (this.passwordFocus) {
        if (!password)
          return { text: "Поле обязательно к заполнению", isError: true };
        return { text: "", isError: false };
      }
      return { text: "", isError: false };
    },
    buttonDisabled() {
      if (this.loginError.isError && this.passwordError.isError) return true;
      if (this.loginError.isError || this.passwordError.isError) return true;
      else return false;
    },
  },
};
</script>
<style lang="scss" scoped>
.conteiner {
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
  padding-top: 40px;

  &__title {
    margin: 0px 0px 16px;
    min-width: 0px;
    font-weight: 600;
    font-size: 32px;
    line-height: 40px;
    color: rgb(30, 35, 41);
  }
}

.form {
  max-width: 384px;
  display: flex;
  flex-direction: column;
  width: 100%;
  margin: 0 auto;
  gap: 24px;

  &__agree {
    display: flex;
    align-items: start;
    gap: 10px;

    p {
      font-weight: 400;
      font-size: 14px;
      line-height: 20px;
    }
  }

  &__input-container {
    display: flex;
    flex-direction: column;
  }
}

.lable {
  margin: 0px 0px 4px;
  min-width: 0px;
  display: flex;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  width: 100%;
  -moz-box-align: center;
  align-items: center;
  color: rgb(30, 35, 41);
  cursor: auto;
}

.input {
  color: rgb(30, 35, 41);
  font-size: 14px;
  padding-left: 12px;
  padding-right: 12px;
  outline: none;
  background-color: inherit;
  opacity: 1;
  width: 100%;
  line-height: 1.6;
  height: 48px;
  border: 1px solid rgb(234, 236, 239);
  border-radius: 4px;

  &_error {
    border-color: red;
  }

  &:focus {
    border-color: rgb(240, 185, 11);
  }
}

.button {
  background-color: rgb(252, 213, 53);
  opacity: 0.9;
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
  white-space: normal;
  width: 100%;
  height: 48px;

  &_disable {
    background-color: #f5f5f5;
  }
}

.need-account {
  font-weight: 500;
  font-size: 14px;
  line-height: 20px;
  color: rgb(112, 122, 138);

  &__link {
    font-size: 14px;
    line-height: 20px;
    color: rgb(201, 148, 0);
    font-weight: 500;
    cursor: pointer;
    display: inline;
    text-decoration: none;

    &:hover {
      color: rgb(252, 213, 53);
      text-decoration: underline;
    }
  }
}

.error {
  margin-top: 8px;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  width: 100%;
  color: rgb(207, 48, 74);
}
</style>