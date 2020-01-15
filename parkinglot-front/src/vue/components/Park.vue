<template>
  <div class="list-group-item list-group-item-action">
      <div class="d-flex w-100 justify-content-between">
        <h5 class="mb-1">{{ parkingName }} </h5>
        <small v-if="hasDistance">{{distance}}</small>
        <small>&#8361;&nbsp;{{ parkingFeePerHour }}</small>
      </div>
      <p class="mb-1">{{ addr }} (Tel. {{ tel }})</p>
      <p class="mb-1" v-bind:class="{'available': parkable}"><b>{{ isParkable }} ({{curParking}}/{{capacity}})</b></p>
      <small v-bind:class="{'closed': isHoliday}">주간 운영시간: {{timeFormat(weekdayBeginTime)}} ~ {{timeFormat(weekdayEndTime)}}</small><br />
      <small v-bind:class="{'closed': !isHoliday}">주말 운영시간: {{timeFormat(weekendBeginTime)}} ~ {{timeFormat(weekendEndTime)}}</small>
  </div>
</template>

<script>
import { getDistance } from "../src/Geo";
const console = window.console;
export default {
  props: [
    'parkingName', 'addr', 'tel', 'parkingFeePerHour', 'parkable', 'capacity', 'curParking',
    'weekdayBeginTime', 'weekdayEndTime', 'weekendBeginTime', 'weekendEndTime',
    'lat', 'lng', 'currentPosition'
  ],
  computed: {
    isParkable: function() {
      return this.parkable? '주차가능':'주차불가';
    },
    isHoliday: function() {
      return [0, 6].includes((new Date()).getDay());
    },
    distance: function() {
      if(!this.currentPosition) {
        return -1;
      }
      console.log(`pos: ${JSON.stringify(this.currentPosition)}`);
      return  getDistance(this.currentPosition, {lat: this.lat, lng: this.lng});
    },
    hasDistance: function() {
      return this.distance > -1;
    }
  },
  methods: {
    timeFormat: function(time) {
      return time.substring(0, 2) + ':' + time.substring(2);
    }
  }
}
</script>

<style>
.closed {
  color: lightgray;
  text-decoration: line-through;
}

.available {
  color: green;
}
</style>