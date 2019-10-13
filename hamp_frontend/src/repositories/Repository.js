import axios from 'axios';

const baseDomain = "http://localhost:8080";
const baseURL = `${baseDomain}`;

export default axios.create({
  baseURL
});