<template>
  <div class="container">
    <v-row>
      <v-col cols="12" sm="6" md="6">
        <div>
          <h4>내가 운영중인 소모임</h4>
          <v-sheet max-width="500">
            <v-slide-group class="pa-2">
              <v-slide-item v-for="item, idx in items" :key="idx">
                <v-card elevation="0" class="mr-5" max-width="160">
                  <div class="box" @click="box(idx)">
                    <v-list-item-avatar tile size="140" class="rounded">
                      <img :src="require(`@/assets/image/moim/${item.moimImg}`)" />
                    </v-list-item-avatar>
                  </div>
                  <div class="text-md-center font-weight-bold">
                    {{ item.moimName }}
                  </div>
                </v-card>
              </v-slide-item>
            </v-slide-group>
          </v-sheet>
        </div>
      </v-col>
      <!--두번째 슬라이드 -->
      <v-col cols="12" sm="6" md="6">
        <div>
          <h4>내가 참여중인 소모임</h4>
          <v-sheet  v-if="chamyeo.length != 0" max-width="500">
            <v-slide-group class="pa-2">
              <v-slide-item v-for="(item, index) in chamyeo" :key="index">
                <v-card elevation="0" class="mr-5" max-width="160">
                  <div class="box" @click="box2(index)">
                    <v-list-item-avatar tile size="140" class="rounded">
                      <img :src="require(`@/assets/image/moim/${item.moimImg}`)" /><br>
                    </v-list-item-avatar>
                  </div>
                  <div class="text-md-center font-weight-bold">
                    {{ item.moimName }}
                  </div>
                </v-card>
              </v-slide-item>
            </v-slide-group>
          </v-sheet>
          <v-sheet v-else>
            <div class="mt-16 text-md-center font-weight-bold">
              가입된 소모임이 없습니다.
            </div>
          </v-sheet>
        </div>
      </v-col>
    </v-row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      memberId: this.$store.state.id,
      items: [],
      chamyeo: [],
      moimOneInfo: [],
    }
  },
  methods: {
    operateMoim() {
      this.axios.get("/operateMoim", {
        params: {
          memberId: this.memberId
        }
      })
        .then((resp) => {
          console.log(resp)
          this.items = resp.data;
        })
        .catch((err) => {
          console.log(err)
        })
    },
    joimMoim() {
      this.axios.get("/joinMoim", {
        params: {
          memberId: this.memberId
        }
      })
        .then((resp) => {
          console.log(resp)
          this.chamyeo = resp.data;
        })
        .catch((err) => {
          console.log(err)
        })
    },
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
    box(idx) {
       this.getOneMoim(idx);
      if (this.moimOneInfo.moimOpen == 1) {
        this.$swal.fire("관리자에 의해 접근 금지된 모임입니다.");
      } else {
        this.$router.push({
          name: "moimBoard",
          params: { moimId: this.items[idx].moimId, moimName: this.items[idx].moimName, boardType: 1 },
        });
      }
    },
     box2(idx) {
       this.getOneMoim(idx);
      if (this.moimOneInfo.moimOpen == 1) {
        this.$swal.fire("관리자에 의해 접근 금지된 모임입니다.");
      } else {
        this.$router.push({
          name: "moimBoard",
          params: { moimId: this.chamyeo[idx].moimId, moimName: this.chamyeo[idx].moimName, boardType: 1 },
        });
      }
    }
  },
  created() {
    this.joimMoim(),
    this.operateMoim()
  }

}
</script>
<style scoped>
.container {
  display: flex;
}
.box {
  cursor: pointer;
}
.chamcheck {
  text-align: center;
}
</style>