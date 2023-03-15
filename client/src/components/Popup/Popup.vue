<template>
  <div class="popup-container" :class="{ show: showPopup }">
    <div class="popup">
      <div class="popup-header">
        <h3>{{ title }}</h3>
        <img src="@/assets/close.svg" class="close" @click="handleClose" />
      </div>
      <form @submit.prevent="handleSubmitForm" class="popup-body">
        <input v-model.number="message" type="text" class="input" />
        <button
          type="submit"
          class="button"
          :class="{
            button_disable: !parseFloat(message.toString()),
          }"
        >
          {{ actionButton }}
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { axiosService } from "@/api";
export default {
  data() {
    return {
      message: 0,
    };
  },
  props: {
    showPopup: Boolean,
    title: String,
    actionButton: String,
    handleClose: Function,
    walletId: Number,
    symbol: String,
    mode: String,
  },
  methods: {
    async handleSubmitForm() {
      try {
        console.log(this.walletId, this.symbol, this.message);
        const response = await axiosService.patch("/user/trade", {
          walletId: this.walletId,
          symbol: this.symbol,
          value: this.mode === "SELL" ? this.message * -1 : this.message,
        });
        if (response.status === 200) {
          console.log(response);
        }
      } catch (error) {
        console.error(error);
      } finally {
        this.handleClose();
        this.message = 0;
      }
    },
  },
};
</script>

<style scoped>
.popup-container {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9999;
  transition: all 0.5s ease;
}

.popup-container.show {
  display: flex;
  justify-content: center;
  align-items: center;
}

.popup {
  width: 400px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  padding: 20px;
  animation: popup-animation 0.5s ease;
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.popup-header h3 {
  margin: 0;
}

.popup-body {
  display: flex;
  flex-direction: column;
  margin: 20px auto 0;
  gap: 15px;
}

.button {
  display: block;
  margin: 0 auto;
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

.button_disable {
  background-color: lightgray;
}

.close {
  width: 20px;
  height: 20px;
  cursor: pointer;
}

.input {
  color: rgb(30, 35, 41);
  font-size: 14px;
  padding-left: 12px;
  padding-right: 12px;
  outline: none;
  background-color: inherit;
  opacity: 1;
  width: 100%;
  line-height: 1.6;
  height: 48px;
  border: 1px solid rgb(234, 236, 239);
  border-radius: 4px;
}

@keyframes popup-animation {
  from {
    transform: scale(0.8);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}
</style>
