<template>
  <v-container>
    <v-row>
      <v-col class="text-center">
        <h1>Page</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        {{ this.$route.params.currId }}
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {};
</script>

<style scoped>

</style>