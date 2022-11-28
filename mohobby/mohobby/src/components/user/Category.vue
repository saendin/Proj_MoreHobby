<template>
    <main>
                    <v-row justify="space-around">
                      <v-col cols="12" sm="30" md="30">
                        <v-sheet class="py-4 px-1">
                          <v-slide-group class="pa-2">
                            <v-slide-item
                              v-for="catg in catgs"
                              :key="catg.keywordId"
                            >
                              <div class="displayflex">
                                <v-chip-group
                                  active-class="primary--text"
                                >
                                  <v-chip :value="catg.keywordId"
                                  @click="sendSelectCatg(catg.keywordName)"
                                  >
                                    {{ catg.keywordName }}
                                  </v-chip>
                                </v-chip-group>
                              </div>
                            </v-slide-item>
                          </v-slide-group>
                        </v-sheet>
                      </v-col>
                    </v-row>

    </main>
</template>
<script>
export default {
    components: {},
    data() {
        return {
            catgs: [],
            selectedCatg : "",
    }
},
    beforeCreate() {},
    created() {
         this.getAllCatg();
    },
    beforeMount() {},
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeUnmount() {},
    unmounted() {},
    methods: {
         // 관심사 전체 목록 가져오기
    getAllCatg() {
      const vm = this;
      this.axios({
        url: "http://localhost:8088/java/allCatg",
        method: "get",
      })
        .then(function (response) {
          console.log(response.data);
          if (response.data != "") {
            vm.catgs = response.data;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    sendSelectCatg(data) {
        console.log("자식 component" + data);
        this.$emit("sendSelectedCatg", data);
    }
    }
}
</script>