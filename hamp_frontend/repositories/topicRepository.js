import Repository from './Repository';

const resource = '/topics'
export default {
  get() {
    return Repository.get(`${resource}`);
  }
};