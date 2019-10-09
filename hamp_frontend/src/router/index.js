import Vue from 'vue'
import Router from 'vue-router'
import List from '@/components/List'
import Category from '@/components/Category'
import Detail from '@/components/Detail'
import SignUpForm from '@/components/SignUpForm'
import LoginForm from '@/components/LoginForm'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      components: {
        list: List,
        category: Category
      }
    },
    {
      path: '/meetings/:id',
      name: 'Detail',
      component: Detail
    },
    {
      path: '/signUp',
      name: "SignUpForm",
      component: SignUpForm
    },
    {
      path: '/login',
      name: "LoginForm",
      component: LoginForm
    }
  ]
})
