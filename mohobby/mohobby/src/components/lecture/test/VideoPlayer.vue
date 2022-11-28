<template id="learn-page-box">
    <Artplayer @get-instance="getInstance" :option="option" :style="style" />
</template>
  
<script>
import Artplayer from "artplayer/examples/vue/Artplayer";
  
  export default {
    data() {
      return {
        option: {
          url: require('@/assets/video/class/curriculum/1.mp4'),
          volume: 0.5,
          isLive: false,
          muted: false,
          autoplay: false,
          pip: false,
          autoSize: true,
          autoMini: true,
          screenshot: true,
          setting: true,
          loop: false,
          rotate: true,
          playbackRate: true,
          fullscreen: true,
          fullscreenWeb: true,
          localVideo: true,
          autoPlayback: true,
          theme: '#ffad00',
          quality: [
            {
              url: require('@/assets/video/class/curriculum/1.mp4'),
              html: 'SD 480P',
            },
            {
              url: require('@/assets/video/class/curriculum/1.mp4'),
              default: true,
              html: 'HD 720P',
            },
            {
              url: require('@/assets/video/class/curriculum/1.mp4'),
              html: 'HD 1080P',
            },
          ],
          controls: [
            {
              position: 'right',
              html: 'Control',
              tooltip: 'Control Tooltip',
              click: function () {
                  console.log('You clicked on the custom control');
                  console.log(document.querySelector(".art-video").currentTime);
              },
            },
          ],
          highlight: [
            {
              time: 15,
              text: 'One more chance',
            },
            {
              time: 30,
              text: '谁でもいいはずなのに',
            },
            {
              time: 45,
              text: '夏の想い出がまわる',
            },
            {
              time: 60,
              text: 'こんなとこにあるはずもないのに',
            },
            {
              time: 75,
              text: '终わり',
            },
          ],
          icons: {
            loading: '',
            state: '',
            indicator: '',
          },
        },
        style: {
          width: "720px",
          height: "480px",
          margin: "60px auto 0",
        },

      };
    },
    components: {
      Artplayer,
    },
    methods: {
      getInstance(art) {
        console.log(art);
      },
    },
    watch: {

    }
  };
</script>
<style scoped>
 
</style>