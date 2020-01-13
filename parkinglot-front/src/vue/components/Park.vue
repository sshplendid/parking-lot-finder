<template>
  <div>
    <div>
      <span class="park-name">{{ parkingName }}</span>
      <span class="addr">{{ addr }}</span>
      <span class="tel">{{ tel }}</span>
      <span class="fee">{{ parkingFeePerHour }}</span>:w

    </div>
  </div>
</template>

<script>
export default {
  props: ['parkingName', 'addr', 'tel', 'parkingFeePerHour']
}
</script>

<style>

</style>