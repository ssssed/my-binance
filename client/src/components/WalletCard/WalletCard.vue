<template>
  <div class="wallet">
    <div class="wallet__content">
      <div class="wallet__info">
        <img
          :src="`https://coinicons-api.vercel.app/api/icon/${wallet.symbol}`"
          :alt="wallet.name"
          class="wallet__logo"
        />
        <h5 class="wallet__symbol">{{ wallet.symbol }}</h5>
      </div>
      <p class="wallet__currency">Количество: {{ wallet.value }}</p>
      <button class="wallet__button" @click="handleSellWallet">продать</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "WalletCard",
  props: {
    wallet: Object,
  },
  methods: {
    handleSellWallet() {
      const queryParams = {
        symbol: this.wallet.symbol,
      };

      const currentRoute = this.$router.currentRoute;

      const newRoute = {
        path: "/currency",
        query: Object.assign({}, currentRoute.query, queryParams),
      };

      this.$router.push(newRoute);
    },
  },
};
</script>

<style lang="scss" scoped>
.wallet {
  background-color: #fafafa;
  border-radius: 16px;

  &__content {
    display: flex;
    flex-direction: column;
    gap: 16px;
    padding: 15px;
  }

  &__symbol {
    font-size: 18px;
    text-transform: uppercase;
  }

  &__info {
    display: flex;
    gap: 8px;
    align-items: center;
  }

  &__logo {
    width: 32px;
    height: 32px;
    object-fit: cover;
  }

  &__button {
    cursor: pointer;
    text-transform: uppercase;
    font-size: 14px;
    font-weight: 700;
    text-align: center;
    -webkit-text-decoration: none;
    text-decoration: none;
    outline: none;
    padding: 12px 18px;
    line-height: 18px;
    min-width: 80px;
    max-width: 120px;
    width: 100%;
    word-break: keep-all;
    color: #181a20;
    border-radius: 4px;
    min-height: 24px;
    border: none;
    background-image: none;
    background-color: #fcd535;
    min-width: 100%;
    padding-left: 24px;
    padding-right: 24px;
  }
}
</style>