import Vue from "vue";
import App from "./App.vue";

Vue.config.productionTip = false;

import vuetify from "./plugins/vuetify";
import router from "./router";
import axios from "axios";
import $ from "jquery";
import ImageViewer from "vue2-viewer";
import store from "./store";
import vueMoment from "vue-moment";
import moment from "moment";
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { VueSpinners } from "@saeris/vue-spinners";


window.Kakao.init("157b38874395f658a48c02cc8473066b"); // 카카오 로그인 앱 키

axios.defaults.baseURL = "http://localhost:8088/java";

const serverURL = "http://localhost:8088/java/sock";
let socket = new SockJS(serverURL);
let stompClient=Stomp.over(socket);

const url = "http://localhost:8081/";
Vue.prototype.$url = url;

Vue.prototype.stompClient = stompClient;

moment.locale("ko");



Vue.use(VueSpinners);
Vue.use(VueSweetalert2);

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

Vue.use(vueMoment, { moment });

Vue.use(ImageViewer);
Vue.config.productionTip = false;
Vue.prototype.axios = axios;
Vue.prototype.$ = $;
moment.locale('ko');
// console.warn = console.error = () => {};
// or IIFE
// (() => { console.warn = console.error = () => {}} )();

Vue.filter("toFixed", (val, num) => {
  let result;
  if(isNaN(val)) {
    return 0;
  } else if (val % 1 == 0) {
    result = val;
  } else if ( val % 1 * 10 % 1 == 0 ) {
    result = val;
  } else {
    result = parseFloat(val), toFixed(num);
  }
  return result;
});
Vue.filter("comma", (val) => {
  return String(Math.round(val)).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
});
Vue.filter("won", (val) => {
  return `${val} 원`;
});
Vue.filter("idx", (val) => {
  if(val < 10) {
    return "0"+val;
  }
  return val;
});
Vue.filter("runtime", (val) => {
  if(val < 10) {
    return "00:0"+val;
  }else if(val <= 60) {
    return "00:"+val;
  }

  let m = (parseInt(val / 60)).toString();
  let s = (parseInt(val % 60)).toString();

  if (s.includes('.')) {
    s = s.substring(0, s.indexOf('.'));
  }

  let mm = m;
  let ss = s;
  if(m<10){
    mm = "0"+m;
  }
  if(s<10){
    ss = "0"+s;
  }

  return mm+":"+ss;
});
Vue.filter("starRatio", (val, total) => {
  return (val / total) * 100;
})
Vue.filter("substr", (val, idx) => {
  if(val.length <= idx) return val;

  return val.substr(0, idx) + '...';
})



/*
* Quill-Editor
*/
// highlight.js style
import "highlight.js/styles/tomorrow.css";

// import theme style
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";

import VueQuill, {Quill} from 'vue-quill-editor'
window.Quill = Quill;

import { ImageDrop } from "quill-image-drop-module";
const ImagePaste = require("quill-yang-image-paste");
const ImageResize = require( 'quill-image-resize-module' );

Quill.register("modules/imageDrop", ImageDrop);
Quill.register("modules/imagePaste", ImagePaste);
Quill.register('modules/ImageResize', ImageResize);

Vue.use(VueQuill, {
  placeholder: "내용을 입력하세요.",
  modules: {
    //imageDrop: true,
    imagePaste: true,
    toolbar: [
      ["bold", "italic", "underline", "strike"], // <strong>, <em>, <u>, <s>
      [{ header: 1 }, { header: 2 }], // <h1>, <h2>
      [{ size: ["small", false, "large", "huge"] }], //class 제어 - html로 되도록 확인
      [{ header: [1, 2, 3, 4, 5, 6, false] }], // <h1>, <h2>, <h3>, <h4>, <h5>, <h6>, normal
      [{ font: [] }], // 글꼴 class로 제어
      [{ color: [] }, { background: [] }], //style="color: rgb(230, 0, 0);", style="background-color: rgb(230, 0, 0);"
      ["image"]
    ],
    imageResize: {
      displayStyles: {
        backgroundColor: "black",
        border: "none",
        color: "white"
      },
      modules: ["Resize", "DisplaySize", "Toolbar"]
    }
  }
});
Vue.component("quill-editor", VueQuill.quillEditor);



/* eslint-disable no-new */
new Vue({
  vuetify,
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");

