<template>
  <div class="flex justify-center">
    <div class="card shadow-lg w-196 h-auto bg-gray-300 rounded px-6 py-4">
      <div class="title bg-gray-100 py-4 px-4 rounded">
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
        <div class="location bg-gray-100 py-4 px-4 rounded mt-4">
          <h1 class="font-extrabold text-gray-900 text-2xl tracking-wider">
            모임 장소:
          </h1>
          <p class="text-gray-600 text-sm mt-1 ">
            모임의 정확한 위치를 입력해주세요
          </p>
          <div class="mt-2 border border-gray-600">  
            <DaumPostcode :on-complete=handleAddress />
          </div>
        </div>
        <div class="date bg-gray-100 py-4 px-4 rounded mt-4 ml-4 w-full">
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
            class="border border-gray-600 rounded text-sm mt-1 py-1 px-1 text-gray-700 w-full">
          </datetime>
          <h1 class="mt-4 font-extrabold text-gray-800 text-base tracking-wider">
            모임 종료 시간
          </h1>
          <datetime type="datetime" v-model="meeting.endDate" 
            class="border border-gray-600 rounded text-sm mt-1 py-1 px-1 text-gray-700 w-full">
          </datetime>
        </div>
      </div>
      <div class="description bg-gray-100 mt-4 py-4 px-4 rounded">
        <h1 class="font-extrabold text-gray-900 text-2xl tracking-wider">
          모임 설명:
        </h1>
        <p class="text-gray-600 text-sm mt-1 ">
          모임이 어떻게 진행되는지 설명해주세요
        </p>
        <textarea 
          cols="30" rows="10"
          class="mt-4 w-172 rounded border border-gray-600 focus:outline-none focus:shadow-outline
                 text-sm text-gray-800 px-2 py-1"
          v-model="meeting.description">
        </textarea>
      </div>
      <div class="flex justify-center">
        <button class="mt-4 font-extrabold text-white bg-red-600 
                     rounded w-40 h-16 text-lg tracking-widest
                     hover:bg-white hover:border-red-600 hover:border hover:text-red-600">
          모임 등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import DaumPostcode from 'vuejs-daum-postcode'
import addressHandler from '../utils/daumPostCodeAddressHandler'

var handleAddress = addressHandler;

export default {
  name: 'HostForm',
  components: {
    DaumPostcode
  },
  data() {
    return {
      meeting: {}
    }
  },
  methods: {
    handleAddress
  }
}
</script>

<style scoped>

</style>