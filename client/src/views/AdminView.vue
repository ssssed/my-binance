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
        <div class="transaction-count">
          Количество проведенных транзакций за все время:
          {{ transactionsCount }}
        </div>
      </div>
      <div class="history">
        <h2 class="history__title">История транзакций</h2>
        <div class="transactions">
          <div
            v-for="transaction in transactions"
            :key="transaction.id"
            class="transaction"
          >
            <span class="transaction__type">{{ transaction.type }}</span>
            <div class="transaction"></div>
            <span
              :style="{
                color: transaction.price > 0 ? 'green' : 'red',
              }"
              >{{ transaction.price }}
              <span class="transaction__symbol">{{
                transaction.symbol
              }}</span></span
            >
          </div>
        </div>
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
      transactions: [],
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
          this.transactions = transactionsResponse.data;
        }
      } catch (error) {
        console.error(error);
      }
    },
    handleUpdateUsers(users) {
      this.users = users;
    },
  },
  computed: {
    ...mapGetters(["getAdminAuthStatus"]),
    transactionsCount() {
      return this.transactions.length;
    },
    transactionsProfit() {
      return this.transactions.reduce((acc, item) => acc + item.price, 0);
    },
  },
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

.transactions {
  padding-top: 20px;
  display: grid;
  grid-template-columns: repeat(auto-fit, 300px);
  gap: 10px;
  margin: 0 auto;
}

.transaction {
  display: flex;
  gap: 5px;

  &-count {
    padding: 10px 0;
  }

  &__symbol {
    color: black;
  }
}
</style>