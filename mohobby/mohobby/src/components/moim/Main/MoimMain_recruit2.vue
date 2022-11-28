<template>
  <div class="container">
    <div class="box1">
      <h3>인기있는 소모임을 확인하세요!</h3>
    </div>
    <div class="box1">
      <h3 style="visibility: hidden">.</h3>
    </div>
    <div
      class="box"
      @click="box(idx)"
      v-for="(item, idx) in items"
      :key="item.moimId"
    >
      <v-card class="mx-3" max-width="550" outlined>
        <v-list-item three-line>
          <div style="display: none">
            {{ item.moimId }}
          </div>
          <v-list-item-content>
            <div class="text-overline mb-4">
              {{ item.moimName }}
            </div>
            <v-list-item-subtitle>{{ item.moimInfo }}</v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-avatar tile size="80" class="rounded">
            <img :src="require(`@/assets/image/moim/${item.moimImg}`)" />
          </v-list-item-avatar>
        </v-list-item>
        <v-card-actions>
          <span class="people"> {{ item.regCnt }}명 참여중 </span>
          <v-spacer></v-spacer>
          <v-chip outlined rounded text>
            {{ item.moimRegion }}
          </v-chip>
          <v-chip outlined rounded text>
            {{ item.moimCatg }}
          </v-chip>
        </v-card-actions>
      </v-card>
    </div>
    <div class="moreMoim"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async box(idx) {
      await this.getOneMoim(idx);
      if (this.moimOneInfo.moimOpen == 1) {
        this.$swal.fire("관리자에 의해 접근 금지된 모임입니다.");
      } else {
        this.$router.push({
          name: "moimBoard",
          params: { moimId: this.items[idx].moimId, boardType: 1 },
        });
      }
    },
    // 접근 유효성 검사할때 사용할 모임 단건 조회
    async getOneMoim(idx) {
      const vm = this;
      await this.axios
        .get("/moimOneInfo/" + this.items[idx].moimId, {})
        .then((resp) => {
          vm.moimOneInfo = resp.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getList() {
      this.axios
        .get("/moimPopularSelect")
        .then((resp) => {
          console.log(resp);
          this.items = resp.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.container {
  margin: 0 auto;
  width: 1000px;
}

.box {
  float: left;
  width: 50%;
  margin-bottom: 15px;
  cursor: pointer;
}

.box1 {
  float: left;
  width: 50%;
  margin-bottom: 15px;
}

.people {
  border-left: 2px solid rgb(34, 85, 177);
  padding-left: 10px;
}

.moreMoim {
  float: right;
  width: 1000px;
}
</style>