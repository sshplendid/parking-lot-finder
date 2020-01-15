const console = window.console;
const getCoordinates = () => {
  return new Promise(function(resolve, reject) {
    navigator.geolocation.getCurrentPosition(resolve, reject);
  });
};

module.exports = {
  getDistance: (current, park) => {
    if(!Object.keys(current).includes('lat') || !Object.keys(current).includes('lng')) {
      return -1;
    }
    let latDiff = Math.abs(current.lat - park.lat);
    let lngDiff = Math.abs(current.lng - park.lng);
    return Math.sqrt(latDiff * latDiff + lngDiff * lngDiff);
  },
  getCurrentPosition: async () => {
    if(!navigator) {
      console.error('Not supported browser: navigator is undefined.');
      return undefined;
    }
    
    let position = await getCoordinates();
    console.log(`position: ${JSON.stringify(position)}`);
    return position;
  }
};