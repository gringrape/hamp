<template>
  <div class="searchBar mx-auto w-268 bg-blue-900 h-24 rounded pl-4 py-4 flex">
    <div class="location flex relative">
      <input class=" text-gray-800 h-12 w-64 rounded px-2 outline-none mt-2" 
             placeholder="모임 주제 검색" 
             type="text"
             v-model="params.keyword">
      <button class="absolute ml-56 mt-5">
        <i class="material-icons text-gray-700 w-8"> search </i>
      </button>
    </div>
    <div class="address flex ml-8">
      <h1 class=" mt-5 text-gray-400 font-bold tracking-wider">모임 지역:</h1>
      <select class="ml-2 mt-3 w-20 text-gray-800 text-sm rounded px-2 h-10"
              v-model="params.address">
        <option value="">전체</option>
        <option value="서울">서울</option>
        <option value="인천">인천</option>
        <option value="부산">부산</option>
        <option value="대구">대구</option>
      </select>
    </div>
    <div class="address flex ml-8">
      <h1 class=" mt-5 text-gray-400 font-bold tracking-wider">모임 언어:</h1>
      <select class="ml-2 mt-3 w-20 text-gray-800 text-sm rounded px-2 h-10"
              v-model="params.topicId">
        <option value="">전체</option>
        <option v-for="topic in topics" :key="topic.id" :value="topic.id">{{ topic.name }}</option>
      </select>
    </div>
     <div class="address flex ml-8">
      <h1 class=" mt-5 text-gray-400 font-bold tracking-wider">기간:</h1>
      <div class="ml-2 pb-4">
        <div class="flex">
          <datetime type="datetime"
          aria-placeholder="시작"
          class="rounded text-sm py-1 px-1 text-gray-700 w-full"
          v-model="params.durationStart"
          >
          </datetime>
          <h1 class="text-xl text-gray-400 font-bold">
            ~
          </h1>
        </div>
        <datetime type="datetime"
          aria-placeholder="종료"
          class="rounded text-sm mt-1 py-1 px-1 text-gray-700 w-full"
          v-model="params.durationEnd">
        </datetime>
      </div>
    </div>
    <button 
      class=" px-4 h-10 mt-3 ml-10 bg-gray-700 text-white font-bold rounded shadow tracking-widest text-sm hover:bg-gray-900"
      @click="setParams">
      검색하기
    </button>
  </div>
</template>

<script>
import { RepositoryFactory } from '../../repositories/RepositoryFactory'
const TopicsRepository = RepositoryFactory.get("topics")

export default {
  name: 'SearchBar',
  data() {
    const imageLocation = ''
    return {
      topics: [],
      params: {
        address: "",
        topicId: "",
        keyword: "",
        durationStart: "",
        durationEnd: ""
      }
    }
  },
  created() {
    this.fetch()
  },
  methods: {
    async fetch() {
      let { data } = await TopicsRepository.get();
      this.topics = data;
    },
    setParams() {
      const store = this.$store

      store.commit('setSearchParams', this.params);
    }
  }
}
</script>