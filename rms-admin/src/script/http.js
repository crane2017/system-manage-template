import axios from 'axios';
import router from '../router/index';

// axios 配置
axios.defaults.timeout = 30000;
// axios.defaults.baseURL = document.URL;

// http request 拦截器
axios.interceptors.request.use(
  config => {
    let token = sessionStorage.getItem('token');
    if (token) {
      config.headers.Authorization = token;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  });

// http response 拦截器
axios.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 401 清除登录信息并跳转到登录页面
          sessionStorage.clear();
          router.replace({
            path: 'login',
            query: {redirect: router.currentRoute.fullPath}
          });
        case 403:
          // 403 清除登录信息并跳转到登录页面
          sessionStorage.clear();
      }
    }
    // console.log(JSON.stringify(error));//console : Error: Request failed with status code 402
    return Promise.reject(error);
  });

export default axios;
