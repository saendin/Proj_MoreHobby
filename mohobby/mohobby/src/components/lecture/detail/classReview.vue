<template>
  <v-container fluid>
    <!-- 리뷰 내역 존재 -->
    <div v-if="reviewList.length != 0">
      <!-- 별점 통계 -->
      <div style="padding-bottom: 60px">
        <v-row>
          <v-card class="mx-auto" max-width="344" flat justify="center" align="center">
            <v-card-text>
              <h1 style="font-size: 4em; padding-top: 40px;">{{ rate.avg | toFixed(1) }}</h1>
              <div class="text-center" style="padding-top: 20px;">
                <v-rating empty-icon="mdi-star-outline" full-icon="mdi-star" half-icon="mdi-star-half-full"
                  half-increments length="5" :value="rate.avg" readonly large color="#ffcb02"
                  background-color="#ffcb02"></v-rating>
              </div>
              <h4>{{ classInfo.reviewTotal | comma }}개의 후기</h4>
            </v-card-text>
          </v-card>
          <v-divider vertical></v-divider>
          <v-card class="mx-auto" max-width="600" flat justify="center" align="center">
            <v-card-text>
              <v-row style="width: 400px" justisfy="center" align="center" v-for="i in 5" :key="i">
                <h3 style="padding-right: 5px;">{{ i }}</h3>
                <v-icon size="18" color="#ffcb02">mdi-star</v-icon>
                <v-col cols="10">
                  <v-progress-linear height="15" :value="rateList[i] | starRatio(classInfo.reviewTotal)"
                    color="#ffcb02">
                    <div style="width: 97%; text-align: right;">
                      <strong style="color: #888888; font-size: smaller;">{{ rateList[i] |
                      starRatio(classInfo.reviewTotal) | toFixed(1) }}%</strong>
                    </div>
                  </v-progress-linear>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-row>
      </div>
      <!-- 정렬 방식 -->
      <div>
        <v-card class="d-flex justify-end" flat tile>
          <v-card tile flat>
            <v-select :items="listStd" item-text="title" item-value="value"
              :menu-props="{ bottom: true, offsetY: true }" attach style="width: 160px;" v-model="defaultSelect" />
          </v-card>
        </v-card>
      </div>
      <!-- 리뷰 목록 -->
      <div style="padding-top: 20px" v-for="(rv,i) in reviewList" :key="i">
        <v-card class="mx-auto" outlined>
          <v-card-text>
            <v-row>
              <!-- 프로필 -->
              <v-avatar style="margin: 10px 0px 30px 5px;" color="grey darken-1" size="50">
                <!-- 이미지부분 -->
                <v-img aspect-ratio="30" :src="require(`@/assets/image/user/${rv.profileImg}`)" />
              </v-avatar>
              <v-col>
                <div style="padding-bottom: 3px;">
                  <span class="reviewTitle">{{ rv.title }}</span>
                  <span style="font-size: 1em;">{{ replaceDate(rv.writeDate) }}</span>
                </div>
                <span class="reviewNickname"
                  @click="$router.push({ path: '/snsUserFeed?memId='+rv.memberId}).catch(()=>{$router.go(0)})">
                  {{ rv.nickname }}</span>
                <div style="font-size: 1.2em; padding-top: 20px">{{ rv.content }}</div>
              </v-col>
              <v-rating empty-icon="mdi-star-outline" full-icon="mdi-star" half-icon="mdi-star-half-full"
                half-increments length="5" :value="rv.rate" readonly color="#ffcb02" background-color="#ffcb02"
                size="20"></v-rating>
            </v-row>
          </v-card-text>
        </v-card>
      </div>
    </div>
    <!-- 리뷰 내역 없음 -->
    <div v-if="reviewList.length == 0">
      <v-card flat justify="center" align="center" style="padding-top: 50px">
        <h1>🙇</h1>
        <h1>등록된 후기가 없습니다</h1>
      </v-card>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "classReview",
  props: {
    classId: {
      type: String,
      default: 1
    }
  },
  data() {
    return {
      classInfo: {},
      rate: {},
      rateList: [],
      reviewList: [],
      listStd: [
        { title: "최신순", value: "0" },
        { title: "별점순", value: "1" }
      ],
      defaultSelect: {
        value: "0"
      }
    };
  },
  created() {
    this.initRate();
    this.initInfo();
    this.getReviewList();
  },
  methods: {
    initRate() {
      this.axios("/class/review", {
        params: {
          classId: this.classId
        }
      })
        .then(res => (this.rate = res.data))
        .then(() => {
          this.rateList.push(this.rate.zero);
          this.rateList.push(this.rate.one);
          this.rateList.push(this.rate.two);
          this.rateList.push(this.rate.three);
          this.rateList.push(this.rate.four);
          this.rateList.push(this.rate.five);
        })
        .catch(err => console.log(err));
    },
    initInfo() {
      let p = this.$parent;
      if (typeof p == "undefined") {
        p.initInfo();
      }
      this.classInfo = p.$data.classInfo;
    },
    getReviewList() {
      this.axios("/class/board", {
        params: {
          classId: this.classId,
          boardType: 4
        }
      })
        .then(res => {
          if (res.data.length > 0) {
            this.reviewList = res.data;
          }
        })
        .catch(err => console.log(err));
    },
    replaceDate(date) {
      return this.$moment(date).fromNow();
    },
    selectSort() {
      let listSort = this.reviewList;
      if (this.defaultSelect == 0) {
        listSort.sort((a, b) => b.boardId - a.boardId);
      } else if (this.defaultSelect == 1) {
        listSort.sort((a, b) => b.rate - a.rate);
      }
      this.reviewList = listSort;
    }
  },
  watch: {
    defaultSelect: function () {
      this.selectSort();
    }
  }
};
</script>

<style scoped>
* {
  color: #2b2b2b;
}

.reviewTitle {
  font-weight: bold;
  font-size: 1.2em;
  padding-right: 8px;
}

.reviewNickname {
  font-size: 1em;
  cursor: pointer;
}
</style>