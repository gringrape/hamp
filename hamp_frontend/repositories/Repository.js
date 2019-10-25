import axios from 'axios';

const baseDomain = "http://localhost:8080/";
const baseURL = `${baseDomain}`;
var token = window.localStorage.getItem('accesstoken')

export default axios.create({
  baseURL,
  headers: {
    'Authorization': token
  }
});