import Vue from 'vue'
import Router from 'vue-router'
import List from '@/components/main/List'
import SearchBar from '@/components/main/SearchBar'
import Detail from '@/components/Detail'
import SignUpForm from '@/components/SignUpForm'
import LoginForm from '@/components/LoginForm'
import HostForm from '@/components/HostForm'
import MyInfo from '@/components/MyInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      components: {
        list: List,
        searchbar: SearchBar
      }
    },
    {
      path: '/meetings/',
      name: 'Detail',
      component: Detail
    },
    {
      path: '/signUp',
      name: 'SignUpForm',
      component: SignUpForm
    },
    {
      path: '/login',
      name: 'LoginForm',
      component: LoginForm
    },
    {
      path: '/host',
      name: 'HostForm',
      component: HostForm
    },
    {
      path: '/myinfo',
      name: 'MyInfo',
      component: MyInfo
    }
  ]
})
