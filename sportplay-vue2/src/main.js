import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
//添加全局样式
import './assets/css/global.css'
//引入iconfont.css
import './assets/font/iconfont.css'

//导入axios
import axios from 'axios'
//挂载axios
Vue.prototype.$http = axios
//设置默认访问根路径
axios.defaults.baseURL = 'http://localhost:9000'



Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
