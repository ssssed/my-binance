<template>
  <footer class="footer">
    <p class="footer__text">My Binance © {{ year }}</p>
  </footer>
</template>

<script>
export default {
  name: "Footer",
  data() {
    return {
      year: new Date().getFullYear(),
    }
  }
};
</script>

<style lang="scss" scoped>
.footer {
  position: absolute;
  bottom: 0;
  padding: 32px 0;
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
  border-top: 1px solid #EAECEF;

  &__text {
    font-size: 14px;
    color: #707A8A;
    line-height: 20px;
    text-align: center;
  }
}
</style>