<template>
  <div>
    <canvas ref="chartCanvas" width="800" height="400"></canvas>
  </div>
</template>

<script>
import Chart from "chart.js/auto";

export default {
  data() {
    return {
      socket: null,
      symbol: null,
    };
  },
  created() {
    if (!this.$route.query.symbol) this.$route.symbol = "btc";
    this.symbol = this.$route.query.symbol;
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
};
</script>
