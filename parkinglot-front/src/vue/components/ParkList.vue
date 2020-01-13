<template>
  <div>
    <Park v-for="park in parkList" :key="park.uniqueKey"
        v-bind:parking-name="park.parkingName"
        v-bind:addr="park.addr"
        v-bind:tel="park.tel"
        v-bind:parking-fee-per-hour="park.parkingFeePerHour"
    ></Park>
  </div>
</template>

<script>
import Park from "./Park";
export default {
  props: ['parkList'],
  components: {
    Park
  }
}
</script>

<style>

</style>