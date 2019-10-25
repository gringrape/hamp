<template>
  <div class="">
    <div class="max-w-xs mx-auto bg-white text-center card shadow-md h-auto border-gray-400 border pb-4">
      <h1 class="mt-5 text-gray-900 font-bold text-2xl tracking-wider">
        로그인
      </h1>
      <div class="mt-10">
        <p class=" mr-48 font-bold text-sm text-gray-800">
          이메일 주소
        </p>
        <input class="mt-1 px-2 py-1 w-64 border border-gray-400 rounded-lg 
                      focus:shadow-outline focus:outline-none text-sm text-gray-800" 
               type="text"
               v-model="userInfo.email">
      </div>
      <div class="mt-5"> 
        <p class=" mr-48 -ml-4 font-bold text-sm text-gray-800">
          비밀번호
        </p>
        <input class="mt-1 px-2 py-1 w-64 border border-gray-400 rounded-lg 
                      focus:shadow-outline focus:outline-none text-sm text-gray-800" 
               type="password"
               v-model="userInfo.password">
      </div>
      <button class=" bg-blue-500 rounded py-2 px-10 text-white text-sm w-64
                     font-bold mt-8 mb-5 hover:bg-red-500 tracking-widest"
              @click="login">
        로그인
      </button>
    </div>
    
  </div>
</template>

<script>
import { RepositoryFactory } from '../repositories/RepositoryFactory'
const SessionRepository = RepositoryFactory.get('session')

export default {
  name: "LoginForm",
  data() {
    return {
      userInfo: {}
    }
  },
  methods: {
    async login() {
      const { headers } = 
        await SessionRepository.login(this.userInfo);
      
      window.localStorage
        .setItem("accesstoken", headers['accesstoken']);
      const router = this.$router
      router.push({path: '/'})
      router.go();
    }
  }
}
</script>