<template>
  <div>
    <div class="search-tab">
      <SearchBox
        v-bind:search-keyword="searchKeyword"
        v-bind:top-func="topFunc"
      ></SearchBox>
    </div>
    <div>
      <ParkList v-bind:park-list="parkList"></ParkList>
    </div>
  </div>
</template>

<script>
import SearchBox from "../components/SearchBox";
import ParkList from "../components/ParkList";
export default {
  props: ['searchKeyword', 'parkList', 'topFunc'],
  components: {
    SearchBox, ParkList
  }

}
</script>

<style>

</style>