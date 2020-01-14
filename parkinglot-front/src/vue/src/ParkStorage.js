const fetch = require('node-fetch');
// const endpoint = this.document.location.origin;
const endpoint = 'http://localhost:8000';
const console = window.console;

function makeQueryString(obj) {
  return Object.keys(obj)
  .filter(key => obj[key])
  .map(key => `${key}=${obj[key]}`)
  .join('&');
}

module.exports = {
  loadAllParkInfoByAddress: async function(addr, sorter, page, pagesize, tel, parkingName) {
    console.log(`=> Trying to load park info. address: '${addr}'`);
    const queryString = makeQueryString({page, pagesize, tel, parkingName});
    const url = `${endpoint}/api/parks/${encodeURI(addr)}?${queryString}`;
    console.log(`url: ${url}`);
    const response = await fetch(url);
    console.log(`\tstatus: ${response.statusCode}`);
    const json = await response.json();
    console.log(`\tstatus: ${json.status}`);
    console.log(`\tdata size: ${json.size}`);
    
    console.log(`<= Done. Finished to load park info.`);
    if(json.data) {
      return json.data;
    }
    return [];
  }
};

/*
(async () => {
  var data = await module.exports.fetchAllParkInfoByAddress('망원');
  console.log(data);
})();
*/