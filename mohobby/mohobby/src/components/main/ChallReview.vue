<template>
 <div class="team">
     <v-container fluid grid-list-xl>
      <v-layout wrap justify-space-around>
         <v-flex v-for="person in team" :key="person.role">
  <v-card
    class="mx-auto"
  >
    <v-card-text>
      <h2>{{ person.name }}</h2>
      <br>
      <textarea name="" id="" rows="6" readonly>
            가나다라마바사 아자차카타파하 가나다라마바사 아자차카타파하
      </textarea>
    </v-card-text>
    <v-card-actions>
     <v-col cols="14" align="center">
      <v-btn>
        챌린지 바로가기
      </v-btn>
     </v-col>
    </v-card-actions>
  </v-card>
         </v-flex>
      </v-layout>
     </v-container>
 </div>
</template>

<script>
  export default {
      data() {
    return {
      team : [
      { name : 'The net ninja', role : 'web developer'},
      { name : 'Rye', role : 'Graphic designer'},
      { name : 'Chun Li', role : 'web developer'},
      { name : 'Gouken', role : 'social media maverick'},
      { name : 'Yoshi', role : 'sales guru'},
      { name : 'Yoshi', role : 'sales guru'},
      { name : 'Yoshi', role : 'sales guru'},
      { name : 'Yoshi', role : 'sales guru'},
    ],
    show : false,
  }
  },
  }
</script>
<style scoped>
textarea {
    font-size: 20px;
    width: 250px;
    line-height: 20px;
}

.row {
  width: 100%,
  
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
  width : 100%;
}
</style>