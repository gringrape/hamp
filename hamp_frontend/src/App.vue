<template>
  <div id="app">
    <nav 
      class="bg-green-600 p-8 shadow-xl flex items-center justify-between">
      <div 
        class="font-semibold text-gray-200 text-3xl tracking-wider flex items-center">
        <a href="#">
          Hamp
        </a>
      </div>
      <div class="flex text-gray-100 text-xl tracking-wide mr-6">
        <a href="" class="ml-6 hover:text-gray-700">
            List
        </a>
        <router-link to="/signUp" class="ml-6 hover:text-gray-700">
            Sign Up
        </router-link>
        <router-link to="/login" class="ml-6 hover:text-gray-700">
            Login
        </router-link>
      </div>
    </nav>
    <div class="bg-gray-200 h-auto pb-32 pt-10">
      <router-view/>
      <router-view name="category" class="ml-20"/> 
      <router-view name="list" class="mt-10 ml-20"/> 
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  @import "../import.css"
</style>
