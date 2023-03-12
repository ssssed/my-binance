<template>
  <div className="spin" :style="style">
    <div class="spin__container rotate">
      <svg
        class="loading"
        aria-hidden="true"
        focusable="false"
        role="presentation"
        viewBox="0 0 20 20"
      >
        <path
          d="M7.229 1.173a9.25 9.25 0 1 0 11.655 11.412 1.25 1.25 0 1 0-2.4-.698 6.75 6.75 0 1 1-8.506-8.329 1.25 1.25 0 1 0-.75-2.385z"
          fill="#919EAB"
        />
      </svg>
    </div>
  </div>
</template>

<script>
export default {
  name: "Spin",
  props: {
    style: Object,
  },
};
</script>

<style lang="scss" scoped>
.spin {
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: calc((100vh - 70px - 85px) / 4) auto;

  &__container {
    display: block;
    width: 200px;
    height: 200px;
    border-radius: 50%;
  }
}

.rotate {
  animation: rotate 1.5s linear infinite;
}

@keyframes rotate {
  to {
    transform: rotate(360deg);
  }
}
</style>