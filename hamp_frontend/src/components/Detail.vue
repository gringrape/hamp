<template>
  <div class="Detail bg-white w-220 p-8 border border-gray-400 rounded mx-auto"
      
  >
    <div id="title" class="mx-auto border-t-4 border-b-4 py-2 border-gray-900">
      <h3 class="text-sm text-gray-600">{{ meeting['meetDate'] }}</h3>
      <h1 class="text-2xl text-gray-900 font-extrabold">{{ meeting['title'] }}</h1>
      <h3 class="text-xs text-gray-600 mt-1 font-bold">주최자:
        <span class="text-blue-500 font-bold">{{ writer['nick'] }}</span>
        <span class="font-bold text-gray-600">{{ writer['email'] }}</span>
      </h3>      
    </div>
    <div id="detail_description" class="mx-auto mt-5">
      <h1 class="text-gray-900 font-bold text-lg">모임 설명</h1>
      <p class="mt-5 text-gray-900">
        {{ meeting['description'] }}
      </p>
    </div>
    <div id="detail_access" class="mx-auto mt-5">
      <h1 class="text-gray-900 font-bold text-lg" >세부 사항
      </h1>
      <div class="dateAndLocation flex">
        <div class="mapContainer w-96 h-84 mt-5 pt-2 shadow-md">
          <div id='map' class=" w-84 h-64 rounded border border-gray-400 mx-auto"></div>
          <p class="mt-5 text-sm ml-8 text-gray-900 font-bold">주소: <span class="text-gray-700">{{ meeting['address'] }}</span></p>
        </div>
        <div class="calendar ml-12 shadow-md mt-5 w-96 h-84">
          <vc-calendar 
            :attributes="attrs" 
            class="w-84 h-64 border-none mx-auto"/>
          <div class="flex ml-10 mt-5">
            <div class="box w-4 h-4 bg-blue-600 rounded mr-2 mt-1"></div>
            <p class=" text-gray-900 font-bold text-sm">오늘날짜</p>
            <div class="box w-4 h-4 bg-red-600 rounded ml-4 mr-2 mt-1"></div>
            <p class=" text-gray-900 font-bold text-sm">모임날짜</p>
          </div>
        </div>
      </div>
    </div>
    <div id="detail_images" class="mx-auto mt-5">
      <h1 class="text-gray-900 font-bold text-lg">사진</h1>
      <div id="images" class="mt-5 flex">
        <img class="mx-2" src="@/assets/logo.png" alt="">
        <img class="mx-2" src="@/assets/logo.png" alt="">
        <img class="mx-2" src="@/assets/logo.png" alt="">
      </div>
    </div>
    <div id="detail_images" class="mx-auto mt-5">
      <h1 class="text-gray-900 font-bold text-lg">참석자</h1>
      <div class="attendees mt-4">
        <h2 v-for="attendee in attendees" :key="attendee.id">
          {{attendee.nick}} {{ `(${attendee.email})`}}
        </h2>
      </div>
    </div>
    <div id="buttons" class="my-5 mx-auto flex items-center justify-center">
      <button class="px-6 py-4 bg-blue-500 rounded-lg shadow-lg hover:bg-blue-400 font-bold text-white" 
             v-if="isLogined"
             @click="attendThis"
             >
        참여 하기
      </button>
      <button class="mx-4 px-6 py-4 bg-blue-500 rounded-lg shadow-lg hover:bg-blue-400 font-bold text-white" 
              @click="goList">
        뒤로 가기
      </button>
      <button class="px-6 py-4 bg-blue-500 rounded-lg shadow-lg hover:bg-blue-400 font-bold text-white" 
              @click="deleteThis">
        삭제하기
      </button> 
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import { RepositoryFactory } from '../repositories/RepositoryFactory'
const MeetingsRepository = RepositoryFactory.get('meetings')
const ApplyingUsersRepository = RepositoryFactory.get('applyingUsers')

const store = this.$store

export default {
  name: 'Detail',
  data() {
    return {
      meeting: {},
      attendees: {},
      writer: {},
      attrs: [
        {
          key: 'today',
          highlight: true,
          dates: new Date()
        }
      ]
    }
  },
  created() {
    this.fetch();
  },
  updated() {
    this.drawMap();
  },
  computed: {
    meetingId() {
      const store = this.$store
      return store.getters.meetingId;
    },
    isLogined() {
      const store = this.$store
      return store.getters.isLogined
    }
  },
  methods: {
    async fetch() {  
      let kakaoScript = document.createElement('script')
      kakaoScript.async = true
      kakaoScript.setAttribute('src', "//dapi.kakao.com/v2/maps/sdk.js?appkey=58b7a9cfaf4c26e305362f4d944999f0&autoload=false")

      await document.head.appendChild(kakaoScript)

      const { data } = await MeetingsRepository.getMeeting(this.meetingId)
        .catch((error) => this.meetingNotFoundErrorHandler());
      this.meeting = data;
      this.writer = this.meeting.writer
      console.log(this.meeting)
      this.getAttendees();
      this.setDate();
    },
    async getAttendees() {
      const { data } = await ApplyingUsersRepository.list(this.meetingId);
      this.attendees = data;
    },
    async attendThis() {
      const { data } = await ApplyingUsersRepository.attend(this.meetingId);
      this.getAttendees();
    },
    async deleteThis() {
      let result = await MeetingsRepository.deleteMeeting(this.meetingId);      
    },
    drawMap() {
      let latitude = this.meeting.latitude;
      let longitude = this.meeting.longitude;
      console.log("지도")


      kakao.maps.load(function() {
        var container = document.getElementById('map');
        var options = {
          center: new kakao.maps.LatLng(latitude, longitude),
          level: 3
        };
        var map = new kakao.maps.Map(container, options);

        var markerPosition  = new kakao.maps.LatLng(latitude, longitude); 

        var marker = new kakao.maps.Marker({
            position: markerPosition
        });

        marker.setMap(map);
      });
    },
    setDate() {
      let start = this.meeting.startDate
      let year = moment(start).format('YYYY');
      let month = moment(start).format('M');
      let day = moment(start).format('D');

      let temp = {}
      temp.key = 'DDay'
      temp.highlight = 'red'
      temp.dates = new Date(year, month - 1, day)

      this.attrs.push(temp)

      console.log(year, month, day)
    },
    goList() {
      const router = this.$router

      router.push({path: '/'});
    },
    meetingNotFoundErrorHandler() {
      const router = this.$router
      console.log("오류가 발생하였습니다.")
      
      router.push({path: "/"});
    }
  }
}
</script>