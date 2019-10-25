import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    meetingId: null,
    searchParams: {
      address: "",
      topicId: "",
      keyword: "",
      durationStart: "",
      durationEnd: "",
      pageNum: ""
    },
    isLogined: null
  },
  mutations: {
    setMeeting(state, id) {
      state.meetingId = id
    },
    setSearchParams(state, params) {
      const clone = JSON.parse(JSON.stringify(params))
      state.searchParams = clone
    },
    setPageSearchParams(state, num) {
      state.searchParams.pageNum = num
    }
  },
  actions: {
    
  },
  getters: {
    meetingId(state) {
      return state.meetingId;
    },
    searchParams(state) {
      return state.searchParams;
    },
    isLogined() {
      let token = window.localStorage.getItem('accesstoken')
      if(token == null) {
        return false
      }
      return true
    }
  }
})