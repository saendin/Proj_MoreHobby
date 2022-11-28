<template>
    <div class="mx-auto">
      <SnsSidebar></SnsSidebar>
      <h1>내 비밀게시글 보기</h1>

  
    </div>
  </template>
  <script>
      import SnsSidebar from "@/components/sns/Common/SnsSidebar";

  
    export default {
      name: "Follower",
      components: { SnsSidebar },

      data() {
        return {
          memberId : this.$store.state.id,
        };
      },
      setup() {
        
      },
      created() {

      },
      mounted() {
    
      },
      unmounted() {
    
      },
      methods: {

     //게시글 상세 로드
    showDetail(postId, writer) {
      this.axios("/sns/user/feed_detail/" + postId, {
        params: {
          memberId: writer,
        },
      })
        .then((res) => {
          if(this.confirmMember(this.memberId) == true ){
            console.log("로그인세션을 확인합니다");
            this.getBookmarkStatus(postId);
            this.getCollectionList(this.memberId);
          }
          console.log();
          this.items = res.data;
          console.log("비밀글여부: "+ this.items.secPost);
  
          //자신의 게시물이면 dot list 세팅
          this.lists.push({title: "수정"});
          this.lists.push({title: "삭제"});
          if(this.items.secPost == 0){
            this.lists.push({title: "비밀글로"});
          } else {
            this.lists.push({title: "비밀글 해제"});
          }
  
  
          if (this.items.hashtag != null) {
            let str = this.items.hashtag; //%%,%%,%% 형태
            let hashtag = str.split(","); //해시태그 자르기
            this.hashtags = hashtag; //자른 해시태그들 hashtags에 담기
          }
          console.log("상세페이지 접근 성공!");
        })
        .catch((err) => {
          alert("게시글호출 실패" + err);
        });
    },
  
      
  
    },
  };
  </script>

  