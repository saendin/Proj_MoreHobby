<template>
    <div class="container">
        <div v-show="memberId == userId" class="mb-6">
            <v-switch
                v-model="secret"
                label="비밀글만 모아보기"
                color="red darken-3"
                value="on"
                hide-details
              ></v-switch>
        </div>
        <!-- 피드 갤러리 -->
        <div class="gallery">
            <div v-for="(feed,i) in feeds" :key="i" 
                @click.stop="goFeedDetail(feed.memberId, feed.postId)" 
                 class="gallery-item" 
                 tabindex="0">
                <v-icon v-show="feed.secPost === 1" color="red" class="icon_secret">mdi-lock</v-icon>
                <v-icon v-show="feed.imgs > 1" class="image_icon">mdi-checkbox-multiple-blank</v-icon>
                <v-img class="gallery-image" :src="require(`@/assets/image/sns/${feed.postId}/${feed.thumbnail}`)" alt="thumbnail_img" />
                <div class="gallery-item-info">
                    <ul>
                    <li class="gallery-item-likes"><v-icon color="#e1e1e1" class="mr-2">mdi-heart</v-icon>{{ feed.likes }}</li>
                    <li class="gallery-item-comments"><v-icon color="#e1e1e1" class="mr-2">mdi-chat-outline</v-icon>{{ feed.cmts }}</li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- 게시물이 없을 때 -->
        <div class="no_post" v-show="feeds.length < 1">
            <v-img id="no_post_img" :src="require(`@/assets/image/sns/default/no_post.png`)" />
            <h2>아직 업로드된 게시글이 없습니다!</h2>
        </div>
        <!-- 피드 갤러리 끝 -->
        <!-- <div class="loader"></div> -->
    </div>
</template>
<script>
export default {
    name: "Feeds",
    props:{
        userId:String,
    },
    data(){
        return{
            feeds:[],
            getUserId:"", //부모에서 받은 userId바인딩
            isSecPost:"", //내피드인지 아닌지
            memberId: this.$store.state.id,
            secret: false,
        }
    },

    watch: {
        secret(sec){
            console.log("sec: " + sec);
            if(sec == "on") {
                this.loadSecretFeeds();
            }else {
                this.loadUserFeedList(this.getUserId);
            }
        }
    },

    created() {
        this.getUserId = this.userId;
        this.loadUserFeedList(this.getUserId);
    },

    methods: {

        //피드호출
        loadUserFeedList(getUserId) {
            if(this.getUserId != this.memberId){
            this.isSecPost = 0;
            };
            console.log(this.isSecPost);
            this.axios
            ('sns/user/user_feeds/' + getUserId, {
                params: {
                secPost : this.isSecPost,
                },
            })
            .then(res => {
              this.feeds = res.data;
            }).catch(err => {
              console.log(err);
            });  
          },


        //비밀글 모아보기
        loadSecretFeeds(){
            this.axios
            ('sns/user/archive/' + this.memberId)
            .then(res => {
              this.feeds = res.data;
            }).catch(err => {
              console.log(err);
            });  
          },


        //피드 디테일로 이동
        goFeedDetail(memberId, postId) {
            console.log(postId);
            this.$router.push({ path: '/sns-feed-detail', query: {writer : memberId, postId : postId} }).$router.go(0);
        },
    },
    setup() {
    },
}
</script>
<style scoped lang="css" src="@/assets/css/sns/Feeds.css">
</style>