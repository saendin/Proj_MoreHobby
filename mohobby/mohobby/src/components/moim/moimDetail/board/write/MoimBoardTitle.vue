<template>
  <div class="container">
    <div class="flex">
      <v-chip class="ma-2 mt-4" text-color="white" color="black" label>목록</v-chip>
      <v-radio-group v-model="row" row>
      <v-radio class="ml-7" label="공지" value="radio-1"></v-radio>
      <v-radio label="메인글" value="radio-2"></v-radio>
    </v-radio-group>
    </div>
    <div class="flex">
      <v-chip class="ma-2 mt-4" text-color="white" color="black" label>제목</v-chip>
      <v-col cols="12" sm="6" md="11">
      <v-text-field class="ml-4" label="제목을 입력해주세요!" solo dense></v-text-field>
          </v-col>
    </div>
  </div>
</template>

<script>
    export default {
    data () {
      return {
        row: null,
      }
    },
  }

</script>

<style scoped>
  .container {
    width : 70%;
  }
  .flex {
    display : flex;
  }

</style>