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
        <div class="head">Последняя цена</div>
        <div class="head">Капитализация</div>
      </div>
      <div v-for="currency in currencyList" class="currency-item">
        <div class="currency__header">
          <img :alt="currency.name" :src="currency.img" class="currency__img" />
          <p class="currency__full-name">{{ currency.fullName }}</p>
          <p class="currency__name">{{ currency.name }}</p>
        </div>
        <div class="currency__last-price">₽ {{ currency.lastPrice }}</div>
        <div :class="{
            'currency-percentage_up': currency.percent >= 0
          }" class="currency-percentage">{{ currency.percent }}%
        </div>
        <div class="currency-capitalization">₽ {{ currency.capitalize }}</div>
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
          img: "https://png.pngtree.com/element_our/20190601/ourmid/pngtree-yellow-bitcoin-free-map-image_1369249.jpg",
          fullName: "Bitcoin",
          name: "btc",
          lastPrice: 23250,
          percent: -2.78,
          capitalize: "3 667 548M"
        },
        {
          id: 2,
          img: "https://png.pngtree.com/element_our/20190601/ourmid/pngtree-yellow-bitcoin-free-map-image_1369249.jpg",
          fullName: "Bitcoin",
          name: "btc",
          lastPrice: 23250,
          percent: 0.15,
          capitalize: "3 667 548M"
        },
        {
          id: 3,
          img: "https://png.pngtree.com/element_our/20190601/ourmid/pngtree-yellow-bitcoin-free-map-image_1369249.jpg",
          fullName: "Bitcoin",
          name: "btc",
          lastPrice: 23250,
          percent: -1.01,
          capitalize: "3 667 548M"
        },
        {
          id: 4,
          img: "https://png.pngtree.com/element_our/20190601/ourmid/pngtree-yellow-bitcoin-free-map-image_1369249.jpg",
          fullName: "Bitcoin",
          name: "btc",
          lastPrice: 23250,
          percent: 0.78,
          capitalize: "3 667 548M"
        }
      ]
    };
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

  &__img {
    width: 32px;
    height: 32px;
    align-items: center;
  }

  &__header {
    display: flex;
    gap: 10px;
    align-items: center;
    justify-content: center;
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