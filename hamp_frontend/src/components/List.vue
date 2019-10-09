<template>
  <div class="List">
    <div class="flex justify-between">
      <h1 
        class="text-gray-900 font-semibold tracking-wider">
          현재 진행되는 모임들
      </h1>
      <h1>
        <a href="" class="mr-64 text-blue-700">모두보기</a>
      </h1>
    </div>
    <p 
      class="mt-1 text-sm text-gray-700">
        관심있는 모임의 목록을 확인해보세요
    </p>
    <div class="mt-2 flex">
      <div 
        v-for="meeting in list" 
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
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'List',
  data () {
    return {
      list: [],
      msg: 'Welcome to Your Vue.js App',
      router: this.$router
    }
  },
  mounted() {
    this.axiosGETList()
  },
  methods: {
    goDetail: function(meetingId) {
      this.$router.push({name: 'Detail', params: { id: meetingId}});
    },
    axiosGETList: function(){
      axios.get('http://localhost:8080/meetings')
      .then(response => this.list = response.data)
      console.log("전송했어요~")
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>