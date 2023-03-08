<template>
    <div class="page convert">
        <h1>Конвертер криптовалют</h1>
        <div class="form">
            <div class="form__group">
                <label>Исходная валюта</label>
                <custom-select v-model="fromCurrency" :selected="fromCurrency" :options="currencies"
                               @update-selected="selectFromCurrency"/>
            </div>
            <div class="form__group">
                <label>Количество</label>
                <input placeholder="Кол-во монет" type="number" v-model="amount" @input="getExchangeRate">
            </div>
            <div class="form__group">
                <label>Целевая валюта</label>
                <custom-select v-model="toCurrency" :selected="toCurrency" :options="fromCurrencies"
                               :from-currency="fromCurrency"
                               @update-selected="selectToCurrency"/>
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
import {axiosService} from "@/api";
import CustomSelect from "@/components/CustomSelect/CustomSelect.vue";

export default {
    components: {CustomSelect},
    data() {
        return {
            currencies: ['BTC', 'ETH', 'LTC', 'XRP'], // список поддерживаемых валют
            fromCurrencies: ['USDT', 'BTC', 'ETH', 'LTC', 'XRP'],
            fromCurrency: 'BTC', // исходная вал3юта
            toCurrency: 'USDT', // целевая валюта
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
            const response = await axiosService.post('/convert', {
                from: this.fromCurrency,
                to: this.toCurrency
            });
            if (response.status === 200) {
                this.exchangeRate = response.data.price;
                this.convertedAmount = (this.amount * this.exchangeRate).toFixed(8)
            }

        },
        selectFromCurrency(currency) {
            this.fromCurrency = currency;
        },
        selectToCurrency(currency) {
            this.toCurrency = currency;
        }
    }
}
</script>
<style lang="scss" scoped>
.convert {
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
  padding-top: 40px;
}

.form {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  max-width: 400px;
  margin: 60px auto 0;
}

.form__group {
  display: flex;
  align-items: center;
  padding: 10px 20px;
  max-width: 384px;
  width: 100%;
  justify-content: space-between;


  & input[type=number] {
    -moz-appearance: textfield; /* убрать стрелки в Firefox */
    appearance: textfield; /* убрать стрелки в других браузерах */
    border: 1px solid #ccc; /* убрать рамку */
    outline: none; /* убрать контур вокруг элемента */
    background-color: white; /* убрать фоновый цвет */
    text-align: center; /* выравнивание текста по центру */
    max-width: 140px;
    border-radius: 18px;
    padding: 12px 20px;
    width: 100%; /* установить ширину элемента на 100% */
  }
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

.result {
  padding-top: 25px;
  max-width: 400px;
  margin: 0 auto;

  & h2, p {
    text-align: center;
  }
}
</style>