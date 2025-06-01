import axios from 'axios';

const REST_API_BASE_URL = "http://localhost:8080/api/employees";

// function to get all employees from the backend api

// export const listEmployees = () => {
//     return axios.get(REST_API_BASE_URL);
// }

export const listEmployees = () => axios.get(REST_API_BASE_URL);

export const createEmployee = (employeeObject) => axios.post(REST_API_BASE_URL, employeeObject);

export const getEmployee = (employeeId) => axios.get(REST_API_BASE_URL + "/" + employeeId);

export const updateEmployee = (employeeId, employeeObject) => axios.put(REST_API_BASE_URL + "/" + employeeId, employeeObject);

export const deleteEmployee = (employeeId) => axios.delete(REST_API_BASE_URL + "/" + employeeId);