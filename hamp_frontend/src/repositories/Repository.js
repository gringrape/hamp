import axios from 'axios';

const baseDomain = "http://localhost:8080/";
const baseURL = `${baseDomain}`;
const token = window.localStorage.getItem("accesstoken")

if(token != null) {
  axios.defaults.headers.common['Authorization'] = token // for all requests
}

export default axios.create({
  baseURL
});
