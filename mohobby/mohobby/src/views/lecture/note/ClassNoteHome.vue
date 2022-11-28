<template>
  <v-container>
    <v-row>
      <v-col>
        <h1>강의를 선택하세요</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <ul>
          <li>
            <v-btn
              :to="{
                name: 'MyNoteFolder',
                params: {
                  currId: 1,
                }
              }"
            >1</v-btn>
          </li>
          <li>
            <v-btn
              :to="{
                name: 'MyNoteFolder',
                params: {
                  currId: 2,
                },
              }"
            >2</v-btn>
          </li>
          <li>
            <v-btn
              :to="{
                name: 'MyNoteFolder',
                params: {
                  currId: 3,
                },
              }"
            >3</v-btn>
          </li>
          <li>
            <v-btn
              :to="{
                name: 'MyNoteFolder',
                params: {
                  currId: 4,
                },
              }"
            >4</v-btn>
          </li>
        </ul>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Vue from "vue";
export default Vue.extend({
  name: "MyNoteHome",
  data() {
    return{
      info: {},
    }
  },
});
</script>

<style scoped>
li {
  list-style: none;
}
</style>