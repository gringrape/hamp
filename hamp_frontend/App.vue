<template>
  <div id="app" class="mother">
    <nav class="bg-green-600 p-8 shadow-xl flex items-center justify-between">
      <div 
        class="font-semibold text-gray-200 text-3xl tracking-wider flex items-center">
        <a href="">
          Hamp
        </a>
      </div>
      <div class="flex text-gray-100 text-xl tracking-wide mr-6">
        <router-link to="/" class="ml-6 hover:text-gray-700">
            List
        </router-link>
        <router-link v-if="!isLogined" to="/signUp" class="ml-6 hover:text-gray-700">
            Sign Up
        </router-link>
        <router-link v-if="!isLogined" to="/login" class="ml-6 hover:text-gray-700">
            Login
        </router-link>
        <router-link v-if="isLogined" to="/host" class="ml-6 hover:text-gray-700">
            Host
        </router-link>
        <a href="" @click="logout" v-if="isLogined" class="ml-6 hover:text-gray-700">
            Log out
        </a>
      </div>
    </nav>
    <transition name="router-anim" class="bg-gray-100 h-screen pb-32 pt-10" enter-active-class="animated flash" leave-active-class="fadeOutDown">
      <router-view/>
    </transition>
    <transition class="bg-gray-100 h-screen pb-32 pt-10" enter-active-class="animated fadeInDown" leave-active-class="fadeOutDown">
      <router-view name="searchbar"/> 
      <router-view name="list"/>
    </transition>
  </div>
</template>

<script>

export default {
  name: 'App',
  data() {
    return {
      token: ''
    }
  },
  created() {
    this.token = window.localStorage.getItem('accesstoken');
  },
  computed: {
    isLogined() {
      if(this.token != null) {
        return true
      }
      return false
    }
  },
  methods: {
    logout() {
      window.localStorage.removeItem('accesstoken');
    }
  }
}
</script>

<style>
  @import 'vue-datetime/dist/vue-datetime.css';
  @import "../import.css";
  @import url('https://fonts.googleapis.com/css?family=Nanum+Gothic&display=swap');
  @import url("https://fonts.googleapis.com/icon?family=Material+Icons"); 
  @import './assets/css/animate.css';
  .mother {
    font-family: 'Nanum Gothic', sans-serif;
  }
</style>
