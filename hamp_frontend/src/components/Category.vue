<template>
  <div 
    class="categories">
    <h1 
      class="text-gray-900 font-semibold tracking-wider">
        카테고리
    </h1>
    <p 
      class="mt-1 text-sm text-gray-700">
        관심있는 주제의 모임을 선택해보세요
    </p>
    <div class="title flex">
      <div v-for="topic in topics" :key="topic['id']">
        <div class="topic-image-container">
          <img src="../assets/JavaLogo.png">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Category',
  data() {
    const imageLocation = '../assets/'
    return {
      topics: [] // get from backend
    }
  },
  methods: {
    getTopics: function() {
      axios.get("http://localhost:8080/topics")
      .then(response => this.topics = response.data)
    }
  },
  mounted() {
    this.getTopics();
  }
}
</script>