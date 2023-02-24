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
          link: 'https://coinicons-api.vercel.app/api/icon/usdt',
          percentMin: 0.6,
          percentMax: 115.84
        },
        {
          name: 'btc',
          link: 'https://coinicons-api.vercel.app/api/icon/btc',
          percentMin: 0.74,
          percentMax: 78.54
        },
        {
          name: 'bnb',
          link: 'https://coinicons-api.vercel.app/api/icon/bnb',
          percentMin: 0.76,
          percentMax: 70.20
        },
        {
          name: 'busd',
          link: 'https://coinicons-api.vercel.app/api/icon/busd',
          percentMin: 0.60,
          percentMax: 110.09
        },
        {
          name: 'etherium',
          link: 'https://coinicons-api.vercel.app/api/icon/eth',
          percentMin: 0.97,
          percentMax: 56.52
        },
        {
          name: 'dot',
          link: 'https://coinicons-api.vercel.app/api/icon/dot',
          percentMin: 0.8,
          percentMax: 65.19
        },
        {
          name: 'ada',
          link: 'https://coinicons-api.vercel.app/api/icon/ada',
          percentMin: 1.03,
          percentMax: 84.69
        },
        {
          name: 'shib',
          link: 'https://coinicons-api.vercel.app/api/icon/shib',
          percentMin: 0.36,
          percentMax: 1.73
        },
        {
          name: 'sol',
          link: 'https://coinicons-api.vercel.app/api/icon/sol',
          percentMin: 1.44,
          percentMax: 68.95
        },
        {
          name: 'matic',
          link: 'https://coinicons-api.vercel.app/api/icon/matic',
          percentMin: 2.84,
          percentMax: 103.41
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