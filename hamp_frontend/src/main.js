// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Datetime from 'vue-datetime'
import Vuex from 'vuex'

Vue.use(Vuex)
Vue.use(Datetime)
Vue.config.productionTip = false

const store = new Vuex.Store({
  state: {
    meetingId: null
  },
  mutations: {
    setMeeting(state, id) {
      state.meetingId = id
    }
  },
  actions: {
    
  },
  getters: {
    meetingId(state) {
      return state.meetingId;
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})
