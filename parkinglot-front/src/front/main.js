import Vue from "vue";
import App from "./App.vue"


Vue.use(VueMaterial);

new Vue({
    el: '#main',
    render: h => h(App)
});