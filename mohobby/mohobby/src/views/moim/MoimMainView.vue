<template>
  <div>
    <MoimMain_Top @search="childValue" @category="catg"></MoimMain_Top>
    <MoimMainrecruit :rsearch="psearch" :keyWord="catgKey"></MoimMainrecruit><br>
    <MoimMainrecruit2></MoimMainrecruit2>
  </div>
</template>
<script>
import MoimMain_Top from "../../components/moim/Main/MoimMain_Top.vue";
import MoimMainrecruit from "../../components/moim/Main/MoimMain_recruit.vue";
import MoimMainrecruit2 from "../../components/moim/Main/MoimMain_recruit2.vue";

export default {
  components: { MoimMain_Top, MoimMainrecruit, MoimMainrecruit2 },
  data() {
    return {
      psearch : '',
      catgKey : '',
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    childValue(search) {
      this.psearch = search

    },
    catg(category) {
      console.log(this.catgKey)
      if(this.catgKey === category) {
        this.catgKey = ""
        this.psearch = ""
      } else {
        this.catgKey = category
      }
    }
  },
};
</script>

<style scoped></style>