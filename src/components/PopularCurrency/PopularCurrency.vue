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
            <img :alt="currency.name" :src="currency.img" class="currency__img" />
            <p class="currency__full-name">{{ currency.fullName }}</p>
          </div>
          <p class="currency__name">{{ currency.name }}</p>
        </div>
        <div class="currency__last-price">$ {{ currency.lastPrice }}</div>
        <div :class="{
            'currency-percentage_up': currency.percent >= 0
          }" class="currency-percentage">{{ currency.percent }}%
        </div>
        <div class="currency-capitalization">$ {{ currency.capitalize }}</div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "PopularCurrency",
  data() {
    return {
      currencyList: [
        {
          id: 1,
          img: "https://coinicons-api.vercel.app/api/icon/btc",
          fullName: "Bitcoin",
          name: "btc",
          lastPrice: 0,
          percent: 0,
          capitalize: ""
        },
        {
          id: 2,
          img: "https://coinicons-api.vercel.app/api/icon/bnb",
          fullName: "BNB",
          name: "bnb",
          lastPrice: 0,
          percent: 0,
          capitalize: ""
        },
        {
          id: 3,
          img: "https://coinicons-api.vercel.app/api/icon/busd",
          fullName: "BUSD",
          name: "busd",
          lastPrice: 0,
          percent: 0,
          capitalize: ""
        },
        {
          id: 4,
          img: "https://coinicons-api.vercel.app/api/icon/eth",
          fullName: "Ethereum",
          name: "eth",
          lastPrice: 0,
          percent: 0,
          capitalize: ""
        }
      ],
      sockets: []
    };
  },
  created() {
    this.currencyList.forEach(async (currency) => {
      const socket = new WebSocket(
        `wss://stream.binance.com:9443/ws/${currency.name.toLowerCase()}usdt@ticker`
      );

      this.sockets.push(socket);

      socket.addEventListener("message", async function(event) {
        const data = JSON.parse(event.data);
        if (data.e === "24hrTicker") {
          currency.percent = parseFloat(data.P).toPrecision(2);
          currency.lastPrice = parseFloat(data.c).toFixed(0);
          currency.capitalize = parseFloat(data.w * data.L / 1.405).toString().slice(0, 6) + "M";
        }
      });
    });
  },
  unmounted() {
    this.sockets.forEach((socket) => {
      socket.close();
    })
  }
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
    max-width: 200px;
    justify-content: space-between;
    gap: 10px;
    align-items: center;
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