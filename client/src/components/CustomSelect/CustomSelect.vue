<template>
  <div class="custom-select">
    <div class="selected-option" @click="toggleOptions">
      <span class="custom-select__option" :class="{
        'custom-select__option_open': isOpen
      }"><img class="custom-select__img" :alt="selectedOption" :src="`https://coinicons-api.vercel.app/api/icon/${selectedOption.toLowerCase()}`" />{{ selectedOption }}
      </span>
      <i :class="`fas fa-chevron-${isOpen ? 'up' : 'down'}`"></i>
    </div>
    <transition name="fade">
      <div class="options" v-if="isOpen">
        <div
            class="option"
            v-for="(option, index) in filteredOptions"
            :key="index"
            @click="selectOption(index)"
        >
          <span class="option__content">
            <img class="custom-select__img" :alt="selectedOption" :src="`https://coinicons-api.vercel.app/api/icon/${option.toLowerCase()}`" />
            {{ option }}
          </span>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  props: {
    options: {
      type: Array,
      required: true,
    },
    selected: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isOpen: false,
    };
  },
  computed: {
    selectedOption() {
      return this.selected || this.options[0];
    },
    filteredOptions() {
      return this.options.filter((el) => el !== this.selected)
    }
  },
  methods: {
    toggleOptions() {
      this.isOpen = !this.isOpen;
    },
    selectOption(index) {
      this.$emit("update-selected", this.options[index]);
      this.isOpen = false;
    },
  },
};
</script>

<style lang="scss">
.custom-select {
  position: relative;
  max-width: 120px;
  cursor: pointer;
  user-select: none;

  &__img {
    width: 24px;
    height: 24px;
  }

  &__option {
    display: flex;
    max-width: 120px;
    width: 120px;
    align-items: center;
    border: 1px solid #ccc;
    gap: 10px;
    border-radius: 18px;
    padding: 12px 15px;
    background-color: white;
    user-select: none;

    &_open {
      border-bottom-left-radius: 0;
      border-bottom-right-radius: 0;
      border-bottom: 0;
    }
  }
}

.selected-option {
  display: flex;
  align-items: center;
  max-width: 120px;
  width: 120px;
  cursor: pointer;
}

.selected-option i {
  font-size: 12px;
  margin-left: 10px;
}

.options {
  position: absolute;
  z-index: 1;
  top: 100%;
  left: 0;
  width: calc(100% - 10px);
  max-height: 150px;
  overflow-y: auto;
  border: 1px solid #ccc;
  border-top: none;
  border-radius: 0 0 5px 5px;
  background-color: #fff;
}

.option {
  cursor: pointer;
  user-select: none;

  &__content {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 5px 15px;
  }
}

.option:hover {
  background-color: #f2f2f2;
}
</style>
