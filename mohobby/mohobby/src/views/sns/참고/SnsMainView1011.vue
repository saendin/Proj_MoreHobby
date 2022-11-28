<template>
    <div id = "container">
      <div id="searchbar">
          <div class="regFeed">
              <!-- 글쓰기: 비회원-로그인폼 -->
              <v-card-actions  v-if="!this.member" >
                  <v-spacer></v-spacer>
                  <div class="text-center">
                      <v-dialog
                              v-model="noneuser"
                              width="500"
                      >
                      <template v-slot:activator="{ on, attrs }">
                          <v-btn text>
                              <v-chip
                              color="success"
                              outlined
                              v-bind="attrs"
                              v-on="on"
                              >
                              <v-icon left>mdi-plus</v-icon>글쓰기
                              </v-chip>
                          </v-btn>
                      </template>
                          <v-card>
                              <br><br>
                              <v-card-text class="font-weight-bold center">
                                  로그인이 필요합니다 !
                              </v-card-text>
                              <v-divider></v-divider>
                              <v-card-actions>
                                  <v-spacer></v-spacer>
                                  <v-btn
                                  color="success"
                                  depressed
                                  @click="join"
                                  >로그인하러 가기</v-btn>
                                  <v-btn
                                  depressed
                                  @click="noneuser=false"
                                  >닫기</v-btn>
                              </v-card-actions>
                          </v-card>
                      </v-dialog>
                  </div>
              </v-card-actions>
              <!-- 글쓰기: 회원-글쓰기폼 -->
              <v-card-actions v-else>
                  <v-spacer></v-spacer>
                  <v-btn text>
                      <v-chip
                      color="success"
                      outlined
                      @click="select"
                      >
                      <v-icon left>mdi-plus</v-icon>
                      글쓰기
                      </v-chip>
                  </v-btn>
              </v-card-actions>
          </div>
          <!-- 검색창 -->
          <div>
          <v-card-actions>
              <v-spacer></v-spacer>
                  <v-autocomplete 
                          v-model="ctg" 
                          :items="ctg" 
                          item-text="tag" 
                          item-value="tag" 
                          label="해시태그나 유저 아이디를 검색해보세요"
                          dense 
                          outlined
                          :search-input.sync="userInput" 
                          @input="userInput=null"
                          menu-props="{'closeOnContentClick': true}"
                          class="rounded-xl"
                          append-icon="mdi-magnify"
                          @change="search()"
                          style="height:50px"
                  />
          </v-card-actions>
          <!-- 해시태그 검색 -->
          <v-card-actions>
              <v-spacer></v-spacer>
              <div id="chip">
                  <v-row justify="space-around">
                      <v-col cols="12">
                          <v-sheet ref="getHashtag" >
                              <v-chip-group active-class="primary--text">
                                  <v-chip v-for="(item,i) in items" 
                                          :key="i"
                                          @click="searchHashtag(item.hashtag)"
                                           color="#2ac187"
                                           class="mx-auto white--text font-weight-bold">
                                      {{ item.hashtag }}
                                  </v-chip>
                              </v-chip-group>
                          </v-sheet>
                      </v-col>
                  </v-row>
              </div>
          </v-card-actions>
      </div>
  </div>
      <div>
        <v-btn @click="goUserFeed()">유저피드테스트</v-btn>
      </div>
      <div>
          <SearchPage id="searchResult" :feeds="feeds" />
          <div id = "noSearchResult">
              <h1> 검색 결과가 없습니다 !</h1>
          </div>
      </div>
      <div id="hotLecturers">
      <!-- 인기 피드리스트 -->
          <h3>추천 만능 재주꾼들 피드</h3>
          <HotLecturer name="this.items" />
      </div>
      <div id="nonuserFeeds">
          <h3>재주 견습생들 피드</h3>
          <!-- 랜덤피드 무한스크롤링 -->
          <NoneUser />
      </div>
      <!-- </ul> -->
    </div>
  </template>
  <script>
    import HotLecturer from "@/components/sns/Main/HotLecturer";
    import NoneUser from "@/components/sns/Main/Noneuser";
  
    export default {
      name: "snsMain",
      components: { SnsSearchbar, HotLecturer, NoneUser,   },
     
      data() {
          return {
              hotTags: [
                  "오운완",
                  "다이어트",
                  "헬린이",
                  "오수완",
                  "주짓수",
                  "삼대오백"
              ],
              ctg: [
                  { tag: '운동' },
                  { tag: '오공완' },
                  { tag: '다이어트' },
                  { tag: '건강' },
                  { tag: '공예' },
                  { tag: '연극' },
                  { tag: '취향' },
                  { tag: '메이크업' },
                  { tag: '오운완' },
  
              ],
              userInput: null,
              memberId: 123,
              items: [{
                  name: '최신순',
                  value: '최신순'
                  },
                  {
                  name: '인기순',
                  value: '인기순'
                  },
              ],
              //검색
              feeds: [],
              word: "",
              //moim
              role : 0,
              noneuser : false,
              items: [],
              member : this.$store.state.id,
              show: false,
          }
      },
      watch: {
          userInput(val) {
              if (!val) {
                  return
              }
              this.fetchEntriesDebounced()
          },
      },
      created() {
          this.feeds=this.$route.params.sfeeds; //피드디테일에서 받아옴
          this.show = this.$route.params.show; //받아온값이 true면
          this.getHotHashtags();//함수실행
          console.log(this.$route.params.sfeeds);
          console.log(this.$route.params.show);
      },
      methods: {
          //상단바 top6해시태그 표시
          getHotHashtags() {
              this.axios('/sns/main/hashtag').then(res => {
              this.items = res.data;
              console.log(this.items);
            }).catch(err =>{
              console.log(err);
            });
          },
          //해시태그 검색
          searchHashtag(getHashtag){
              console.log("이고")
              console.log(getHashtag);
              this.axios('/sns/search/hashtag', {
                  params : {
                      hashtag : getHashtag
                  }
              }).then(res => {
                  console.log(res);
                  this.feeds = res.data;
                  console.log("피드들어옴"+this.feeds);
                  if (this.feeds.length === 0){
                      document.getElementById('searchResult').style.display = "block";
                      document.getElementById('noSearchResult').style.display = "block";
                      document.getElementById('searchResult').style.display = "none";
                      document.getElementById('searchResult').style.display = "none";
                  } else {
                      document.getElementById('searchResult').style.display = "block";
                      document.getElementById('hotLecturers').style.display = "none";
                      document.getElementById('nonuserFeeds').style.display = "none";
                  }
                  
              }).catch(err =>{
                  console.log(err);
              });
          },
  
          //글 등록 이동
          select : function() {
              if (this.member) {
              this.$router.push({ path: 'snsFeedRegister' })
              }
          },
          join() {
              this.$router.push({ path: 'login' })
          },
          //
          fetchEntriesDebounced() {
              // cancel pending call
              clearTimeout(this._timerId)
  
              // delay new call 500ms
              this._timerId = setTimeout(() => {
                  // maybe : this.fetch_data()
                  this.people = this.itemData ? this.itemData : []
              }, 500)
          },
          goRegForm() {
              this.$router.push({ name: "snsFeedRegister" });
          },
  
          //
          // search(){
          //     //유저 아이디 or 닉네임 조회
          //     let searchValue = document.querySelector("#rounded-xl").value;
          //     console.log(searchValue)
          //     this.axios({
          //     url : '/sns/search/user',        
          //     params : {
          //         memberId : searchValue,
          //         nickname : searchValue,
          //     }
          //     }).then(res => {
          //     console.log(res);
          //     this.data = res.data;
          //     console.log(this.data);
  
          //     }).catch(err =>{
          //     console.log(err);
          //     });
          // },
          enterkey: function (e) {
              if (window.event.keyCode == 13) {
              this.search(e);
          }
        },
        checkbox: function () {
          const ckbox = document.querySelector(".form-check-input");
          const is_cked = ckbox.checked;
          var isChecked = document.querySelector(".form-check-input").innerText = is_cked
          console.log(isChecked);
          // axios({
          //   url: "http://localhost:8088/zippy/used/main",
          //   methods: "GET",
          //   params: {
          //     keyword: "",
          //     location: "",
          //     category: "",
          //     checked: isChecked
          //   }
          // }).then(res => {
          //   console.log(res);
          //   this.data = res.data;
          // }).catch(err => {
          //   console.log(err)
          // })
        },
        // dropVal: function () {
        //   let dropValue = this.select.value;
        //   console.log(dropValue);
        //   axios({
        //     url : "http://localhost:8088/zippy/used/main",
        //     methods : "GET",
        //     params : {
        //       keyword: "",
        //       location: "",
        //       category: "",
        //       checked: "",
        //       dropbox : dropValue
        //     }
        //   })
        //  },
      }
    };
  </script>
  
  <style scoped>
      * {
        list-style:none;
      }
      
      #container {
        width : 80%;
        list-style:none;
        margin: 0 auto;
      }
  
      #searchbar{
          width:80%;
          margin: 0 auto;
      }
  
      label {
          margin-right: 3px;
      }
  
      #searchResult{
          display : none;
      }
  
      #noSearchResult{
          display : none;
      }
  
  
  </style>