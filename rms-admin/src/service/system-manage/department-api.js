'use strict';
import axios from '../../script/http';
import * as apiUrl from '../../script/api_url';
import {} from '../../data/data';

const getAllDepartment = () => {
  return axios.get(apiUrl.ROLE_DEPARTMENT_URL + '/departmentList/all');
};

const getDepartment = () => {
  return axios.get(apiUrl.ROLE_DEPARTMENT_URL + '/departmentList');
};
const updateDepartment = (params) => {
  return axios.put(apiUrl.ROLE_DEPARTMENT_URL + '/' + params.adminDptPkid, params);
};

const insertDepartment = (params) => {
  return axios.post(apiUrl.ROLE_DEPARTMENT_URL, params);
};

const deleteDepartment = (params) => {
  return axios.post(apiUrl.ROLE_DEPARTMENT_URL + '/delete', params);
};

export {
  getDepartment,
  updateDepartment,
  deleteDepartment,
  insertDepartment,
  getAllDepartment
};
