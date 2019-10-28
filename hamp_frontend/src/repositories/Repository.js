import axios from 'axios';

const baseDomain = "http://13.209.184.17:8080/hamp-0.0.1-SNAPSHOT";
const baseURL = `${baseDomain}`;
const token = window.localStorage.getItem("accesstoken")

if(token != null) {
  axios.defaults.headers.common['Authorization'] = token // for all requests
}

export default axios.create({
  baseURL
});
