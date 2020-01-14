<template>
  <div id="app" class="container-sm">
    <h1>주차장 검색</h1>
    <div class="container">
      <form>
        <div class="form-row">
          <div class="col-7 mb-3">
            <label for="validationDefault01">주소</label>
            <input type="text" class="form-control" id="validationDefault01"
              placeholder="주소를 입력하세요. (e.g. 마포구, 망원동)" 
              v-model="address"
            required>
          </div>
          <div class="col-5 mb-3">
            <label for="validationDefault04">정렬조건</label>
            <select v-model="sorter" class="custom-select" id="validationDefault04" required>
              <option value="fee">주차비용</option>
              <option value="distance">거리</option>
            </select>
          </div>
        </div>
        <div class="form-row">
          <div class="col-4 mb-3">
            <label for="validationDefault01">주차장 이름</label>
            <input type="text" class="form-control" id="validationDefault01"
              placeholder="e.g. 공영주차장" 
              v-model="parkingName">
          </div>
          <div class="col-4 mb-3">
            <label for="validationDefault04">전화번호</label>
            <input type="tel" class="form-control" id="validationDefault01"
              pattern="[0-9]{2}-[0-9]{3}-[0-9]{4}"
              placeholder="02-200-1234"
              v-model="tel">
          </div>
          <div class="col-4 mb-3">
            <label for="validationDefault04">페이징</label>
            <select v-model="pageSize" class="custom-select" id="validationDefault04" required>
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="20">20</option>
            </select>
          </div>
        </div>
        <button v-on:click="refreshParkInfo" class="btn btn-primary">검색</button>
      </form>
    </div>
    <div class="container">
      <div v-if="notFound" class="list-group">
        <NotFound></NotFound>
      </div>
      <div v-else class="list-group">
        <Park v-for="park in computedParkInfo" :key="park.uniqueKey"
            v-bind:parking-name="park.parkingName"
            v-bind:addr="park.addr"
            v-bind:tel="park.tel"
            v-bind:parking-fee-per-hour="park.parkingFeePerHour"
            v-bind:parkable="park.parkable"
        ></Park>
        <nav aria-label="Page navigation example">
          <ul class="pagination justify-content-center">
            <li class="page-item" v-bind:class="{'is-first-page': disabled}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true" v-on:click="previousPage">Previous</a>
            </li>
            <li class="page-item"  v-bind:class="{'is-last-page': disabled}">
              <a class="page-link" href="#" v-on:click="nextPage">Next</a>
            </li>
          </ul>
        </nav>
      </div>
    </div>

  </div>
</template>

<script>
import Park from './components/Park';
import NotFound from './components/NotFound';
import { loadAllParkInfoByAddress } from "./src/ParkStorage";
const console = window.console;

const loadParks = function(address, sorter, page, pageSize, tel, parkingName, callback) {
  loadAllParkInfoByAddress(address, sorter, page, pageSize, tel, parkingName)
    .then(responseData => {
      console.log('fetch done.');
      if(responseData) {
        callback(responseData);
      }
    });
};

export default {
  name: 'app',
  components: {
    Park, NotFound
  },
  created () {
    const address = '마포'
    loadParks(address, 'fee', 1, 5, undefined, undefined, (data) => {
      this.parkList = data;
      console.log('created hook done.');  
    });
  },
  computed: {
    notFound: function() {
      return this.parkList.length == 0 ? true : false;
    },
    computedParkInfo: function() {
      return this.parkList;
    },
    isFirstPage: function() {
      return this.page == 1;
    },
    isLastPage: function() {
      return this.pageSize > this.parkList.length;
    }
  },
  methods: {
    refreshParkInfo: function() {
      this.page = 1;
      this.loadParkInfo();
    },
    loadParkInfo: function() {
      loadParks(this.address, this.sorter, this.page, this.pageSize, this.tel, this.parkingName, (data) => {
        this.parkList = data;
        console.log('Completed to refresh park info.');
      });
    },
    nextPage: function() {
      if(this.isLastPage) {
        console.error('마지막 페이지입니다.');
        return;
      }
      this.page = this.page + 1;
      this.loadParkInfo();
    },
    previousPage: function() {
      if(this.isFirstPage) {
        console.error('첫 페이지입니다.');
        return;
      }
      this.page = this.page - 1;
      this.loadParkInfo();
    }
  },
  data: function() {
    return {
      address: '마포',
      sorter: 'fee',
      tel: '',
      parkingName: '',
      page: 1,
      pageSize: 5,
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
