<template>
  <div>
    <ClassSidebar></ClassSidebar>
    <h1></h1>
    <router-view />
  </div>
</template>
<script>
import ClassSidebar from "@/components/lecture/ClassSidebar.vue";

// highlight.js style
import "highlight.js/styles/tomorrow.css";

// import theme style

export default {
  components: {
    ClassSidebar,
  },
  data() {
    return {};
  },
  setup() {},
  created() {},
  mounted: function () {
    console.log("this is Quill instance:", this.editor);
  },
  unmounted() {},
  methods: {},
  computed: {},
};
</script>

<style scoped>
form {
  display: inline-block;
}
button {
  border: 1px solid gray;
}
input[type="submit"] {
  border: 1px solid gray;
}
</style>
