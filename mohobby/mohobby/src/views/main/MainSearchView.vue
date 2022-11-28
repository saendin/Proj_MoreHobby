<template>
    <main>
      <div class="container">
      <v-col lg="4" cols="12" style="margin-left: 100px; width: 100%;"></v-col>
      <v-form class="mt-5">
          <h3>검색된 sns</h3>
          <MainSnsList :snsHighLikesList="searchSnsList"></MainSnsList>
          <h3>검색된 모임</h3>
          <moimPopularList :moimPopularList="searchMoimList"></moimPopularList>
          <h3>검색된 클래스</h3>
          <collectClassList :collectClassList="searchClassList"></collectClassList>
      </v-form>
    </div>  
    </main>
</template>
<script>
import collectClassList from "../../components/main/CollectClassList.vue";
import moimPopularList from "../../components/main/MoimPopularList.vue";
import snsHighLikesList from "../../components/main/SnsHighLikesList.vue";
import MainSnsList from "../../components/main/MainSnsList.vue";

export default {
    components: {
        moimPopularList,
        snsHighLikesList,
        collectClassList,
        MainSnsList
  },
    data() {
        return {
          searchText : "",
          searchSnsList : [],
          searchMoimList : [],
          searchClassList : [],
    }
},
    beforeCreate() {
    },
    created() {
      this.searchText = this.$route.query.searchText;
      this.getSearchSnsList();
      this.getSearchMoimList();
      this.getSearchClassList();
    },
    beforeMount() {},
    mounted() {
    },
    beforeUpdate() {},
    updated() {},
    beforeUnmount() {},
    unmounted() {},
    methods: {
        getSearchSnsList() {
          const vm = this;
          this.axios({
            url: "/searchSnskeyword/" + this.searchText,
            method: "get",
          })
            .then(function (response) {
              vm.searchSnsList = response;
            })
            .catch(function (error) {
              console.log(error);
            });
        },
        getSearchMoimList() {
          const vm = this;
          this.axios({
            url: "/searchMoimkeyword/" + this.searchText,
            method: "get",
          })
            .then(function (response) {
              for(let i = 0; i < response.data.length; i++) {
                response.data[i].show = false;
              }
              vm.searchMoimList = response;
            })
            .catch(function (error) {
              console.log(error);
            });
        },
        getSearchClassList() {
          const vm = this;
          this.axios({
            url: "/searchClassKeyword/" + this.searchText,
            method: "get",
          })
            .then(function (response) {
              vm.searchClassList = response;
            })
            .catch(function (error) {
              console.log(error);
            });
        }
    }
}
</script>
<style scoped>
/* .v-input {
    margin-left: 30px;
} */

.container {
  width: 1500px;

}
</style>