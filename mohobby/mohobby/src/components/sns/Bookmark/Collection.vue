<template>
  <!-- 


      공백, 특수문자 안들어가게 막기


  -->
    <div class="main-title">

        <!-- 북마크 생성, 수정 모달 시작 -->
        <div>
          <v-row justify="center">
              <v-dialog v-model="dialog" scrollable max-width="350px">
                <template v-slot:activator="{ on, attrs }" class="mb-16">
                  <div v-if="editMode == false" v-bind="attrs" v-on="on" class="mr-3">
                    <v-icon size="40" color="#2255b1" >mdi-plus-box</v-icon>
                  </div>
                  <div v-else style="display:none;"  v-bind="attrs" v-on="on" class="mr-3">
                    <v-icon size="40" color="#2255b1" >mdi-plus-box</v-icon>
                  </div>
                  <div>
                    <v-btn v-if="editMode == true" @click="manager()" color="#2255b1" class="white--text">설정 <v-icon>mdi-cog-off</v-icon></v-btn>
                    <v-btn v-else @click="manager()" color="#e9e9ec">설정 <v-icon color="#2255b1" size="30">mdi-cog</v-icon></v-btn>
                  </div>
                </template>
              <v-card class="mx-auto">
                <v-card-title>
                  <span v-if="editMode == true" class="mx-auto text-h8">컬렉션 수정</span>
                  <span v-else class="mx-auto text-h8">컬렉션 만들기</span>
                </v-card-title>
                    <v-card-text>
                          <div>

                            <!-- 수정 dialog -->
                            <template v-if="editMode == true">
                              <v-text-field
                                v-model="editedCatgName"
                                @change ="editedCatgName"
                                label="*수정할 이름을 입력해주세요!"
                                :rules="rules"
                                hide-details="auto" />
                            </template>

                            <!-- 생성 dialog -->
                            <template v-else>
                              <v-text-field
                                width="300"
                                v-model="newCatgName"
                                label="*컬렉션이름을 입력해주세요!"
                                :rules="rules"
                                hide-details="auto" />
                            </template>

                          </div>
                      </v-card-text>

                      <!-- 수정모드 dialog -->
                      <v-card-actions v-if="editMode == true">
                        <v-spacer></v-spacer>
                          <v-btn  v-if="editedCatgName.length >=1 && editedCatgName.length<=10 && catgNames.indexOf(editedCatgName) == -1 && checkSpace(editedCatgName) == false && checkSpecial(editedCatgName) == false" 
                                  color="blue darken-1" text 
                                  @click="editCollection(catgId, memberId)">저장</v-btn>

                          <v-btn color="blue darken-1" text @click="cancel()">취소</v-btn>
                        </v-card-actions>

                        <!-- 생성모드 dialog -->
                        <v-card-actions v-else>
                          <v-spacer></v-spacer>
                          <v-btn  v-if="newCatgName.length >=1 && newCatgName.length<=10 && catgNames.indexOf(newCatgName) == -1 && checkSpace(newCatgName) == false && checkSpecial(newCatgName) == false" 
                                  color="blue darken-1" text 
                                  @click="createCollection(memberId)">저장</v-btn>

                          <v-btn color="blue darken-1" text @click="cancel()">취소</v-btn>
                        </v-card-actions>

                    </v-card>
                </v-dialog>
            </v-row>
        </div>
        <!-- 모달 끝 -->


        <!-- 컬렉션 시작 -->
        <div  v-for="(collection,i) in collections" :key="i"
              class="container"
              style="cursor:pointer;">
            <div class="content">
                <div class="content-overlay"></div>
              
                
                <!-- 없으면 디폴트 이미지 -->
                <v-icon v-if ='collection.postId == ""' size="250" color="#e9e9ec"
                        @click="goBookmark(collection.catgId, collection.catgName)"  >
                        mdi-folder-open
                </v-icon>
                <!-- <v-img  v-if ='collection.postId == ""'
                        class="white--text align-end rounded-sm dark" 
                        aspect-ratio="1.2" 
                        @click="goBookmark(collection.catgId, collection.catgName)"
                        :src="require('@/assets/image/sns/default/bookmark_default.png')" /> -->

                <!-- 저장 포스트의 첫번째 썸네일 커버사진으로  -->
                <v-img v-else class="white--text align-end rounded-sm dark" 
                        aspect-ratio="1.2"
                        @click="goBookmark(collection.catgId, collection.catgName)"
                        :src="require(`@/assets/image/sns/${collection.postId}/${collection.thumbnail}`)" />

                <!-- 관리버튼 - 수정, 삭제 -->
                <div  v-if="management == true" class="content-details fadeIn-bottom">
                  <v-btn  v-if='collection.catgName!= "default" && editMode == true'
                          class="text--white" 
                          color="#2255b1" 
                          dark v-bind="attrs"
                          v-on="on" 
                          @click="showEditForm(collection.catgId)">수정</v-btn>

                  <v-btn  v-if='collection.catgName!= "default" && editMode == true' 
                          dark
                          color="#F36A3E" 
                          @click="swal(collection.memberId,collection.catgId)">삭제</v-btn>
                  <h3 v-if='collection.catgName == "default"' class="content-title">{{collection.catgName}}</h3>
                </div>

                <div v-else class="content-details">
                  <h3 @click="goBookmark(collection.catgId, collection.catgName)" class="content-title">{{collection.catgName}}</h3>
                </div>
            </div>
        </div>
        <!-- 컬렉션 끝 -->
    </div>
</template>
<script>
  export default {
      name: "Collection",
      data() {
          return {
              collections:[],
              dialog: false,
              newCatgName: "",
              catgId:"",
              memberId : this.$store.state.id,
              tnPostId:Number,
              thumbnail:"",
              dummy:"",//해당 유저에게 생성된 컬렉션이 없을 시 자동생성되도록 도와주는 더미
              management: false, //북마크관리
              editMode:false, //수정모드
              editedCatgName: "", //수정할 컬렉션 이름

              catgNames: [], //본인 컬렉션 이름들

              //컬렉션 이름 rules
              rules: [
                value => !!value || '이름은 입력 부탁드립니다🙏', //이름 없으면
                value => (value && value.length <= 10) || '10글자 이내로 부탁드립니다🙏', //10글자 이내이면
                value => (this.catgNames.indexOf(value) == -1) || '이미 존재하는 이름입니다🙏', //이미 존재하는 이름이면
                value => (this.checkSpace(value) == false) || '공백은 자제 부탁드립니다🙏', //공백이 있으면
                value => (this.checkSpecial(value) == false) || '특수문자는 사용 자제 부탁드립니다🙏', //특수문자가 있으면
              ],
          }
      },
      setup() {
          
      },
      created() {
          this.getCollectionList(this.memberId);
          this.management = false;
          this.editMode = false;
          this.dialog = false;
      },
      mounted() {
      
      },
      unmounted() {
      
      },
      watch: {
        // inputE(val) { //입력한 값 받아와서
        //   if (!val) { //입력한 값이 없으면 return
        //       return
        //   }
        //   this.createCollection() //있으면
        // },
      },
      methods: {

        // 공백이 있나 없나 체크
        checkSpace(str) {
          if(str.search(/\s/) !== -1) {
            return true; //공백O
          } else {
            return false; //공백X
          }
        },

        // 특수 문자가 있나 없나 체크
        checkSpecial(str) {
          let bannedPattern = /[!?@#$%^&*():;+-=~{}<>\_\[\]\|\\\"\'\,\.\/\`\₩]/gi;

          if(bannedPattern.test(str)) {
            return true; //금지문자O
          } else {
            return false; //금지문자X
          }
        },


        //컬렉션 관리
        manager(){
          this.management = !this.management;
          this.editMode = !this.editMode;
          console.log("managemenet: "+ this.management);
          console.log("editMode: "+ this.editMode);
        },


        //dialog 수정
        cancel(){
          this.dialog = false;
          this.editedCatgName = "";
        },


        //컬렉션리스트
        getCollectionList(memberId){

            this.axios('/sns/collection/' + memberId, {
                params: {
                    memberId: memberId,
                }
            }).then(res => {
                
                if(res.data.length == 0){ // 만들어놓은 컬렉션이 존재하지 않으면 디폴트 컬렉션 생성
                  this.createDefaultCollection(memberId);

                }else{ // 만든 컬렉션이 존재하면 이름만 뽑아서 바인딩

                  for(let i=0; i<res.data.length; i++){
                    let isCatgName = res.data[i].catgName;
                    this.catgNames.push(isCatgName);

                  }

                    this.collections = res.data;

                }

            }).catch(err => {
                alert(err);
            });
        },


        //디폴트 컬렉션 생성 - 해당 유저 아이디에 생성된 컬렉션이 없으면 기본컬렉션 생성됨
        createDefaultCollection(memberId){

          const thumbnail = 'bookmark_default.png'

          this.axios.post('/sns/collection', {
              memberId : memberId,
              catgName : 'default',
              thumbnail : thumbnail,
          }).then(res => {
              this.getCollectionList(memberId);
          }).catch(err => {
              alert(err);
          });

        },


        //컬렉션생성
        createCollection(memberId){     

            const thumbnail = 'bookmark_default.png'
            this.dialog = false;

            this.axios.post('/sns/collection', {
                memberId : memberId,
                catgName : this.newCatgName,
                thumbnail : thumbnail,

            }).then(res => { 

                this.dialog = false;
                this.newCatgName = ""
                this.getCollectionList(memberId);

            }).catch(err => {
                alert(err);
            });

        },

        
        // 수정폼 호출
        showEditForm(catgId) {

          this.dialog =! this.dialog;
          this.catgId = catgId; //수정클릭한 컬렉션 Id 바인딩

        },


        //컬렉션수정(이름)
        editCollection(catgId, memberId){

          this.axios
            .put("/sns/collection", {
              catgId:catgId,
              catgName: this.editedCatgName,
            })

            .then((res) => {
              console.log("컬렉션이름수정 성공! " + res);
              this.$swal('컬렉션 이름이 수정되었습니다🙏');
              this.dialog = !this.dialog;
              this.editedCatgName = "";
              this.manager();
              // this.editMode = false;
              // this.management = false;
              this.getCollectionList(memberId);
            })
            .catch((err) => {
              console.log(err);
            });
          
        },

        
          //삭제swal
          swal(memberId,catgId) {

            this.$swal({
              title: "정말 삭제할까요?",
              text: "삭제된 컬렉션은 복구가 불가능합니다.🙏",
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "#2ac187",
              cancelButtonColor: "#d33",
              cancelButtonText: "취소",
              confirmButtonText: "네, 삭제할게요!",
            }).then((result) => {
              if (result.isConfirmed) {
                this.deleteClctn(memberId,catgId);
                this.$swal("삭제 완료!", "게시글이 삭제되었습니다.", "success");
              }
            });

          },


          //컬렉션 삭제
          deleteClctn(memberId,catgId) {

            this.swal();
            this.axios
              .delete("/sns/collection/" + catgId)
              .then((res) => {
                console.log("컬렉션삭제 성공! " + res);
                this.manager();
                this.getCollectionList(memberId);
              })
              .catch((err) => {
                alert(err);
              });

          },


          //해당 컬렉션으로 이동
          goBookmark(catgId,catgName){
            console.log(catgId);
            console.log(catgName);
            this.$router.push({ path: '/sns-bookmark-detail', query: {catgId : catgId, catgName : catgName} });
          }

       },
    };
</script>

<style scoped lang="css" src="@/assets/css/sns/Collection.css" />
    