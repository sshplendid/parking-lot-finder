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
  loadAllParkInfoByAddress: async function(addr, page, pageSize, tel, parkingName) {
    console.log(`=> Trying to load park info. address: '${addr}'`);
    const queryString = makeQueryString({page, pageSize, tel, parkingName});
    const url = `${endpoint}/api/parks/${encodeURI(addr)}?${queryString}`;
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