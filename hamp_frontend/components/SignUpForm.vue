<template>
  <div id="signUp"
       class="max-w-xs text-center mx-auto shadow-md h-auto border-gray-400 border pb-4 pt-6">
    <div class="title">
      <h1 class="text-gray-900 font-bold tracking-wide text-2xl">
        회원가입
      </h1>
    </div>
    <div class="mt-8">
      <h2 class="text-gray-900 font-bold mr-56 text-sm">
        이름
      </h2>
      <input 
        type="text" 
        class="w-64 h-8 mt-1 border border-gray-400 px-2 py-4 rounded-lg
              focus:outline-none focus:shadow-outline text-sm text-gray-800"
        v-model="payload.nick">
    </div> 
    <div class="mt-4">
      <h2 class="text-gray-900 font-bold mr-48 ml-2 text-sm">
        이메일 주소
      </h2>
      <input 
        type="text" 
        class="w-64 h-8 mt-1 border border-gray-400 px-2 py-4 rounded-lg
              focus:outline-none focus:shadow-outline text-sm text-gray-800"
        v-model="payload.email">
    </div> 
    <div class="mt-4">
      <h2 class="text-gray-900 font-bold mr-48 text-sm">
        비밀번호
      </h2>
      <input 
        type="password" 
        class="mt-1 w-64 h-8 border border-gray-400 px-2 py-4 
               rounded-lg focus:outline-none focus:shadow-outline text-sm text-gray-800"
        v-model="payload.password">
    </div>
    <div class="mt-10">
      <button 
        class="bg-purple-600 text-sm text-white w-64 py-2 rounded-sm font-bold tracking-wider
               hover:bg-purple-500"
        @click="signUp">
        계속하기  
      </button>  
    </div> 
  </div>
</template>

<script>
import { RepositoryFactory } from '../repositories/RepositoryFactory'
const UsersRepository = RepositoryFactory.get('users')

export default {
  name: 'SignUpForm',
  data() {
    return {
      payload: {}
    }
  },
  methods: {
    async signUp() {
      const response = await UsersRepository.createUser(this.payload);
      console.log(response)
    }
  } 
}
</script>