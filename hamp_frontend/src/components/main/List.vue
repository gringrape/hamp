<template>
  <div class="List mx-auto w-268 mt-20 p-2">
    <div class="flex justify-between">
      <h1 class="text-gray-900 font-semibold tracking-wider">
          현재 진행되는 모임들
      </h1>
      <h1>
        <a href="" class="text-blue-700">모두보기</a>
      </h1>
    </div>
    <p 
      class="mt-1 text-sm text-gray-700">
        관심있는 모임의 목록을 확인해보세요
    </p>
    <div v-for="tray in box" class="mt-6 flex">
      <div 
        v-for="meeting in tray" 
        class="w-64 shadow-xl p-2 rounded-lg leading-normal mx-2" 
        @click="">
        <h4 
          class="text-indigo-600 text-xs">
            {{ meeting['meetDate'] }}
        </h4>
        <h2 
          class="text-gray-800 font-bold mt-1">
            {{ meeting['title'] }}
        </h2>
        <p 
          class="mt-1 text-sm text-gray-900 tracking-wide">
            {{ meeting['description'] }}
        </p>
        <div 
          class="flex mt-5 justify-between">
          <h4 
            class="text-xs">
              참석인원 {{ meeting['noAppliers'] }} 명
          </h4>
          <a 
            href="#" 
            class="bg-purple-600 hover:bg-purple-400 text-gray-200 px-4 py-2 rounded-lg text-xs mr-2 tracking-wider" 
            @click="goDetail(meeting['id'])">
              참여하기
          </a>
        </div>
      </div>
    </div>
    <div class="pageBar absolute flex mt-10 mx-120">
      <button class="mr-2 bg-gray-600 text-gray-200 font-bold p-1 rounded">
        <
      </button>
      <button v-for="number in pages" class="mr-2 bg-gray-600 text-gray-200 font-bold p-1 rounded">
        {{ number }}
      </button>
      <button class="mr-2 bg-gray-600 text-gray-200 font-bold p-1 rounded">
        >
      </button>
    </div>
  </div>
</template>

<script>
import { RepositoryFactory } from '../../repositories/RepositoryFactory'
const MeetingsRepository = RepositoryFactory.get('meetings')

export default {
  name: 'List',
  data () {
    return {
      list: [],
      box: [],
      pages: 3
    }
  },
  created() {
    this.fetch()
  },
  methods: {
    async fetch() {
      const { data } = await MeetingsRepository.get();
      this.list = data;
      this.putInBox();
    },
    goDetail(meetingId) {
      const router = this.$router
      const store = this.$store

      store.commit('setMeeting', meetingId);
      router.push({name: 'Detail'});
    },
    putInBox() {
      var tray = []
      var box = this.box
      var meeting 
      for(meeting of this.list) {
        if(tray.length == 4) {
          box.push(tray)
          tray = []
        }
        tray.push(meeting)
      }
      box.push(tray)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>