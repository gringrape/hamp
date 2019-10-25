import Repository from './Repository';

const resource = '/search/keyword.json'
export default {
  get(arg) {
    return Repository.get(`${resource}?query=${arg}`)
  }
};
