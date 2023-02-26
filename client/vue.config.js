const { defineConfig } = require("@vue/cli-service");
const Dotenv = require('dotenv-webpack');

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  configureWebpack: {
    plugins: [
      new Dotenv()
    ]
  }
});
