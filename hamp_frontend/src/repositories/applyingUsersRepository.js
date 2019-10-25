import Repository from './Repository';

export default {
  list(meetingId) {
    const resource = `meetings/${meetingId}/applyingUsers`

    return Repository.get(`${resource}`)
  },
  attend(meetingId) {
    const resource = `meetings/${meetingId}/applyingUsers`

    return Repository.post(`${resource}`)
  }
};
