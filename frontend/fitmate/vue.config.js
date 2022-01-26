module.exports = {
  devServer: {
    proxy: 'http://localhost:9000'
  },
  transpileDependencies: [
    'vuetify'
  ]
}
