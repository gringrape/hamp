<template>
  <div class="List mx-auto w-280 mt-20 p-2">
    <div class="flex justify-between">
      <h1 class="text-gray-900 font-semibold tracking-wider">
          현재 진행되는 모임들
      </h1>
    </div>
    <p 
      class="mt-1 text-sm text-gray-700">
        관심있는 모임의 목록을 확인해보세요
    </p>
    <div v-for="tray in box" class="mt-6 flex">
      <div 
        v-for="meeting in tray" 
        class="w-64 shadow-md p-2 rounded-lg leading-normal mx-2 bg-white truncate" 
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
            class="text-xs"
            >
              참석인원 {{ attendees[meeting['id']] }} 명
          </h4>
          <a 
            href="#" 
            class=" bg-summer-sea-4 hover:bg-blue-900 text-gray-200 font-bold px-4 py-2 rounded-lg text-xs mr-2 tracking-wider" 
            @click="goDetail(meeting['id'])">
              자세히 보기
          </a>
        </div>
      </div>
    </div>
    <div class="pageBar absolute flex mt-10 mx-120">
      <button class="mr-2 bg-spring-3 text-gray-800 font-bold p-1 rounded">
        <
      </button>
      <button v-for="number in pages" class="mr-2 bg-spring-3 text-gray-800 font-bold p-1 rounded">
        <h1 @click="getPage(number)">{{ number }}</h1>
      </button>
      <button class="mr-2 bg-spring-3 text-gray-800 font-bold p-1 rounded">
        >
      </button>
    </div>
    {{ params }}
  </div>
</template>

<script>
import { RepositoryFactory } from '../../repositories/RepositoryFactory'
const ApplyingUsersRepository = RepositoryFactory.get('applyingUsers')
const MeetingsRepository = RepositoryFactory.get('meetings')

export default {
  name: 'List',
  data () {
    return {
      box: [],
      pages: '',
      attendees: {}
    }
  },
  computed: {
    params() {
      const store = this.$store
      let parameters = store.getters.searchParams
      console.log("실행")
      this.fetch(parameters)
    }
  },
  methods: {
    async fetch(parameters) {
      const response = await MeetingsRepository.get(parameters)
        .catch((error) => console.log("에러발생"));

      const data = response.data;
      const headers = response.headers;

      this.pages = parseInt(headers['totalpages']);

      await this.getAttendees(data)
      this.putInBox(data);
    },
    goDetail(meetingId) {
      const router = this.$router
      const store = this.$store

      store.commit('setMeeting', meetingId);
      router.push({name: 'Detail'});
    },
    putInBox(data) {
      this.box = []
      let tray = []
      let box = this.box
      let meeting
      
      for(meeting of data) {
        if(tray.length == 4) {
          box.push(tray)
          tray = []
        }
        tray.push(meeting)
      }
      box.push(tray)
    },
    async getAttendees(meetings) {
      for(var meeting of meetings) {
        const { data } = await ApplyingUsersRepository.list(meeting['id'])
        var key = meeting['id']
        this.attendees[key] = data.length
      }      
    },
    async getPage(number) {
      const store = this.$store
      let parameters = store.getters.searchParams

      store.commit('setPageSearchParams', number);

      const { data } = await MeetingsRepository.get(parameters)
        .catch((error) => console.log("에러발생"));

      this.putInBox(data);
    }

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#meetingContainer {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>