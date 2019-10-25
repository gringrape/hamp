import Repository from './Repository';

export default {
  attend(meetingId) {
    const resource = `meetings/${meetingId}/applyingUsers`

    return Repository.post(`${resource}`)
  }
};
