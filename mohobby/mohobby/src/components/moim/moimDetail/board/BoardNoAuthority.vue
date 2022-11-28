<template>
  <div>
    <div class="container">
    <v-container>
      <v-text-field
            label="글 내용 검색"
            single-line
            solo
          ></v-text-field>
    </v-container>
    </div>
    
    <v-card
    class="mx-auto mb-8"
    max-width="800"
    outlined
    v-for="item in items"
    :key="item.comment"
    @click=""
    >
     
    <v-list-item three-line>
      <v-list-item-avatar
        tile
        size="80"
        color="grey"
      ></v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-subtitle class="content mt-5">
        {{item.none}}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

      <v-card-actions>
          <v-spacer></v-spacer>
          <div class="text-overline mb-1 mr-2">댓글 {{item.comment}}</div>
      </v-card-actions>
  </v-card>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        items:[{
          comment: 21,
          none: "게시글은 소모임 멤버만 열람 가능합니다."  
        },
        {
          comment: 22,
          none: "게시글은 소모임 멤버만 열람 가능합니다."  
        },
        {
          comment: 23,
          none: "게시글은 소모임 멤버만 열람 가능합니다."  
        }]
      }
    },
    methods : {
    },
    props :{

    }
  }
</script>
<style scoped>
  .container{
    width: 80%;
  }
</style>