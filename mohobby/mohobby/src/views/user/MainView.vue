<template>
  <div id="container">
    <div class="carousels">
      <v-carousel cycle>
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </div>
    <v-spacer />
    <h4>SNS인기글</h4>
    <v-container fluid>
      <v-row dense>
        <v-col v-for="card in cards" :key="card.title" :cols="card.flex">
          <v-card>
            <v-img
              :src="card.src"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <v-card-title v-text="card.title"></v-card-title>
            </v-img>

            <v-card-actions>
              <v-spacer></v-spacer>

              <v-btn icon>
                <v-icon>mdi-heart</v-icon>
              </v-btn>

              <v-btn icon>
                <v-icon>mdi-bookmark</v-icon>
              </v-btn>

              <v-btn icon>
                <v-icon>mdi-share-variant</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <h4>새로 생성된 모임</h4>
    <moimlist></moimlist>
    <h4>모집 중인 강의</h4>
    <moimlist></moimlist>
    <h4>챌린저스 후기</h4>
    <challReview></challReview>
    <p>{{ this.$store.state.id }}</p>
  </div>
</template>

<script>
import moimlist from "../../components/user/Moimlist.vue";
import challReview from "../../components/user/ChallReview.vue";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
export default {
  components: { moimlist, challReview },
  created() {
    if (this.$store.state.id !== "") this.connect();
  },
  methods: {
    connect() {
      console.log("test");
      const serverURL = " http://192.168.0.85:8088//java/sock";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        (frame) => {
          console.log("소켓 연결 성공", frame);
        },
        (error) => {
          console.log("소켓 연결 실패", error);
        }
      );
    },
  },
  data() {
    return {
      items: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
        },
      ],
      cards: [
        {
          title: "Pre-fab homes",
          src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
          flex: 2,
        },
        {
          title: "Favorite road trips",
          src: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
          flex: 2,
        },
        {
          title: "Best airlines",
          src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
          flex: 2,
        },
        {
          title: "Pre-fab homes1",
          src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
          flex: 2,
        },
        {
          title: "Favorite road trips2",
          src: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
          flex: 2,
        },
        {
          title: "Best airlines3",
          src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
          flex: 2,
        },
      ],
    };
  },
};
</script>

<style>
.container {
  width: 100%;
}
.carousels {
  position: relative;
  width: 100%;
}

.carousels .v-window {
  height: 400px !important;
  width: 100%;
}
</style>
