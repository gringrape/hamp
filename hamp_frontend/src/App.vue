<template>
  <div id="app" class="mother">
    <nav id="navigation" class="border-b-8 border-spring-6 p-8 shadow-xl flex items-end justify-between h-48 relative">
      <div>
      </div>
      <div 
        class="font-semibold text-gray-200 text-3xl tracking-wider flex items-center">
      </div>
      <div class="flex text-gray-100 text-xl tracking-wide mr-6">
        <router-link to="/" class="link ml-6 hover:text-gray-700 animated">
            List
        </router-link>
        <router-link v-if="!isLogined" to="/signUp" class="link ml-6 hover:text-gray-700 animated">
            Sign Up
        </router-link>
        <router-link v-if="!isLogined" to="/login" class="link ml-6 hover:text-gray-700 animated">
            Login
        </router-link>
        <router-link v-if="isLogined" to="/myinfo" class="link ml-6 hover:text-gray-700 animated">
            MyInfo
        </router-link>
        <router-link v-if="isLogined" to="/host" class="link ml-6 hover:text-gray-700 animated">
            Host
        </router-link>
        <a href="" @click="logout" v-if="isLogined" class="link ml-6 hover:text-gray-700 animated">
            Log out
        </a>
      </div>
    </nav>
    <div class="bg-gray-200 h-full pb-32 pt-10">
      <transition name="router-anim" class="" enter-active-class="animated fadeIn ">
        <router-view/>
      </transition>
      <transition name="router-anim" class="" enter-active-class="animated fadeIn ">
        <router-view name="searchbar"/>
      </transition> 
      <transition name="router-anim" class="" enter-active-class="animated fadeIn ">
        <router-view name="list"/>
      </transition>
    </div>
    <div class="bgbottom bg-gray-200 h-32">
    </div>
  </div>
</template>

<script>
import { KakaoRepositoryFactory } from './kakaoRepositories/RepositoryFactory'
const KeywordRepository = KakaoRepositoryFactory.get('keyword')

export default {
  name: 'App',
  data() {
    return {
    }
  },
  computed: {
    isLogined() {
      return this.$store.getters.isLogined
    }
  },
  methods: {
    logout() {
      window.localStorage.removeItem('accesstoken');
    },
    async test(arg) {
      const { data } = await KeywordRepository.get(arg);
      console.log(data)
    },
    setEffect(e) {
      let element = e.target;
      element.classList.add('animated')
      element.classList.add('bounce')
    }
  }
}
</script>

<style>
  @import 'vue-datetime/dist/vue-datetime.css';
  @import "../import.css";
  @import url('https://fonts.googleapis.com/css?family=Nanum+Gothic&display=swap');
  @import url("https://fonts.googleapis.com/icon?family=Material+Icons"); 
  @import "./assets/css/animate.css";

  .mother {
    font-family: 'Nanum Gothic', sans-serif;
  }
  #navigation {
    background-image: url('./assets/nav.svg');
    background-size: auto 100%;
    background-repeat: no-repeat;
  }
  .link:hover {
    animation-name: swing;
  }
</style>
