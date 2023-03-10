<template>
  <div class="page market">
    <h1>Список всех криптовалют:</h1>
    <table class="market__table">
      <thead>
        <tr class="market__head">
          <th>Название</th>
          <th>Цена</th>
          <th>Изменение за 24 часа</th>
          <th>Капитализация</th>
        </tr>
      </thead>
      <spin v-if="isLoading" />
      <tbody v-else class="market__body">
        <tr
          v-for="(cryptocurrency, index) in displayedCryptocurrencies"
          :key="index"
          class="currency"
        >
          <td>
            {{ cryptocurrency.name.replace("USDT", "") }}
          </td>
          <td>{{ cryptocurrency.price }} $</td>
          <td
            :class="{
              green: cryptocurrency.change > 0,
              red: cryptocurrency.change < 0,
            }"
          >
            {{ cryptocurrency.change }}%
          </td>
          <td>{{ cryptocurrency.marketCap }} $</td>
        </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button
        :class="{
          pagination__button_disable: currentPage === 0,
        }"
        :disabled="currentPage === 0"
        class="pagination__button"
        @click="prevPage"
      >
        Предыдущая
      </button>
      <button
        :class="{
          pagination__button_disable: currentPage === totalPages - 1,
        }"
        :disabled="currentPage === totalPages - 1"
        class="pagination__button"
        @click="nextPage"
      >
        Следующая
      </button>
    </div>
  </div>
</template>
<script>
import Spin from "@/components/Spin/Spin.vue";
export default {
  components: { Spin },
  data() {
    return {
      isLoading: true,
      cryptocurrencies: [],
      currentPage: 0,
      pageSize: 40,
      firstRender: false,
    };
  },
  mounted() {
    const wsUrl = "wss://stream.binance.com:9443/ws/!ticker@arr";
    const websocket = new WebSocket(wsUrl);

    websocket.onmessage = (event) => {
      const tickers = JSON.parse(event.data);
      if (!this.firstRender) {
        this.cryptocurrencies = tickers.map((ticker) => ({
          name: ticker.s,
          price: parseFloat(ticker.c).toFixed(2),
          change: parseFloat(ticker.P).toFixed(2),
          marketCap: parseFloat(ticker.q).toFixed(2),
        }));
        this.cryptocurrencies = this.cryptocurrencies.filter(
          (el) => el.name.slice(el.name.length - 4) === "USDT"
        );
        this.firstRender = true;
        this.isLoading = false;
      }
      tickers.forEach((ticker) => {
        const findTicker = this.cryptocurrencies.find(
          (el) => el.name === ticker.s
        );
        if (findTicker) {
          const updateTicker = {
            name: ticker.s,
            price: parseFloat(ticker.c).toFixed(2),
            change: parseFloat(ticker.P).toFixed(2),
            marketCap: parseFloat(ticker.q).toFixed(2),
          };
          for (const key in findTicker) findTicker[key] = updateTicker[key];
        }
      });
      this.sortByMarketCap();
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.cryptocurrencies.length / this.pageSize);
    },
    displayedCryptocurrencies() {
      const start = this.currentPage * this.pageSize;
      const end = start + this.pageSize;
      return this.cryptocurrencies.slice(start, end);
    },
  },
  methods: {
    sortByMarketCap() {
      this.cryptocurrencies.sort((a, b) => {
        return b.marketCap - a.marketCap;
      });
    },
    nextPage() {
      this.currentPage += 1;
    },
    prevPage() {
      this.currentPage -= 1;
    },
  },
};
</script>
<style lang="scss" scoped>
.green {
  color: green;
}

.red {
  color: red;
}

.pagination {
  width: 100%;
  margin: 10px 0 0;
  display: flex;
  gap: 20px;

  &__button {
    background-color: transparent;
    color: #c99400;
    font-size: 16px;
    line-height: 24px;
    cursor: pointer;
    border: 0;

    &_disable {
      color: #b9c0c9;
    }
  }
}

.market {
  max-width: 1280px;
  margin: 0 auto;
  padding: 40px 60px;

  &__table {
    display: flex;
    flex-direction: column;
    padding: 30px 0 0;
  }

  &__head {
    max-width: 1280px;
    width: 100%;
    display: flex;
    justify-content: space-between;
    padding-bottom: 15px;

    th {
      width: 100%;
      text-align: start;
    }
  }

  .currency {
    max-width: 1280px;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    td {
      width: 100%;
      text-align: start;
    }
  }
}
</style>