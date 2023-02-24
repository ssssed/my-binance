<template>
  <section class="popular-currency">
    <div class="title-container">
      <h2 class="title">Популярные криптовалюты</h2>
      <router-link class="link" to="/markets">
        <div class="button">
          <span class="text">Посмотреть больше рынков</span>
          <img alt="accordion" class="accordion-img" src="@/assets/accordion.svg" />
        </div>
      </router-link>
    </div>
    <div class="currency-container">
      <div class="headers">
        <div class="head">Имя</div>
        <div class="head">Последняя цена</div>
        <div class="head">Изменение за 24ч</div>
        <div class="head">Капитализация</div>
      </div>
      <div v-for="currency in currencyList" class="currency-item">
        <div class="currency__header">
          <div class="currency__logo">
            <img :alt="currency.name" :src="`https://coinicons-api.vercel.app/api/icon/${currency.symbol.toLowerCase()}`" class="currency__img" />
          </div>
          <p class="currency__name">{{ currency.name }}</p>
        </div>
        <div class="currency__last-price">$ {{ currency.price }}</div>
        <div :class="{
            'currency-percentage_up': currency.change >= 0
          }" class="currency-percentage">{{ currency.change }}%
        </div>
        <div class="currency-capitalization">$ {{ currency.marketCap }}</div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "PopularCurrency",
  data() {
    return {
      currencyList: [],
    };
  },
  created() {
    const ws = new WebSocket('wss://stream.binance.com:9443/ws/!ticker@arr')

    ws.onmessage = event => {
      const data = JSON.parse(event.data)
      const topCurrencies = ['BTCUSDT', 'ETHUSDT', 'BNBUSDT', 'BUSDUSDT', 'XRPUSDT']
      const filteredData = data.filter(currency => topCurrencies.includes(currency.s))

      this.currencyList = filteredData.map(currency => {
        return {
          name: currency.s.replace('USDT', ''),
          price: parseFloat(currency.c).toFixed(2),
          change: parseFloat(currency.P).toFixed(2),
          marketCap: parseFloat(currency.q).toLocaleString(),
          symbol: currency.s.slice(0, -4)
        }
      })
    }
  },
};
</script>

<style lang="scss" scoped>

.popular-currency {
  margin-bottom: 80px;
}

.title-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  text-transform: uppercase;
  font-weight: 600;
  font-size: 40px;
  line-height: 48px;
}

.link {
  text-decoration: none;
  color: #939aa6;
}

.text {
  font-weight: 500;
  font-size: 14px;
  line-height: 20px;
}

.button {
  text-decoration: none;
  display: flex;
  gap: 2px;
  align-items: center;
}

.accordion-img {
  width: 24px;
  height: 24px;
}

.currency-container {
  padding-top: 64px;
}


.headers, .currency-item {
  display: grid;
  grid-template-columns: repeat(4, minmax(120px, 1fr));
  align-items: center;
  margin-top: 10px;
  padding: 16px;
}

.currency {

  &-percentage, &-capitalization, &__last-price {
    text-align: center;
  }

  &__logo {
    display: flex;
    gap: 10px;
    align-items: center;
  }

  &__name {
    text-transform: uppercase;
  }

  &__img {
    width: 32px;
    height: 32px;
    align-items: center;
  }

  &__header {
    display: flex;
    max-width: 302px;
    justify-content: start;
    gap: 10px;
    align-items: center;
    padding-left: 40px;
  }

  &-percentage {
    color: rgb(207, 48, 74);

    &_up {
      color: rgb(3, 166, 109);
    }
  }

  &-capitalization {
    font-weight: 400;
    font-size: 16px;
    line-height: 24px;
  }
}

.head {
  color: #707A8A;
  text-align: center;
}
</style>