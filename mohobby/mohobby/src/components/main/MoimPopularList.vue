<template>
  <v-container fluid>
    <v-sheet style="background-color: inherit;" max-width="2200">
      <v-slide-group class="pa-2">
        <v-slide-item v-for="moim in moimPopularList.data" :key="moim.index">
          <div class="displayflex">
            <v-card
              class="mx-auto"
              max-width="230px"
              @click="
                $router.push({
                  name: 'moimBoard',
                  params: { boardId: moim.moimId },
                })
              "
            >
              <v-img
                :src="require(`@/assets/image/moim/${moim.moimImg}`)"
                height="200px"
              ></v-img>

              <v-card-subtitle v-text="moim.moimName"></v-card-subtitle>
              <v-spacer />
              <v-chip outlined rounded text>
                {{ moim.moimRegion }}
              </v-chip>
              <v-chip outlined rounded text>
                {{ moim.moimCatg }}
              </v-chip>
              <v-card-actions>
                <v-btn color="orange lighten-2" text> 상세보기 </v-btn>

                <v-spacer></v-spacer>

                <v-btn icon @click.capture.stop="moim.show = !moim.show">
                  <v-icon>{{
                    moim.show ? "mdi-chevron-up" : "mdi-chevron-down"
                  }}</v-icon>
                </v-btn>
              </v-card-actions>

              <v-expand-transition>
                <div v-show="moim.show">
                  <v-divider></v-divider>

                  <v-card-text>
                    {{ moim.moimInfo }}
                  </v-card-text>
                </div>
              </v-expand-transition>
            </v-card>
          </div>
        </v-slide-item>
      </v-slide-group>
    </v-sheet>
  </v-container>
</template>
<script>
export default {
  props: {
    moimPopularList: {
      type: [],
      required: true,
    },
  },
  data() {
    return {
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
};
</script>
<style scoped>
.row {
  width: 100%;
}

.v-card {
  width: 100%;
}

.v-main {
  width: 100%;
}

.team {
  width: 100%;
}

.v-main__wrap {
  width: 100%;
}

.displayflex {
  display: inline-block;
  width: 270px;
  max-width: 100%;
}
</style>
