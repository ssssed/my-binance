<template>
  <div class="page profile">
    <div class="content">
      <div class="profile__data">
        <img class="profile__img" :src="profileImage" alt="avatar" />
        <h2 class="profile__name">{{ getUserInfo.username }}</h2>
        <button class="profile__button profile__logout" @click="handleLogout">
          Выйти
        </button>
      </div>
      <spin v-if="isLoading" />
      <div v-if="!isLoading" class="profile__wallets">
        <wallet-card
          v-for="wallet in wallets"
          :key="wallet.currencyId"
          :wallet="wallet"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { axiosService } from "@/api";
import { mapActions, mapGetters } from "vuex";
import WalletCard from "@/components/WalletCard/WalletCard.vue";
import Spin from "@/components/Spin/Spin.vue";
export default {
  components: { WalletCard, Spin },
  name: "profile",
  data() {
    return {
      isLoading: true,
      wallets: [],
    };
  },
  computed: {
    ...mapGetters(["getUserInfo"]),
    profileImage() {
      console.log(this.getUserInfo.avatar, typeof this.getUserInfo.avatar);
      if (this.getUserInfo.avatar === "null" || this.getUserInfo.avatar === null)
        return require("../assets/default-profile-img.png");
      return this.getUserInfo.avatar;
    },
  },
  async created() {
    try {
      const response = await axiosService.get(
        `/user/wallets/${this.getUserInfo.walletId}`
      );
      if (response.status === 200) {
        this.wallets = response.data;
        this.isLoading = false;
      }
    } catch (error) {
      console.error(error);
    }
  },
  methods: {
    ...mapActions(["changeAuthStatus", "changeUserInfo"]),
    handleLogout() {
      this.changeAuthStatus(false);
      this.changeUserInfo({});
      localStorage.removeItem("userInfo");
      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style lang="scss">
.profile {
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;

  &__data {
    display: flex;
    flex-direction: column;
    gap: 10px;
    padding: 20px;
  }

  &__name {
    text-align: center;
  }

  &__img {
    width: 180px;
    height: 180px;
    object-fit: cover;
    border-radius: 50%;
    display: block;
    margin: 0 auto;
  }

  &__button {
    margin: 0;
    min-width: 0;
    box-sizing: border-box;
    margin: 0;
    margin-top: 0px;
    min-width: 0;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    cursor: pointer;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: -webkit-inline-box;
    display: -webkit-inline-flex;
    display: -ms-inline-flexbox;
    display: inline-flex;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    box-sizing: border-box;
    font-size: 16px;
    font-family: inherit;
    font-weight: 500;
    text-align: center;
    -webkit-text-decoration: none;
    text-decoration: none;
    outline: none;
    padding-left: 24px;
    padding-right: 24px;
    padding-top: 12px;
    padding-bottom: 12px;
    line-height: 24px;
    min-width: 80px;
    word-break: keep-all;
    color: #181a20;
    border-radius: 4px;
    min-height: 24px;
    border: none;
    background-image: none;
    background-color: #fcd535;
    height: 48px;
    min-width: 100%;
    padding-left: 24px;
    padding-right: 24px;
  }

  &__wallets {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(auto-fit, 220px);
    gap: 15px;
  }
}

.content {
  display: flex;
  gap: 20px;
}
</style>