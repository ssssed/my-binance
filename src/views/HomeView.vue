<template>
  <main class="home">
    <div class="home__content">
      <popular-currency />
      <binance-earn />
    </div>
  </main>
</template>

<script>
import PopularCurrency from "@/components/PopularCurrency/PopularCurrency.vue";
import BinanceEarn from "@/components/BinanceEarn/BinanceEarn.vue";

export default {
  name: "HomeView",
  components: {
    "popular-currency": PopularCurrency,
    "binance-earn": BinanceEarn
  }
};
</script>

<style lang="scss" scoped>
.home {
  background-color: #ffffff;
  font-family: Arial, serif;

  &__content {
    max-width: 1280px;
    margin: 0 auto;
    padding: 40px 20px;
  }
}
</style>