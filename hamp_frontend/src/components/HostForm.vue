<template>
  <div class="flex justify-center">
    <div class="card shadow-lg w-196 h-auto bg-gray-300 rounded px-6 py-4 border border-gray-400">
      <div class="title bg-white py-4 px-4 rounded">
        <h1 class="font-extrabold text-gray-900 text-2xl tracking-wider">
          모임 주제:
        </h1>
        <p class="text-gray-600 text-sm mt-1 ">
          모임에서 어떤일을 하는지 말해 주세요
        </p>
        <input type="text" 
               class="mt-2 border border-gray-600 rounded 
                      py-1 px-2 w-172 text-sm text-gray-800 focus:outline-none focus:shadow-outline"
               v-model="meeting.title">
      </div>
      <div class="flex">
        <div class="w-full">
          <div class="location bg-white py-4 px-4 rounded mt-4">
            <h1 class="font-extrabold text-gray-900 text-2xl tracking-wider">
              모임 장소:
            </h1>
            <p class="text-gray-600 text-sm mt-1 ">
              모임의 정확한 위치를 검색해주세요
            </p>
            <div class="dropdown relative inline-block">
              <div class="relative flex">
                <input type="text" v-model="value" 
                      class="border border-gray-600 rounded text-sm mt-2 py-1 px-1 text-gray-700 w-72"
                      placeholder="검색어를 입력하세요"/>
                <button class="absolute right-0 top-12" @click="search(value)">
                  <i class="material-icons text-gray-700 w-8"> search </i>
                </button>
              </div>
              <div class="dropdown-content w-72" :style="{ display: activeDisplay }">
                <p v-for="candidate in candidates">
                  <button class="text-sm text-gray-700 font-bold"
                          @click="setAddress(candidate)">
                    {{ candidate.place_name }}
                  </button>
                </p>
              </div>
            </div>
          </div>
          <div class="topic bg-white py-4 px-4 rounded mt-4">
            <h1 class="font-extrabold text-gray-900 text-2xl tracking-wider">
              모임 언어:
            </h1>
            <p class="text-gray-600 text-sm mt-1 ">
              모임에서 사용할 컴퓨터 언어를 선택해주세요
            </p>
            <div class="mt-2 text-gray-800 text-sm font-bold">
              <input type="radio" value="1" id="c" v-model="meeting.topicId">
                <label class="" for="c">C 언어</label>
              <input type="radio" class="ml-1" value="2" id="python" v-model="meeting.topicId">
                <label class="" for="c">Python</label>
              <input type="radio" class="ml-1" value="3" id="java" v-model="meeting.topicId">
                <label class="" for="c">Java</label>
              <input type="radio" class="ml-1" value="4" id="javascript" v-model="meeting.topicId">
                <label class="" for="c">Javascript</label>
            </div>
          </div>
        </div>
        <div class="date bg-white py-4 px-4 rounded mt-4 ml-4 w-full">
          <h1 class="font-extrabold text-gray-900 text-2xl tracking-wider">
            모임 시간:
          </h1>
          <p class="text-gray-600 text-sm mt-1 ">
            모임이 열리는 날짜와 시간을 입력해주세요
          </p>
          <h1 class="mt-4 font-extrabold text-gray-800 text-base tracking-wider">
            모임 시작 시간
          </h1>
          <datetime type="datetime" v-model="meeting.startDate" 
            class="border border-gray-600 rounded text-sm mt-1 py-1 px-1 text-gray-700 w-full"
            :input-style="'width:100%;'">
          </datetime>
          <h1 class="mt-4 font-extrabold text-gray-800 text-base tracking-wider">
            모임 종료 시간
          </h1>
          <datetime type="datetime" v-model="meeting.endDate" 
            class="border border-gray-600 rounded text-sm mt-1 py-1 px-1 text-gray-700 w-full"
            :input-style="'width:100%;'">
          </datetime>
        </div>
      </div>
      <div class="description bg-white mt-4 py-4 px-4 rounded">
        <h1 class="font-extrabold text-gray-900 text-2xl tracking-wider">
          모임 설명:
        </h1>
        <p class="text-gray-600 text-sm mt-1 ">
          모임이 어떻게 진행되는지 설명해주세요
        </p>
        <vue-editor id="editor" useCustomImageHandler 
          @image-added="handleImageAdded" 
          v-model="meeting.description"> 
        </vue-editor>
      </div>
      <div class="flex justify-center">
        <button class="mt-4 font-extrabold text-white bg-blue-500
                     rounded w-40 h-16 text-lg tracking-widest
                     hover:bg-white hover:border-red-600 hover:border hover:text-red-600"
                @click="submit">
          모임 등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { VueEditor } from "vue2-editor";
import { RepositoryFactory } from '../repositories/RepositoryFactory'
import { KakaoRepositoryFactory } from '../kakaoRepositories/RepositoryFactory'

const MeetingsRepository = RepositoryFactory.get('meetings')
const KeywordRespository = KakaoRepositoryFactory.get('keyword')

export default {
  name: 'HostForm',
  components : {
    VueEditor
  },
  data() {
    return {
      meeting: {},
      value: '',
      candidates: [],
      activeDisplay: 'none'
    }
  },
  methods: {
    async submit() {
      const router = this.$router
      const { data } = await MeetingsRepository.createMeeting(this.meeting);

      router.push({path: '/'});
    },
    async search(arg) {
      const { data } = await KeywordRespository.get(arg);
      console.log(data)
      this.candidates = data.documents;
      this.activeDisplay = 'block'
    },
    setAddress(selected) {
      const meeting = this.meeting

      meeting.address = selected.address_name
      this.value = selected.address_name
      meeting.longitude = selected.x
      meeting.latitude = selected.y

      console.log(meeting)

      this.activeDisplay = 'none'
    }
  }
}
</script>

<style scoped>
.dropdown-content {
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  padding: 12px 16px;
  z-index: 1;
}
</style>