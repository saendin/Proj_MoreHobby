<template>
  <div id = main>
    <v-btn icon>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>
    <v-btn icon>
      <v-icon left>mdi-plus-thick</v-icon>
    </v-btn>
    <!-- 카드형5개 옆으로 슬라이드 -->
    <h3>추천 만능 재주꾼들 피드</h3>

    <!-- 카드형 무한스크롤링 -->
    <h3>재주 견습생들 피드</h3>
    <listCmp v-for="(card, i) in card_list" :key="i" 
          :card="card_list"/>
    <!-- <listCmp 
          :card="card_list"
    /> -->
  </div>
</template>
<script>
export default {
  name: "snsMain",
  components: {
  },

  data() {
    return{
      card_list : []
    };
  },

  methods: {
    //list dummy data
    getCard() {
      const card_titles = [
        "1"
       ,"2"
       ,"3"
       ,"4"
       ,"5"
       ,"6"
      ];

      const card_srcs = [
       "https://img.insight.co.kr/static/2020/03/06/700/j0rpqkg2y659dw4acito.jpg"
      ,"https://img.insight.co.kr/static/2020/03/06/700/w73pq7140798cym777d8.jpg"
      ,"https://img.insight.co.kr/static/2020/03/06/700/d852a5r04tvpa860xz92.jpg"
      ,"https://img.insight.co.kr/static/2020/03/06/700/o5384w751o0583qs2659.jpg"
      ,"https://img.insight.co.kr/static/2020/03/06/700/4o5ookw8k16p4k5jgzur.jpg"
      ,"https://img.freepik.com/premium-photo/outdoor-camping-equipment-in-a-tent-setting-on-hill-in-the-evening-at-countryside_49071-5905.jpg?w=2000"
      ];
      
      const card_likecnts = [
        "153",
        "200",
        "198",
        "123",
        "98",
        "159"
      ];

      const card_cmtcnts = [
        "20",
        "12",
        "10",
        "19",
        "32",
        "60"
      ];

      const card = [];

      for (let i = 0; i < 6; i++) {
        card.push({
          title: card_titles[
            Math.floor(Math.random() * card_titles.length)
          ],
          
          src: card_srcs[
            Math.floor(Math.random() * card_srcs.length)
          ],
          likecnt: card_likecnts[
            Math.floor(Math.random() * card_likecnts.length)
          ],
          cmtcnt: card_cmtcnts[
            Math.floor(Math.random() * card_cmtcnts.length)
          ],
          flex:"2"
        });
      }

      return card;
    },
    
   handleScroll() {
    if (
      window.scrollY + window.innerHeight >=
      document.body.scrollHeight - 50
      ) {
        const new_card = this.getCard();
      this.card_list = [...this.card_list, ...new_card];
    }
   }
  },

  mounted () {
    this.card_list = this.getCard();
    window.addEventListener('scroll', this.handleScroll);
  }
};
</script>

<style>
  #main {
    margin: 20px 10%;
  }
</style>
