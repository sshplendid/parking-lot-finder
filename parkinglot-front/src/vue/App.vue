<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <ListLayout 
      v-bind:search-keyword="searchKeyword"
      v-bind:park-list="filterdList"
      v-bind:top-func="topFunc"
    ></ListLayout>
  </div>
</template>

<script>
import ListLayout from './layouts/ListLayout';
import { loadAllParkInfoByAddress } from "./src/ParkStorage";
const console = window.console;
const defaultAddress = encodeURI('망원');
console.log(`default address: ${defaultAddress}`);

export default {
  name: 'app',
  components: {
    ListLayout
  },
  created () {
    loadAllParkInfoByAddress(defaultAddress)
    .then(responseData => {
      console.log('fetch done.');
      if(responseData) {
        this.parkList = responseData;
      }
      console.log('created hook done.');  
    });
/*
    const responseData = loadAllParkInfoByAddress(empty);
    console.log('fetch done.');
    if(responseData) {
      this.parkList = responseData;
    }
  console.log('created hook done.');
  */
  },
  methods: {
    topFunc: function (event) {
      console.log('this function is defined on top of component.');
      return event.computed.name;
    }
  },
  computed: {
    name: function() {
      return 'world';
    },
    filterdList: function() {
      if(!this.searchKeyword) {
        return this.parkList;
      }
      return this.parkList.filter(park => park.parkingName.includes(name));
    }
  },
  data: function() {
    return {
      searchKeyword: '',
      parkList: [
        {parkingCode: '123', parkingName: '망원 공영주차장', addr: '마포구 망원동 123', tel: '02-123-4567', parkingFeePerHour: 6000}, 
        {parkingCode: '223', parkingName: '합정 공영주차장', addr: '마포구 합정동 123', tel: '02-123-4567', parkingFeePerHour: 5000}, 
        {parkingCode: '323', parkingName: '강남 공영주차장', addr: '강남구 서초동 123', tel: '02-123-4567', parkingFeePerHour: 4000}, 
      ]
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
