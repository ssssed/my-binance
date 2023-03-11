<template>
  <div class="user">
    <button class="user__button" @click="toggleEditMode">изменить</button>
    <button class="user__button" @click="handleDeleteUser(user)">
      удалить
    </button>
    <p v-if="!isEdit" class="user__text">Логин: {{ login }}</p>
    <p v-if="isEdit" class="user__text">
      Логин:
      <input
        v-model="login"
        @keydown.enter="handleUpdateUserDate"
        class="user__input"
      />
    </p>
    <p v-if="!isEdit" class="user__text">Пароль: {{ password }}</p>
    <p v-if="isEdit" class="user__text">
      Пароль:
      <input
        v-model="password"
        @keydown.enter="handleUpdateUserDate"
        class="user__input"
      />
    </p>
    <p v-if="!isEdit" class="user__text">Аватарка: {{ avatar }}</p>
    <p v-if="isEdit" class="user__text">
      Аватарка:
      <input
        v-model="avatar"
        @keydown.enter="handleUpdateUserDate"
        class="user__input"
      />
    </p>
  </div>
</template>

<script>
import { axiosService } from "@/api";
export default {
  name: "UserInfo",
  props: {
    user: Object,
  },
  data() {
    return {
      isEdit: false,
      login: this.user.username || "",
      password: this.user.password || "",
      avatar: this.user.avatar || "null",
    };
  },
  methods: {
    async handleDeleteUser(user) {
      const adminToken = sessionStorage.getItem("adminToken");
      try {
        const response = await axiosService.delete(
          `/admin/users/delete/${user.id}`,
          {
            headers: {
              Authorization: adminToken,
            },
          }
        );
        if (response.status === 200) {
          this.$emit("updateUsers", response.data);
          console.log(response.data);
        }
      } catch (error) {
        console.error(error);
      }
    },
    async handleUpdateUserDate() {
      const adminToken = sessionStorage.getItem("adminToken");
      try {
        const response = await axiosService.patch(
          `/admin/users/${this.user.id}`,
          {
            username: this.login,
            password: this.password,
            avatar: this.avatar,
            headers: {
              Authorization: adminToken,
            },
          }
        );
        if (response.status === 200) {
          console.log(response.data);
        }
      } catch (error) {
        console.error(error);
      } finally {
        this.toggleEditMode();
      }
    },
    toggleEditMode() {
      this.isEdit = !this.isEdit;
    },
  },
};
</script>

<style lang="scss" scoped>
.user {
  display: flex;
  align-items: center;
  gap: 10px;

  &__button {
    padding: 5px 10px;
    background-color: rgb(240, 185, 11);
    border: 0;
    border-radius: 8px;
    color: black;
    font-size: medium;
    cursor: pointer;
  }

  &__text {
    font-size: large;
    display: flex;
    align-items: center;
  }

  &__input {
    border: 2px dashed black;
    background-color: transparent;
    font-size: large;
    font-weight: normal;
    color: #2c3e50;
    padding: 0 5px;
    margin-left: 5px;
  }
}
</style>