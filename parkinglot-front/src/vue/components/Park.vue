<template>
  <div class="list-group-item list-group-item-action">
      <div class="d-flex w-100 justify-content-between">
        <h5 class="mb-1">{{ parkingName }} </h5>
        <small v-if="hasDistance">약 {{distance}} km</small>
        <small>&#8361;&nbsp;{{ parkingFeePerHour }}</small>
      </div>
      <p class="mb-1">{{ addr }} (Tel. {{ tel }})</p>
      <p class="mb-1" v-bind:class="{'available': parkable}"><b>{{ isParkable }} ({{curParking}}/{{capacity}})</b></p>
      <small v-bind:class="{'closed': isHoliday}">주간 운영시간: {{timeFormat(weekdayBeginTime)}} ~ {{timeFormat(weekdayEndTime)}}</small><br />
      <small v-bind:class="{'closed': !isHoliday}">주말 운영시간: {{timeFormat(weekendBeginTime)}} ~ {{timeFormat(weekendEndTime)}}</small>
  </div>
</template>

<script>
const calculateDistance = (lat1, lng1, lat2, lng2, unit) => {
	if ((lat1 == lat2) && (lng1 == lng2)) {
		return 0;
	} else {
		var radlat1 = Math.PI * lat1/180;
		var radlat2 = Math.PI * lat2/180;
		var theta = lng1-lng2;
		var radtheta = Math.PI * theta/180;
		var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
		if (dist > 1) {
			dist = 1;
		}
		dist = Math.acos(dist);
		dist = dist * 180/Math.PI;
		dist = dist * 60 * 1.1515;
		if (unit=="K") { dist = dist * 1.609344 }
		if (unit=="N") { dist = dist * 0.8684 }
		return dist;
	}
};
// const console = window.console;

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
      return Number((calculateDistance(this.lat, this.lng, this.currentPosition.lat, this.currentPosition.lng, 'K').toFixed(1)));
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