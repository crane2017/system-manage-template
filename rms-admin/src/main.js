// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App';
import router from './router';
import * as myFilter from '@/script/filter';
import * as constData from '@/script/constants';
// 将自定义常量挂载到全局
Vue.prototype.constData = constData;
// 全局添加自定义过滤器
Object.keys(myFilter).forEach(function (key, index, arr) {
  Vue.filter(key, myFilter[key]);
});
Vue.config.productionTip = false;

Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
