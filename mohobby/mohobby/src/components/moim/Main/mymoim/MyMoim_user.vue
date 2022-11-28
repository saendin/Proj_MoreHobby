<template>
  <div>
    <div>
      <h3>내가 참여중인 소모임</h3>
      <v-sheet max-width="1000">
        <v-slide-group class="pa-2">
          <v-slide-item v-for="(item,idx) in items" :key="idx">
            <v-card elevation="0"  class="mr-5" max-width="160">
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
  </div>
</template>
<script>
export default {
  data() {
    return {
      memberId: this.$store.state.id,
      items: [],
      moimOneInfo: [],
    }
  },
  methods: {
    joimMoim() {
      this.axios.get("/joinMoim", {
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
  },
  created() {
    this.joimMoim()
  }
}

</script>
<style scoped>
.box {
  margin-right: 25px;
  cursor: pointer;
}
</style>