<template>
  <div class="">
    <div class="mx-auto text-center card shadow-md h-auto w-1/5 border-gray-400 border pb-4">
      <h1 class="mt-5 text-gray-900 font-bold text-2xl tracking-wider">
        로그인
      </h1>
      <div class="mt-10">
        <p class=" mr-48 font-bold text-sm text-gray-800">
          이메일 주소
        </p>
        <input class="mt-1 px-2 py-1 w-64 border border-gray-400 rounded-lg 
                      focus:shadow-outline focus:outline-none text-sm text-gray-800" 
               type="text">
      </div>
      <div class="mt-5"> 
        <p class=" mr-48 -ml-4 font-bold text-sm text-gray-800">
          비밀번호
        </p>
        <input class="mt-1 px-2 py-1 w-64 border border-gray-400 rounded-lg 
                      focus:shadow-outline focus:outline-none text-sm text-gray-800" 
               type="password">
      </div>
      <button class="bg-pink-600 rounded-lg py-2 px-10 text-white text-sm w-64
                     font-bold mt-8 mb-5 hover:bg-pink-400 tracking-widest">
        로그인
      </button>
    </div>
    
  </div>
</template>

<script>
export default {
  name: "LoginForm"
}
</script>