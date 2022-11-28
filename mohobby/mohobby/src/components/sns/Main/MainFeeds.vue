<!-- none_user random postlist component in the list -->
<template>
  <div>
    <v-container class="gallery" 
                 max-width="1400" 
                 fluid>
      <v-row>
        <v-col class="gallery-item" 
               v-for="(feed,i) in feeds" :key="i" cols="3">

          <div @click="goFeedDetail(feed.postId, feed.memberId)">

            <v-img :src="require(`@/assets/image/sns/${feed.postId}/${feed.thumbnail}`)"
                    class="white--text align-end rounded-sm" 
                    aspect-ratio="1.2"
                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)">

            <!-- class="d-flex child-flex" -->
            <v-icon v-show="feed.imgs > 1" 
                     class="image_icon">
                     mdi-checkbox-multiple-blank
            </v-icon>
                <span id="user_id" 
                      class="ml-3 mb-2">
                      {{ feed.memberId }}
                </span>
                <div class="gallery-item-info">
                  <ul>
                    <li class="gallery-item-likes">
                      <v-icon color="#bdbdbd" class="mr-2">
                        mdi-heart
                      </v-icon>
                      {{feed.likes}}
                    </li>
                    <li class="gallery-item-comments">
                      <v-icon color="#e1e1e1" class="mr-2">
                        mdi-chat-outline
                      </v-icon>
                      {{feed.cmts}}
                      </li>
                  </ul>
                </div>

              <template v-slot:placeholder>
                <v-row class="fill-height ma-0 text-center align-center" 
                       justify="center">
                  <v-progress-circular 
                       indeterminate 
                       color="grey lighten-5" />
                </v-row>
              </template>
            </v-img>
          </div>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>
export default {
  name: "NoneUser",
  props: {
    feeds : [],
    keyword : String,
  },
  data() {
      return {
      };
    },
    methods : {

      //디테일피드 테스트버튼
      goFeedDetail(postId, memberId) {
        console.log(memberId);
        this.$router.push({ path: "/sns-feed-detail", query: {postId : postId, writer : memberId} });
      },

      //유저피드로 이동
      goUserFeed(userId) {
        this.$router.push({ path: "/sns-user-feed", query: { userId: userId } });
      },
      
  }
}
</script>
<style scoped lang="css" src="@/assets/css/sns/MainFeeds.css" />