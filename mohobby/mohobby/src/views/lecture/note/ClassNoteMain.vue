<template>
  <div>

      <BreadCrumb />
      <router-view></router-view>
  </div>
</template>

<script>
import Vue from "vue";
import BreadCrumb from "@/components/BreadCrumb.vue";
export default Vue.extend({
  name: "App",
  components: { BreadCrumb },
  data: () => ({
    //
  }),
});
</script>