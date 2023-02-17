<template>
  <div class="earn">
    <div class="earn__header">
      <h2 class="earn__title">Binance Earn</h2>
      <p class="earn__subtitle">Просто и безопасно. Найдите популярные криптовалюты и начните зарабатывать.</p>
    </div>
    <div class="cards">
      <earn-card
        v-for="card in cards"
        :name="card.name"
        :link="card.link"
        :percent-min="card.percentMin"
        :percent-max="card.percentMax"
      />
    </div>
  </div>
</template>

<script>
import EarnCard from "@/components/EarnCard/EarnCard.vue";

export default {
  name: "BinanceEarn",
  components: {
    'earn-card': EarnCard,
  },
  data() {
    return {
      cards: [
        {
          name: 'usdt',
          link: 'https://w7.pngwing.com/pngs/581/504/png-transparent-tether-usdt-cryptocoins-icon.png',
          percentMin: 0.6,
          percentMax: 120.8
        },
        {
          name: 'btc',
          link: 'https://w7.pngwing.com/pngs/581/504/png-transparent-tether-usdt-cryptocoins-icon.png',
          percentMin: -1.8,
          percentMax: 73.9
        },
        {
          name: 'etherium',
          link: 'https://w7.pngwing.com/pngs/581/504/png-transparent-tether-usdt-cryptocoins-icon.png',
          percentMin: 1.2,
          percentMax: 80.3
        },
        {
          name: 'doge',
          link: 'https://w7.pngwing.com/pngs/581/504/png-transparent-tether-usdt-cryptocoins-icon.png',
          percentMin: 0.8,
          percentMax: 200.65
        },
        {
          name: 'aboba',
          link: 'https://w7.pngwing.com/pngs/581/504/png-transparent-tether-usdt-cryptocoins-icon.png',
          percentMin: -2.6,
          percentMax: 1
        },
        {
          name: 'aboba2',
          link: 'https://w7.pngwing.com/pngs/581/504/png-transparent-tether-usdt-cryptocoins-icon.png',
          percentMin: -8.2,
          percentMax: 250
        },
      ]
    }
  }
};
</script>

<style lang="scss" scoped>

  .earn {
    margin-top: 80px;

    &__title {
      font-weight: 600;
      font-size: 40px;
      line-height: 48px;
    }

    &__subtitle {
      font-weight: 400;
      font-size: 20px;
      line-height: 28px;
      color: #474D57;
    }

  }

  .cards {
    display: grid;
    gap: 32px;
    grid-template-columns: repeat(5, minmax(220px, 1fr));
    margin-top: 64px;
    padding-bottom: 80px;
    background-color: #ffffff;
  }
</style>