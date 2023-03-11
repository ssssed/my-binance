<template>
  <div class="card">
    <img alt="" class="card__img" :src="link" />
    <h6 class="card__name">{{ name }}</h6>
    <p class="card__apr">
      APR
      <span
        class="card__percent"
        :class="{
          card__percent_up: percentMin >= 0,
        }"
      >
        {{ percentMin }}%-{{ percentMax }}%
      </span>
    </p>
    <button class="card__button" @click="handleNavigate(name)">
      <img alt="next" class="card__next-btn" src="@/assets/accordion.svg" />
    </button>
  </div>
</template>

<script>
export default {
  name: "EarnCard",
  props: {
    name: String,
    link: String,
    percentMin: Number,
    percentMax: Number,
  },
  methods: {
    handleNavigate(name) {
      const queryParams = {
        symbol: name,
      };

      // Объект текущего маршрута
      const currentRoute = this.$router.currentRoute;

      // Новый маршрут с добавленными параметрами
      const newRoute = {
        path: "/currency",
        query: Object.assign({}, currentRoute.query, queryParams),
      };

      this.$router.push(newRoute);
    },
  },
};
</script>

<style lang="scss" scoped>
.card {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  max-width: 220px;
  width: 100%;
  border-radius: 16px;
  background-color: rgb(250, 250, 250);
  border: 2px solid transparent;
  padding: 16px;
  cursor: pointer;

  &:hover {
    background-color: #ffffff;
    transition: all 0.3s linear;
    border-color: rgb(252, 213, 53);

    //  сделать кнопку такого же цвета как и бордер
  }

  &__img {
    width: 40px;
    height: 40px;
  }

  &__name {
    font-weight: 500;
    font-size: 20px;
    line-height: 28px;
    text-transform: uppercase;
  }

  &__percent {
    font-weight: 400;
    font-size: 14px;
    line-height: 20px;
    color: rgb(207, 48, 74);

    &_up {
      color: rgb(3, 166, 109);
    }
  }

  &__apr {
    font-weight: 400;
    font-size: 14px;
    line-height: 20px;
    color: rgb(71, 77, 87);
  }

  &__button {
    width: 32px;
    height: 32px;
    border: 0;
    background-color: rgb(234, 236, 239);
    border-radius: 50%;
  }
}
</style>