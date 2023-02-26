<template>
  <div class="page about">
    <div class="about__header">
      <div class="about__header-content">
        <div class="about__text-container" style="max-width: 575px">
          <h1 class="about__title">Добро пожаловать на My Binance</h1>
          <p class="about__subtitle">Каждый имеет право зарабатывать, хранить, тратить и отправлять свои средства,
            независимо от
            того, кто вы и откуда. <strong>P.S.</strong> ( Эта работа была выполнена в качестве проектной работы и не
            несет в себе ничего
            коммерческого )</p>
        </div>
        <img alt="монетки вокруг мира" class="about__img" src="@/assets/about.png"/>
      </div>
    </div>
    <div class="about__container">
      <div class="about__text-container">
        <h2 class="about__title">Наша экосистема</h2>
        <p class="about__subtitle">Нашей платформе доверяют миллионы пользователей по всему миру, и мы предлагаем
          уникальный набор финансовых продуктов.</p>
      </div>
      <div class="eco-systems">
        <service-card
            v-for="service in services"
            :key="service.id"
            :link="service.link"
            :subtitle="service.subtitle"
            :title="service.title"
        />
      </div>
    </div>
  </div>
</template>
<script>
import ServiceCard from "@/components/ServiceCard/ServiceCard.vue";

export default {
  name: 'AboutView',
  components: {
    "service-card": ServiceCard,
  },
  data() {
    return {
      services: [
        {
          id: 1,
          link: 'search',
          title: 'My Binance предоставляет только актуальную информацию',
          subtitle: 'Биржа My Binance является крупнейшей криптовалютной биржей по объему торгов.'
        },
        {
          id: 2,
          link: 'security',
          title: 'Защищенный кошелек',
          subtitle: 'Безопасный децентрализованный кошелек, в котором пользователи могут хранить, отправлять и получать свои цифровые активы.'
        },
        {
          id: 3,
          link: 'launch',
          title: 'Предоставляем api для платежей по нашей защищенной системе',
          subtitle: 'Разрешаем различные интеграции с нашим api, проведение различных платежей в любой из доступных вам криптовалют'
        }
      ],
    }
  }
}
</script>
<style lang="scss" scoped>
.about {
  width: 100%;
  padding: 48px 0;

  &__container {
    max-width: 1280px;
    padding: 20px 10px;
    width: 100%;
    margin: 0 auto;
  }

  &__header {
    display: flex;
    align-items: center;
    height: 400px;
    background-color: #FAFAFA;
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    background-image: url("@/assets/about-bg-descktop.png");

    &-content {
      max-width: 1280px;
      width: 100%;
      margin: 0 auto;
      display: flex;
      justify-content: space-between;
    }
  }

  &__text-container {
    display: flex;
    flex-direction: column;
    gap: 16px;
    width: 100%;
  }

  &__title {
    font-weight: 600;
    font-size: 48px;
    line-height: 56px;
    color: #1E2329;
  }

  &__subtitle {
    font-weight: 400;
    font-size: 20px;
    line-height: 28px;
    color: #474D57;
  }

  &__img {
    width: 485px;
  }
}
.eco-systems {
  padding-top: 40px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
}
</style>