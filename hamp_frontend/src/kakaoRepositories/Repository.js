import axios from 'axios';

const baseDomain = "https://dapi.kakao.com";
const baseURL = `${baseDomain}/v2/local`;

export default axios.create({
  baseURL,
  headers: {
    'Authorization': 'KakaoAK 62b0cb59fabba034d1aa8c9c5ff1a01f'
  }
});
