<template>
  <div class="main h-screen">
    <div class="main w-220 mx-auto p-16 rounded">
      <div class="profileCard flex items-center">
        <div class="pictureContainer">
          <img src="../assets/logo.png" alt="">
          <p class=" text-center">프로필 사진</p>
        </div>
        <div class="information ml-16">
          <div class="name mb-10 flex">
            <h1 class="font-extrabold text-xl text-gray-800">이름: </h1>
            <h1 class=" text-gray-700 ml-8" >{{ this.thisUser.nick }}</h1>
          </div>
          <div class="email flex">
            <h1 class="font-extrabold text-xl text-gray-800">이메일: </h1>
            <h1 class=" text-gray-700 ml-8" >{{ this.thisUser.email }}</h1>
          </div>
        </div>
      </div>
      <div class="itineraries relative">
        <div class="title ml-2 bg-gray-100 p-1 absolute mt-8 z-10 bg-gray-200">
          <h1 class=" font-bold text-gray-800">참여할 모임들</h1>
        </div>
        <div class="absolute border-gray-600 border-2 w-full h-auto p-8 mt-12">
          <h1 
            v-for="meeting in thisUser.appliedMeetings"
            class="mt-2 font-bold text-gray-700"> 
              {{ meeting.title }} 
          </h1>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { RepositoryFactory } from '../repositories/RepositoryFactory'
const SessionRepository = RepositoryFactory.get('session')

export default {
  name: "MyInfo",
  data() {
    return {
      thisUser: {}
    }
  },
  methods: {
    async fetch() {
      const { data } = await SessionRepository.userInfo();
      this.thisUser = data;
    }
  },
  created() {
    this.fetch();
  }
}
</script>