<template>
  <div id="app">
    <nav class="flex items-center justify-between flex-wrap bg-gray-800 p-6">
      <div class="flex items-center flex-shrink-0 text-green-500 mr-6">
        <span class="font-semibold text-2xl tracking-tight">hamp</span>
      </div>
      <div class="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
        <div class="text-xl lg:flex-grow">
          <a href="" class="text-green-500 ml-6">List</a>
          <a href="" class="text-green-500 ml-6">Docs</a>
          <a href="" class="text-green-500 ml-6">Forum</a>
        </div>
      </div>
    </nav>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
@tailwind base;

@tailwind components;

@tailwind utilities;

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
