import Repository from './Repository';

const resource = '/meetings'
export default {
  // get(params) {
  //   return Repository.get(`${resource}?address=${params.address}&topicId=${params.topicId}&keyword=${params.keyword}&durationStart=${params.durationStart}&durationEnd=${params.durationEnd}`);
  // },
  get() {
    return Repository.get(`${resource}`);
  },

  getMeeting(meetingId) {
    return Repository.get(`${resource}/${meetingId}`);
  },

  createMeeting(payload) {
    return Repository.post(`${resource}`, payload);
  },

  deleteMeeting(meetingId) {
    return Repository.delete(`${resource}/${meetingId}`);
  }
};