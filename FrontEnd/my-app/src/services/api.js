import axios from "axios";

const API_URL = "http://localhost:8081";

export const startTicketOperations = (params) =>
  axios.post(${API_URL}/start, params);

export const getLogs = () => axios.get(${API_URL}/logs);