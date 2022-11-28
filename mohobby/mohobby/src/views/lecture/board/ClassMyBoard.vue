<template>
  <div>
    <div style="background-color: #F5F5F5; ">
    <div class="container">
    <v-card
      class="mx-auto mb-8"
      max-width="800"
      outlined
      v-for="(item, idx) in items"
      :key="idx"
      @click="goPost(idx)"
    >
      <v-list-item three-line>
        <v-list-item-avatar tile size="80" color="grey"></v-list-item-avatar>
        <v-list-item-content>
          <div class="text-overline mt-6">
            {{ item.memberId }}
            <hr />
            {{ item.writeDate | moment('YYYY-MM-DD HH:mm') }}
          </div>
          <v-list-item-subtitle class="content mt-5">
            {{ item.title }}
          </v-list-item-subtitle >
        </v-list-item-content>
      </v-list-item>
      <v-card-actions>
        <v-spacer></v-spacer>
        <div class="text-overline mb-1 mr-2">댓글 {{ item.cnt }}</div>
      </v-card-actions>
    </v-card>
    </div>
  </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            items: [
                {
                    memberId: 1,
                    writeDate: this.$moment(),
                    title: 'title',
                    cnt: 0,
                },
            ],
        }
    },

}
</script>

<style>

</style>