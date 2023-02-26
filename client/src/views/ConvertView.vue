<template>
  <div class="page convert">
    <h1>Конвертер криптовалют</h1>
    <div class="form">
      <div class="form__group">
        <label>Исходная валюта</label>
        <select v-model="fromCurrency" @change="getExchangeRate">
          <option v-for="currency in currencies" :value="currency">{{ currency }}</option>
        </select>
      </div>
      <div class="form__group">
        <label>Количество</label>
        <input type="number" v-model="amount" @input="getExchangeRate">
      </div>
      <div class="form__group">
        <label>Целевая валюта</label>
        <select v-model="toCurrency" @change="getExchangeRate">
          <option v-for="currency in currencies" :value="currency">{{ currency }}</option>
        </select>
      </div>
    </div>
    <div class="result">
      <h2>Результат:</h2>
      <p>{{ amount }} {{ fromCurrency }} = {{ convertedAmount }} {{ toCurrency }}</p>
      <p>Курс обмена: 1 {{ fromCurrency }} = {{ exchangeRate }} {{ toCurrency }}</p>
    </div>
  </div>
</template>
<script>
import { axiosService } from "@/api";

export default {
  data() {
    return {
      currencies: ['BTC', 'ETH', 'LTC', 'XRP', 'USDT'], // список поддерживаемых валют
      fromCurrency: 'BTC', // исходная валюта
      toCurrency: 'ETH', // целевая валюта
      amount: 1, // количество исходной валюты
      exchangeRate: null, // курс обмена
      convertedAmount: null // количество целевой валюты
    }
  },
  mounted() {
    this.getExchangeRate()
  },
  methods: {
    async getExchangeRate() {
      // const response = await axiosService.post('/convert', {
      //   from: this.fromCurrency,
      //   to: this.toCurrency
      // });
      // console.log(response);
      // if(response.status === 200) {
      //   this.exchangeRate = response.data;
      //   this.convertedAmount = (this.amount * this.exchangeRate).toFixed(8)
      // }
    }
  }
}
</script>
<style lang="scss" scoped>
.convert {
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
}
.form {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.form-input {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-input input[type="number"] {
  font-size: 20px;
  width: 200px;
  height: 40px;
  padding: 0 10px;
  border: none;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}

.form-input select {
  font-size: 20px;
  width: 150px;
  height: 40px;
  padding: 0 10px;
  border: none;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  margin-left: 20px;
}

.form-input label {
  font-size: 20px;
  font-weight: bold;
  margin-right: 20px;
}

.form-result {
  margin-top: 20px;
  font-size: 24px;
  font-weight: bold;
}
</style>