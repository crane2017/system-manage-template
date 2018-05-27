'use strict';
import axios from '../../script/http';
import * as apiUrl from '../../script/api_url';

const getMenu = () => {
  return axios.get(apiUrl.ROLE_MENU_URL, {
    params: {}
  });
};

const getMenuList = () => {
  return axios.get(apiUrl.ROLE_MENU_URL + '/menuList');
};

const insertMenu = (params) => {
  return axios.post(apiUrl.ROLE_MENU_URL, params);
};

const updateMenu = (params) => {
  return axios.put(apiUrl.ROLE_MENU_URL + '/' + params.menuPkid, params);
};

const deleteMenu = (params) => {
  return axios.delete(apiUrl.ROLE_MENU_URL + '/' + params.menu_pkid);
};

const deleteList = (params) => {
  return axios.post(apiUrl.ROLE_MENU_URL + '/menuListPkid', params);
};

const getRoleList = (filter) => {
  return axios.get(apiUrl.ROLE_LIST_URL, {
    params: filter
  });
};

const insertRole = (params) => {
  return axios.post(apiUrl.ROLE_LIST_URL, params);
};

const updateRole = (params) => {
  return axios.put(apiUrl.ROLE_LIST_URL, params);
};

const deleteRole = (params) => {
  return axios.delete(apiUrl.ROLE_LIST_URL + '/' + params.admin_role_pkid + '?uid=' + params.uid);
};

const setRole = (params) => {
  return axios.post(apiUrl.ROLE_LIST_URL + '/resource', params);
};

export {
  getMenu,
  getMenuList,
  insertMenu,
  updateMenu,
  deleteMenu,
  deleteList,
  getRoleList,
  insertRole,
  updateRole,
  deleteRole,
  setRole
};
