<template>
  <div id="container">
    <div class="carousels">
      <v-carousel hide-delimiters cycle>
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
        ></v-carousel-item>
      </v-carousel>
    </div>
    <v-img
      :src="require('@/assets/image/web/back-1.jpg')"
      max-height="900"
    >
      <v-container fluid class="mt-10">
        <div class="d-flex justify-center">
          <v-chip :ripple="false" class="pa-4" large dark color="#2255b1">
            <h1 style="font-weight: bolder;">Best Feed in SNS</h1>
          </v-chip>
        </div>
        <MainSnsList :snsHighLikesList="snsHighLikesList"></MainSnsList>
      </v-container>
    </v-img>
    <v-img
      :src="require('@/assets/image/web/back-2.jpg')"
    />
    <v-img
      :src="require('@/assets/image/web/back-3.jpg')"
      max-height="700"
    >
      <div class="d-flex justify-center pb-10">
        <v-chip :ripple="false" class="pa-4" large dark color="#2255b1">
          <h1 style="font-weight: bolder;">Best CLUB</h1>
        </v-chip>
      </div>
      <moimPopularList :moimPopularList="moimPopularList"></moimPopularList>
    </v-img>
    <v-img
      :src="require('@/assets/image/web/back-4.jpg')"
    />
    <v-img
      :src="require('@/assets/image/web/back-5.jpg')"
      max-height="700"
    >
      <div class="d-flex justify-center">
        <v-chip :ripple="false" class="pa-4" large dark color="#2255b1">
          <h1 style="font-weight: bolder;">Take a CLASS</h1>
        </v-chip>
      </div>
      <collectClassList :collectClassList="collectClassList"></collectClassList>
    </v-img>
    <v-img
      :src="require('@/assets/image/web/footer.jpg')"
    />
  </div>
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
      items: [
        {
          src: "https://image.idus.com/image/files/945cc30fe6474be4aa0595b0fa10c663.jpg",
        },
        {
          src: "https://image.idus.com/image/files/4e22bce11c114e84a139e9625bced2dd.jpg",
        },
        {
          src: "https://image.idus.com/image/files/980f79a053774b1fb38c3200f73c4152.jpg",
        },
        {
          src: "https://image.idus.com/image/files/313d51459f094114afdb70d40e777cb8.jpg",
        },
      ],
      snsHighLikesList: [],
      moimPopularList: [],
      collectClassList: [],
    };
  },
  setup() {},
  created() {},
  mounted() {
    this.getSnsList();
    this.getMoimList();
    this.getClassList();
  },
  unmounted() {},
  methods: {
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

    getMoimList() {
      const vm = this;
      this.axios({
        url: "/mainmoimlist",
        method: "get",
      })
        .then(function (response) {
          for(let i = 0; i < response.data.length; i++) {
            response.data[i].show = false;
          }
          vm.moimPopularList = response;
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    getClassList() {
      const vm = this;
      this.axios({
        url: "/mainclasslist",
        method: "get",
      })
        .then(function (response) {
          vm.collectClassList = response;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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
