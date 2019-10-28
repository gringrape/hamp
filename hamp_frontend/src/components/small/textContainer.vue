<template>
  <div :id="containerId">
    {{ text }}
  </div>
</template>

<script>
export default {
  name: 'textContainer',
  props: {
    containerId: String,
    text: String
  },
  mounted() {
    this.setText();
  },
  methods: {
    setText() {
      let element = document.getElementById(this.containerId);
      element.innerHTML = this.text;
    }
  }
}
</script>