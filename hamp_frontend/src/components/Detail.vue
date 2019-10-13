<template>
  <div class="Detail">
    <div id="title" class="mx-auto w-3/5 border-t-4 border-b-4 py-2 border-gray-900">
      <h3 class="text-sm text-gray-600">{{ meeting['meetDate'] }}</h3>
      <h1 class="text-lg text-gray-900 font-extrabold">{{ meeting['title'] }}</h1>
      <h3 class="text-xs text-gray-600 mt-1 font-bold">주최자:
        <span class="text-blue-500 font-bold">gringrape</span>
        <span class="font-bold text-gray-600">"gringrape200@gmail.com"</span>
        <span id="comment" class=" font-medium text-red-600 font-bold">#여기에 모임을 개설한 사람을 넣을거에요~</span>
      </h3>      
    </div>
    <div id="detail_description" class="mx-auto w-3/5 mt-5">
      <h1 class="text-gray-900 font-bold text-lg">모임 설명</h1>
      <p class="mt-5 text-gray-900">
        {{ meeting['description'] }}
      </p>
    </div>
    <div id="detail_access" class="mx-auto w-3/5 mt-5">
      <h1 class="text-gray-900 font-bold text-lg">세부 사항
        <span class="text-red-600 text-sm">#모임에 어떻게 참여할수 있는지 교통수단같은 접근에 관한 사항을 넣을 거에요</span>
      </h1>
      <p class="mt-5 text-gray-900">
        Lorem ipsum dolor, sit amet consectetur adipisicing elit. Nam maiores minima eveniet officiis eaque, quo qui repellendus corrupti quaerat aut nesciunt nemo quibusdam id quis odio enim repellat beatae? Sequi?
      </p>
    </div>
    <div id="detail_images" class="mx-auto w-3/5 mt-5">
      <h1 class="text-gray-900 font-bold text-lg">사진</h1>
      <div id="images" class="mt-5 flex">
        <img class="mx-2" src="@/assets/logo.png" alt="">
        <img class="mx-2" src="@/assets/logo.png" alt="">
        <img class="mx-2" src="@/assets/logo.png" alt="">
      </div>
    </div>
    <div id="detail_images" class="mx-auto w-3/5 mt-5">
      <h1 class="text-gray-900 font-bold text-lg">참석자
        <span class="text-red-600 font-bold text-sm">#여기에는 참석하는 사람의 아이디를 서버의 모임 endpoint에서 받아와서 넣을거에요</span>
      </h1>
    </div>
    <div id="buttons" class="my-5 mx-auto w-64 flex">
      <button class="mx-4 p-4 bg-purple-600 rounded-lg shadow-lg hover:bg-purple-400 text-white" 
              @click="goList">
        뒤로 가기
      </button>
      <button class="p-4 bg-purple-600 rounded-lg shadow-lg hover:bg-purple-400 text-white" 
              @click="deleteThis">
        삭제하기
      </button> 
    </div>
  </div>
</template>

<script>
import { RepositoryFactory } from '../repositories/RepositoryFactory'
const MeetingsRepository = RepositoryFactory.get('meetings')

export default {
  name: 'Detail',
  data() {
    return {
      meeting: {},
      meetingId: ''
    }
  },
  created() {
    this.meetingId = this.$route.params['id'];
    this.fetch();
  },
  methods: {
    async fetch() {  
      const { data } = await MeetingsRepository.getMeeting(this.meetingId);
      this.meeting = data; 
    },
    async deleteThis() {
      let result = await MeetingsRepository.deleteMeeting(this.meetingId);      
    },
    goList() {
      var router = this.$router

      router.push({path: '/'});
    }
  }
}
</script>