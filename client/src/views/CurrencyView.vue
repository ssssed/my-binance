<template>
  <div class="page currency">
    <canvas ref="chartCanvas" width="800" height="400"></canvas>
    <div v-if="getAuthStatus" class="currency__functions">
      <button class="currency__button currency__sell" @click="handleSell">
        Продать
      </button>
      <button class="currency__button currency__buy" @click="handleBuy">
        Купить
      </button>
      <popup
        :show-popup="isOpenSell"
        :title="'Продажа валюты'"
        :action-button="'Продать'"
        :handle-close="handleClosePopup"
        :wallet-id="getUserInfo.walletId"
        :symbol="symbol"
        :mode="'SELL'"
      />
      <popup
        :show-popup="isOpenBuy"
        :title="'Покупка валюты'"
        :action-button="'Купить'"
        :handle-close="handleClosePopup"
        :wallet-id="getUserInfo.walletId"
        :symbol="symbol"
        :mode="'BUY'"
      />
    </div>
  </div>
</template>

<script>
import Chart from "chart.js/auto";
import { mapGetters } from "vuex";
import Popup from "@/components/Popup/Popup.vue";

export default {
  components: { Popup },
  data() {
    return {
      socket: null,
      symbol: null,
      isOpenSell: false,
      isOpenBuy: false,
    };
  },
  created() {
    this.symbol = this.$route.query.symbol || "btc";
  },
  computed: mapGetters(["getAuthStatus", "getUserInfo"]),
  methods: {
    handleSell() {
      this.isOpenSell = true;
    },
    handleBuy() {
      this.isOpenBuy = true;
      const walletId = this.getUserInfo.walletId;
    },
    handleClosePopup() {
      this.isOpenSell = false;
      this.isOpenBuy = false;
    },
  },
  mounted() {
    const chartCanvas = this.$refs.chartCanvas;
    const ctx = chartCanvas.getContext("2d");

    const chart = new Chart(ctx, {
      type: "line",
      data: {
        labels: [],
        datasets: [
          {
            label: `${this.symbol.toUpperCase()} Price`,
            data: [],
            fill: false,
            borderColor: "rgb(75, 192, 192)",
            tension: 0.1,
          },
        ],
      },
    });

    this.socket = new WebSocket(
      `wss://stream.binance.com:9443/ws/${this.symbol}usdt@trade`
    );

    this.socket.onopen = function () {
      console.log("Соединение установлено");
    };

    this.socket.onmessage = function (event) {
      const trade = JSON.parse(event.data);
      const time = new Date(trade.T).toLocaleTimeString();
      const price = parseFloat(trade.p);
      const dates = chart.data.labels;
      const values = chart.data.datasets[0].data;
      const lastTime = dates.at(-1);
      if (lastTime === time) return;
      if (dates.length >= 50) dates.unshift();
      if (values.length >= 50) values.unshift();
      dates.push(time);
      values.push(price);
      chart.update();
    };

    this.socket.onclose = function (event) {
      if (event.wasClean) {
        console.log(
          `Соединение закрыто, код=${event.code} причина=${event.reason}`
        );
      } else {
        console.log("Соединение прервано");
      }
    };

    this.socket.onerror = function (error) {
      console.log(`Ошибка ${error.message}`);
    };
  },
  unmounted() {
    this.socket.close();
  },
};
</script>
<style lang="scss" scoped>
.currency {
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
  padding: 40px 15px;

  &__functions {
    padding: 30px 0 0;
    display: flex;
    justify-content: center;
    gap: 20px;
  }

  &__button {
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
    max-width: 200px;
    width: 100%;
    padding: 12px 24px;
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
  }
}
</style>
