<template>
  <v-container>
    <v-row>
      <v-col class="text-center">
        <h1>MyNoteFolder</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="text-center">
        <v-btn
          class="mx-4"
          :to="{
            name: 'MyNote',
            params: {
              currId: this.$route.params.currId,
            }
          }"
        >
          New
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {};
</script>

<style scoped>
</style>