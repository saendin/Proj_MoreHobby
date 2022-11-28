<template>
  <v-container fluid>
    <v-sheet style="background-color: inherit;" max-width="2200">
      <v-slide-group class="pa-2">
        <v-slide-item
          v-for="(item, i) in items"
          :key="i"
          class="mr-5"
        >
          <div class="displayflex">
            <v-card
              class="ma-10"
              max-width="320px"
              @click.stop="goDetail(item)"
            >
            <v-badge
                overlap
                left
                offset-x="90"
                offset-y="45"
                :content="item.classType === 0 ? 'ONLINE' : 'OFFLINE'"
                :color="item.classType === 0 ? '#2255b1' : '#F36A3E'"
                style="padding: 15px"
            >
              <div>

                <v-img
                  :src="require(`@/assets/image/class/thumb/${item.classId}/0.jpg`)"
                  max-width="290"
                  min-height="180"
                  max-height="180"
                ></v-img>
              </div>
            </v-badge>

              <v-card-subtitle v-text="item.className"></v-card-subtitle>
              <v-spacer />
              <!-- <v-chip outlined rounded text>
                {{ item.keywordName }}
              </v-chip> -->
              <v-card-subtitle 
                class="d-flex justify-center text-h6"
              >
                수강료 {{ item.classPrice | comma | won }}
              </v-card-subtitle>
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
  },
  data() {
    return {
      show: false,
      items: [],
    };
  },
  setup() {},
  created() {
    this.getItems();
  },
  mounted() {},
  unmounted() {},
  methods: {
    getItems() {
      this.axios('/class/on').then(res => {
        this.items = res.data;
      })
    },
    goDetail(item) {
        if(item.classType === 0) {
            this.$router.push({ path: '/class/on/'+item.classId+'/info', }).catch(()=>{$router.go(0)});
        } else if(item.classType === 1) {
            this.$router.push({ path: '/class/off/'+item.classId+'/info', }).catch(()=>{$router.go(0)});
        }
    }
  },
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
  width: 320px;
  max-width: 100%;
}
</style>
