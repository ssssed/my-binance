<template>
  <div class="page not-found">
    <img class="img" alt="404 error" src="@/assets/404-error.png" />
    <div class="info">
      <span class="text">Извините! Страница, которую вы ищите не найдена.</span>
      <router-link class="link" to="/">Вернуться на главную</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style lang="scss" scoped>
.not-found {
  display: flex;
  flex-direction: column;
  gap: 65px;
  max-width: 1280px;
  width: 100%;
  margin: 0 auto;
  align-items: center;
  justify-content: center;
}

.img {
  display: block;
  margin: 0 auto;
  width: 340px;
  height: 203px;
  object-fit: cover;
}

.info {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
}

.text {
  font-size: 24px;
  line-height: 28px;
  color: #1e2026;
  font-weight: 500;
}

.link {
  color: #d0980b;
  font-size: 14px;
  line-height: 20px;
}
</style>