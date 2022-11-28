<template>
  <main>
    <AdminSidebar></AdminSidebar>
    <h3>TOP10해시태그</h3>
    <v-row justify="space-around">
      <v-col cols="12" sm="30" md="30">
        <v-sheet class="py-4 px-1">
          <v-slide-group class="pa-2">
            <v-slide-item
              v-for="(hashtagData, index) in hashtagList"
              :key="index"
            >
              <div class="displayflex">
                <v-chip-group active-class="primary--text">
                  <v-chip :value="hashtagData.hashtag" @click="search()">
                    {{ hashtagData.hashtag }}
                  </v-chip>
                </v-chip-group>
              </div>
            </v-slide-item>
          </v-slide-group>
        </v-sheet>
      </v-col>
    </v-row>
    <h3>인기 SNS피드</h3>
    <MainSnsList :snsHighLikesList="snsHighLikesList"></MainSnsList>
  </main>
</template>
s
<script>
import AdminSidebar from "../../components/admin/AdminSidebar.vue";
import snsHighLikesList from "../../components/main/SnsHighLikesList.vue";
import MainSnsList from "../../components/main/MainSnsList.vue";

export default {
  name: "",
  components: { AdminSidebar, snsHighLikesList, MainSnsList},
  data() {
    return {
      hashtagList: [],
      snsHighLikesList: [],
    };
  },
  beforeCreate() {},
  created() {
    this.getHashtags();
    this.getSnsList();
  },
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    search() {},
    getHashtags() {
      const vm = this;
      this.axios({
        url: "/adminHashtag",
        method: "get",
      })
        .then(function (response) {
          console.log(response);
          vm.hashtagList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getSnsList() {
      const vm = this;
      this.axios({
        url: "/mainsnslist",
        method: "get",
      })
        .then(function (response) {
          vm.snsHighLikesList = response;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>
<style scoped>
.py-2 {
  display: inline-block;
  width: 100px;
  margin: 7px;
}
</style>
