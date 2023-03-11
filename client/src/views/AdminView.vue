<template>
  <div class="page admin">
    <admin-form v-if="!getAdminAuthStatus" />
    <div v-if="getAdminAuthStatus" class="content">
      <h2 class="content__title">Админ панель</h2>
      <div class="container">
        <div class="users">
          <h2 class="users__title">Пользователи</h2>
          <div class="users__container">
            <user-info
              v-for="(user, index) in users"
              :key="index"
              :user="user"
              @updateUsers="handleUpdateUsers"
            />
          </div>
        </div>
        <!-- количество новых пользователей -->
        <div class="transactions"></div>
        <!-- количество проведенных транзакций -->
        <div class="transactions"></div>
        <!-- прибиль от транзакций -->
      </div>
      <div class="history">
        <h2 class="history__title">История транзакций</h2>
        <div class="transactions"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { axiosService } from "@/api";
import { mapGetters } from "vuex";
import UserInfo from "@/components/UserInfo/UserInfo.vue";
import AdminForm from "@/components/AdminForm/AdminForm.vue";
export default {
  components: { UserInfo, AdminForm },
  name: "AdminView",
  data() {
    return {
      users: [],
    };
  },
  methods: {
    async getAdminData() {
      const adminToken = sessionStorage.getItem("adminToken");
      try {
        const [userResponse, transactionsResponse] = await Promise.all([
          axiosService.get("/admin/users", {
            headers: {
              Authorization: adminToken,
            },
          }),
          axiosService.get("/admin/transactions", {
            headers: {
              Authorization: adminToken,
            },
          }),
        ]);
        if (
          userResponse.status === 200 &&
          transactionsResponse.status === 200
        ) {
          console.log(userResponse.data, transactionsResponse.data);
          this.users = userResponse.data;
        }
      } catch (error) {
        console.error(error);
      }
    },
    handleUpdateUsers(users) {
      this.users = users;
    },
  },
  computed: mapGetters(["getAdminAuthStatus"]),
  watch: {
    async getAdminAuthStatus(updatedValue, oldValue) {
      if (updatedValue) {
        this.getAdminData();
      }
    },
    "$sessionStorage.adminToken": function (value) {
      if (value) this.getAdminData();
    },
  },
  mounted() {
    if (this.getAdminAuthStatus || sessionStorage.getItem("adminToken")) {
      this.getAdminData();
    }
  },
};
</script>

<style lang="scss" scoped>
.admin {
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
}

.users {
  &__title {
    font-size: xx-large;
  }

  &__container {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
}
</style>