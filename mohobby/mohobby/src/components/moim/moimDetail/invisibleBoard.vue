<template>
<div style="height: 700px">
<v-container fill-height fluid>
  <v-row>
    <v-card
      class="mx-auto"
      flat
      justify="center"
      align="center"
    >
    <v-card-text>
      <v-icon
          size="90"
          color="#2b2b2b"
          class="mb-3"
      >
      mdi-lock-open-alert
      </v-icon>
        <h2>
            {{contents}}
        </h2>
    </v-card-text>
    </v-card>
  </v-row>
</v-container>
</div>
</template>
<script>
export default {
  data() {
    return {
      contents: '소모임 멤버에게만 게시물이 공개됩니다.'
    };
  },
  methods: {

  }
};
</script>

<style scoped>
.content {
  opacity: 80%;
  margin-top: 20%;
}
</style>