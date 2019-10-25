import Repository from './Repository';

const resource = '/session'
export default {
  login(userInformation) {
    return Repository
      .post(`${resource}`, userInformation);
  }
};