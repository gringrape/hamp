import Repository from './Repository';

const resource = '/meetings'
export default {
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