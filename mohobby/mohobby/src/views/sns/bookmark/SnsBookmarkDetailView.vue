<template>
    <div>
        <SnsSidebar />
        <h3 class="heading">{{catgName}} 컬렉션</h3>
        <div class="container" >
            <!-- bookmark가 있을 때 -->
            <div v-if="isBookmark == true">
                <div class="gallery">
                    <div class="gallery-item" v-for="(bookmark, i) in bookmarks" :key="i">
                        <v-img @click.stop="goFeedDetail(bookmark.postId, bookmark.memberId)"
                        aspect-ratio="1.2"
                        :src="require(`@/assets/image/sns/${bookmark.postId}/${bookmark.thumbnail}`)" />                           
                        <v-btn v-if='isCollections.length>1 && catgName !== "default"'
                          @click="openDialog(bookmarks[i].postId)" 
                          color="white" 
                          class="change_catg_btn">
                          카테고리 이동
                        </v-btn>
                    </div>

                </div>
            </div>



            <!-- 북마크가 없을 때 -->
            <div class="wrap_box" v-else>
                <v-img class="mx-auto mt-10 mb-10" width="100" src="@/assets/image/sns/default/warn.jpg" alt="no_result"></v-img>
                <div class="text_box">
                    <p>
                        저장된 피드가 없습니다!<br>마음에 드시는 피드 저장부터 부탁드립니다🙏
                    </p>
                </div>
            </div>

                    <!-- 

                북마크dialog
                
        -->
        <v-dialog v-model="dialog2" max-width="500px">
          <v-card>
            <v-card-title class="justify-space-around">
              이동할 컬렉션 선택
            </v-card-title>
            <v-card-text>


              <!-- 컬렉션 선택 select_box -->
              <v-select
                @click="getCollectionList(memberId)"
                :items="exceptDefaltCtg" item-text="catgName" item-value="catgId" 
                label="이동할 컬렉션을 선택하세요" 
                v-model="selectedCollection"
                return-object />
            </v-card-text>

            <!-- 변경사항 저장, 취소버튼 -->
            <v-card-actions>
              <v-row class="ma-4 justify-space-around">
                <v-btn
                  text
                  @click="changeCatg()"
                >
                  저장
                </v-btn>
                <v-btn text @click="dialog2 = false"> 취소 </v-btn>
                <!-- 북마크 컬렉션 지정 저장,취소버튼 끝 -->

              </v-row>
            </v-card-actions>
          </v-card>
        </v-dialog>

            
        </div>
    </div>
</template>
<script>

  import SnsSidebar from "@/components/sns/Common/SnsSidebar.vue";

  export default {

  name: "Detail",

  components:{ SnsSidebar },

  data: () => ({
      bookmarks: [], //북마크 피드들

      //컬렉션리스트에서 받은 값
      catgId: "",   
      catgName: "",

      dialog2: false, //컬렉션 선택 dialog
      isBookmark: false,//북마크 있을때

      memberId: "", //세션아이디

      isCollections: [], //유저의 기존 컬렉션,
      selectedCollection: "", //북마크를 저장할 컬렉션

      catgNames:[], //이미 존재하는 컬렉션 이름들

      exceptDefaltCtg:[],

      postId: Number,
  }),

  created(){
      this.catgId = this.$route.query.catgId;
      this.catgName = this.$route.query.catgName;
      this.memberId = this.$store.state.id;
      

      console.log(this.catgName !== "default");


      if(this.catgName !== "default"){
        this.getBookmarkList(this.catgId);
        console.log("북마크 디테일뷰 컬렉션");

      }else {
        this.getAllBookmarks();
      }
      console.log("북마크 디테일뷰 전체" + this.catgId + ", " + this.catgName);
  },

  methods: {


        openDialog(postId){
          this.dialog2 = !this.dialog2;
          this.postId = postId;
        },


       //북마크전체
       getAllBookmarks(){
            this.axios('/sns/collection/allBookmarks',{

                    params: {
                        memberId : this.$store.state.id, //post아이디,thumbnail 조회해옴
                    }

                }).then(res => {

                    console.log("북마크 리스트 호출 성공!");
                    console.log(res.data.length);
                    if(res.data.length < 1){  //북마크 리스트가 없으면
                        this.isBookmark = false; //북마크 저장 권유
                    }else{    //있으면 목록보여주기
                        this.bookmarks = res.data;
                        console.log(this.bookmarks);
                        this.isBookmark = true;
                        this.getCollectionList(this.memberId);
                    }
                }).catch(err => {
                    alert(err);
            });
      },


      //컬렉션별 북마크
      getBookmarkList(catgId){
            this.axios('/sns/collection/bookmark/' + catgId, {

              params: {
                  catgId: catgId, //post아이디,thumbnail 조회해옴
              }
            }).then(res => {
              console.log("북마크 리스트 호출 성공!");
              console.log(res.data.length);

              this.bookmarks = res.data;
              console.log(this.bookmarks);
              if(res.data.length < 1){  //북마크 리스트가 없으면
                  this.isBookmark = false; //북마크 저장 권유

              }else{    //있으면 목록보여주기
                this.bookmarks = res.data;
                this.isBookmark = true;
                this.getCollectionList(this.memberId);
              }

          }).catch(err => {
              alert(err);
          });
        },


        //카테고리 이동
        changeCatg(){
           
            this.axios
            .put("sns/collection/bookmark", {
              catgId: this.selectedCollection.catgId,
              postId: this.postId,
            })

            .then((res) => {
              console.log("컬렉션이동 성공! " + res);
              this.$swal("' " + this.selectedCollection.catgName + " '컬렉션으로 이동이 완료되었습니다.🙏");
              this.dialog2 = !this.dialog2;

              if(this.catgName !== "default"){
                this.getBookmarkList(this.catgId);
                console.log("북마크 디테일뷰 컬렉션");

              }else {
                this.getAllBookmarks();
              }
            })
            .catch((err) => {
              console.log(err);
            });
        },


        //회원의 컬렉션리스트
        getCollectionList(memberId){
            this.axios('/sns/collection/' + memberId, {
                params: {
                    memberId: memberId,
                }
            }).then(res => {
                console.log(res.data);
                this.isCollections = res.data;
                
                for(let i=0; i<res.data.length; i++){
                  let catgName = this.isCollections[i].catgName;
                  let catgId = this.isCollections[i].catgId
                  if(catgName !== "default" && catgName !== this.catgName){
                    this.exceptDefaltCtg.push({catgName:catgName, catgId:catgId});
                  }

                }

                }).catch(err => {
                alert(err);
            });
        },




      //피드 디테일로 이동
      goFeedDetail(postId,writer) {
        console.log(postId,writer);
        this.$router.push({ path: '/sns-feed-detail', query: { postId : postId, writer: writer } });
      }
    }
  }
  </script>
<style scoped lang="css" src="@/assets/css/sns/BookmarkDetail.css">
</style>