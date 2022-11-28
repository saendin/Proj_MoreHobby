<template>
  <div>
    <UserSidebar></UserSidebar>
    
    <div id="container" class="center">
      <div>
        <h2>내가 작성한 피드 목록</h2>
        <br />
        <br />
        <v-row>
            <!-- <v-slide-group class="pa-2">
        <v-slide-item
          v-for="post in postList"
          :key="post.postId"
        > -->
          <v-col v-for="post in postList" :key="post.postId" class="d-flex child-flex" cols="4">
            <v-img
              :src="require(`@/assets/image/sns/${post.postId}/${post.thumbnail}`)"
              aspect-ratio="1"
              class="grey lighten-2"
            >
              <template v-slot:placeholder>
                <v-row class="fill-height ma-0" align="center" justify="center">
                  <v-progress-circular
                    indeterminate
                    color="grey lighten-5"
                  ></v-progress-circular>
                </v-row>
              </template>
            </v-img>
          </v-col>
          <!-- </v-slide-item>
    </v-slide-group> -->


        </v-row>
      </div>
      <br />
      <br />
      <div>
        <h2>내가 좋아요 누른 피드 목록</h2>
        <br />
        <br />
        <v-row>
          <v-col v-for="jjimPost in jjimList" :key="jjimPost.postId" class="d-flex child-flex" cols="4">
            <v-img
              :src="require(`@/assets/image/sns/${jjimPost.postId}/${jjimPost.thumbnail}`)"
              aspect-ratio="1"
              class="grey lighten-2"
            >
              <template v-slot:placeholder>
                <v-row class="fill-height ma-0" align="center" justify="center">
                  <v-progress-circular
                    indeterminate
                    color="grey lighten-5"
                  ></v-progress-circular>
                </v-row>
              </template>
            </v-img>
          </v-col>
        </v-row>
      </div>
    </div>
  </div>
</template>
<script>
import UserSidebar from "../../components/user/UserSidebar.vue";

export default {
  name: "",
  components: { UserSidebar },
  data() {
    return {
      memberId: "",
      postList : [],
      jjimList : [],
    };
  },
  setup() {},
  created() {},
  mounted() {
    this.getPostList();
    this.getJjimList();
  },
  unmounted() {},
  methods: {
    getPostList() {
      const vm = this;
      this.memberId = this.$store.state.id;
      this.axios({
        url: "http://localhost:8088/java/mypageSnspost/" + vm.memberId,
        method: "get",
      })
        .then(function (response) {
          vm.postList = response.data;
          console.log(vm.postList);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getJjimList() {
      const vm = this;
      this.memberId = this.$store.state.id;
      this.axios({
        url: "http://localhost:8088/java/mypageSnsJjim/" + vm.memberId,
        method: "get",
      })
        .then(function (response) {
          vm.jjimList = response.data;
          console.log(vm.jjimList);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
#container {
  width: 1000px;
  height: 1500px;
  text-align: center;
}

.center {
  margin: auto;
  width: 50%;
  padding: 10px;
}
</style>
