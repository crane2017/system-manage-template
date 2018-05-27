'use strict';
import axios from '../../script/http';
import * as apiUrl from '../../script/api_url';

const getUser = (params) => {
  return axios.get(apiUrl.ADMIN_USER_URL, {
    params: params
  });
};

const updateUser = (params) => {
  return axios.put(apiUrl.ADMIN_USER_URL, params);
};

const insertUser = (params) => {
  return axios.post(apiUrl.ADMIN_USER_URL, params);
};

const deleteUser = (params) => {
  return axios.delete(apiUrl.ADMIN_USER_URL + '/' + params.admin_user_pkid + '?uid=' + params.uid);
};

const setRole = (params) => {
  return axios.post(apiUrl.ADMIN_USER_URL + '/role', params);
};

const updateUserPassword = (params) => {
  return axios.put(apiUrl.ADMIN_USER_URL + '/change-password', params);
};

const updateUserPassword2 = (params) => {
  return axios.put(apiUrl.ADMIN_USER_URL + '/no-change-password', params);
};

const setWarehouse = (params) => {
  return axios.post(apiUrl.ADMIN_USER_URL + '/warehouse', params);
};

const listPorductStockWarningWarehouse = (filter) => {
  return axios.get(apiUrl.ADMIN_USER_URL + '/stock-warning-warehouse', {
    params: filter
  });
};

const setPorductStockWarningWarehouse = (params) => {
  return axios.post(apiUrl.ADMIN_USER_URL + '/stock-warning-warehouse', params);
};

export {
  getUser,
  updateUser,
  deleteUser,
  insertUser,
  setRole,
  updateUserPassword,
  updateUserPassword2,
  setWarehouse,
  listPorductStockWarningWarehouse,
  setPorductStockWarningWarehouse
};
