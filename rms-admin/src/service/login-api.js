'use strict';
import axios from '../script/http';
import * as apiUrl from '../script/api_url';

const login = (params) => {
  return axios.post(apiUrl.LOGIN_URL, params);
};

const loginOut = (username, password) => {
  return axios.get(apiUrl.LOGIN_URL, {
    params: {
      username: username,
      password: password
    }
  });
};

export {
  login, loginOut
};
