<template>
  <div>
    <input placeholder="주소를 입력하세요. (e.g. 마포구, 망원동)" 
      v-model="searchKeyword"
    />
    <button v-on:click="topFunc">검색</button>
  </div>
</template>

<script>
var console = window.console;
export default {
  props: ['searchKeyword', 'topFunc'],
  methods: {
    submit: function() {
      console.log('button clicked!');
    }
  }
}
</script>

<style>

</style>