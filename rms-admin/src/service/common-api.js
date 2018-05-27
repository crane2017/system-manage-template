'use strict';
import axios from '../script/http';
import * as apiUrl from '../script/api_url';

const getUserMenu = () => {
  return axios.get(apiUrl.ADMIN_MENU_URL, {
    params: {}
  });
};
const getStoreWarehouse = () => {
  return axios.get(apiUrl.STORE_WAREHOUSE_URL, {
    params: {}
  });
};

export {
  // getCodeMaster,
  getStoreWarehouse,
  getUserMenu
};
