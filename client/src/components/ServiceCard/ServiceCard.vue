<template>
  <div class="service-card">
    <div class="service-card__content">
      <img :alt="title" :src="formatLink" class="service-card__img"/>
      <h4 class="service-card__title">{{ title }}</h4>
      <p class="service-card__subtitle">{{ subtitle }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "ServiceCard",
  props: {
    link: String,
    title: String,
    subtitle: String,
  },
  computed: {
    formatLink() {
      return require(`../../assets/${this.link}.svg`);
    }
  }
}
</script>

<style lang="scss" scoped>
.service-card {
  background-color: #fafafa;
  border-radius: 8px;
  max-width: 378px;
  width: 100%;

  &__content {
    padding: 32px;
    min-height: 264px;
    display: flex;
    flex-direction: column;
  }

  &__img {
    width: 48px;
    height: 48px;
    object-fit: cover;
  }

  &__title {
    font-weight: 600;
    font-size: 24px;
    line-height: 32px;
    color: #1E2329;
    margin-top: 16px;
  }

  &__subtitle {
    font-weight: 400;
    font-size: 16px;
    line-height: 24px;
    margin-top: 8px;
    color: #474D57;
  }
}
</style>