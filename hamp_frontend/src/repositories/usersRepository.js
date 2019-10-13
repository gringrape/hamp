import Repository from './Repository';

const resource = '/users'
export default {
  createUser(payload) {
    return Repository
      .post(`${resource}`, payload);
  }
};