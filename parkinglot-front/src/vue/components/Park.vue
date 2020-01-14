<template>
  <div class="list-group-item list-group-item-action">
      <div class="d-flex w-100 justify-content-between">
        <h5 class="mb-1">{{ parkingName }}</h5>
        <small>&#8361;&nbsp;{{ parkingFeePerHour }}</small>
      </div>
      <p class="mb-1"><b>{{ isParkable }}</b></p>
      <small>{{ addr }} (Tel. {{ tel }})</small>
  </div>
</template>

<script>
export default {
  props: ['parkingName', 'addr', 'tel', 'parkingFeePerHour', 'parkable'],
  computed: {
    isParkable: function() {
      return this.parkable? '주차가능':'주차불가';
    }
  }
}
</script>

<style>

</style>