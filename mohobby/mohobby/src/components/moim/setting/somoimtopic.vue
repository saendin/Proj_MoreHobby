<template>
    <div>
        <v-list-item-content>

    <!--주제선택-->
    <v-col
    cols="12"
    sm="5">
    <v-dialog
    v-model="check"
    max-width="600px"
    >
    <template v-slot:activator="{ on, attrs }">
    <v-btn
    color="green"
    dark
    rounded
    v-bind="attrs"
    v-on="on"
    >
    {{ selected || '주제 선택'}}
    </v-btn>
    </template>
    <v-sheet
    elevation="10"
    rounded="xl"
    >
    
    <!-- 취미 hobb -->
    <div class="pa-12">
    <v-chip-group
    active-class="light-green--text"
    column
    v-model="selected"
    >
    <div>
        <div class="mt-3">
    <span class="font-weight-bold">취미</span>
    </div>
    <div class="pa-1">
            <v-chip v-for="item in hobb" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>

    <!-- 스포츠/레저 sports -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">스포츠/레저</span>
        </div>
        <div class="pa-1">
            <v-chip v-for="item in sports" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 어학/외국어 lang -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">어학/외국어</span>
        </div>
        <div class="pa-1">
            <v-chip v-for="item in lang" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 문화/예술 arts -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">문화/예술</span>
    </div>
        <div class="pa-4">
            <v-chip v-for="item in arts" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 여행/캠핑 trip -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">여행/캠핑</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in trip" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 음악 music -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">음악</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in music" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 건강/다이어트 diet -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">건강/다이어트</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in diet" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 친목/모임 amity -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">친목/모임</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in amity" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 맛집/요리 matzip <=맛집ㅋㅋㅋㅋㅋㅋㅋㅋㅋ -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">맛집/요리</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in matzip" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 인문/과학 humenities -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">인문/과학</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in humenities" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!--  팬클럽 fan -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">팬클럽</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in fan" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!--  게임 game -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">게임</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in game" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!--  만화/애니메이션 ani -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">만화/애니메이션</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in ani" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 반려동물/동물 animal -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">반려동물/동물</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in animal" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 교육/공부 edu -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">교육/공부</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in edu" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- IT/컴퓨터 it -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">IT/컴퓨터</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in it" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 경제/재테크 financial -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">경제/재테크</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in financial" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 종교/봉사 charity -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">종교/봉사</span>
        </div>
        <div class="pa-4"> 
            <v-chip v-for="item in charity" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}
            </v-chip>
        </div>
    </div>
    <!-- 자연/귀농 nature -->
    <div>
    <div class="mt-3">
        <span class="font-weight-bold">자연/귀농</span>
        </div>
        <div class="pa-4">
            <v-chip v-for="item in nature" :key="item" :value="item" class="mr-3 mb-3">
            {{item}}                                                                              
            </v-chip>
        </div>
    </div>
    </v-chip-group>
    <v-card-actions class="justify-center mr-5">
    <v-btn
    depressed
    elevation="2"
    @click="[(check = false), cancel()]"
    >
        취소
    </v-btn>
    <v-btn
    color="success"
    depressed
    elevation="2"
    @click="[check = false, save()]"
    >
        저장
    </v-btn>
    </v-card-actions>
    </div>
    </v-sheet>
    </v-dialog>
    </v-col>
    </v-list-item-content>
    </div>
</template>
<script>
  export default {
    data() {
      return {
        items : [{
          src : "https://cdn.class101.net/images/84d01f2e-ec28-4e30-8ed1-fc6e55954e42/960xauto.webp"
        }],
          hobb: [
            '독서',
            '글쓰기',
            'DIY/공예',
            '가죽/목공',
            '드론/액션캠/RC',
            '바둑/장기',
            '취미',
            ],
          sports: [
            '러닝/걷기',
            '자전거',
            '테니스',
            '등산/산악',
            '야구',
            '축구/풋살',
            '당구',
            '서핑',
            '자동차',
          ],
          lang:[
            '영어',
            '일본어',
            '중국어',
            '외국어',
            '스페인어/포르투갈어'
          ],
          arts: [
            '그림/일러스트',
            '디자인',
            '캘리그라피/서예',
            '사진',
            '댄스/무용',
            '공연/뮤지컬',
            '영화',
            '미술/전시',
            '문화/예술'
          ],
          trip: [
            '국내여행',
            '해외여행',
            '캠핑/백패킹'
          ],
          music: [
            '노래',
            '악기',
            '음악'
          ],
          diet: [
            '피트니스',
            '요가/필라테스'
          ],
          amity: [
            '친목/모임',
            '육아/맘 모임'
          ],
          matzip: [
            '맛집',
            '요리/레시피'
          ],
          humenities: [
            '역사',
            '심리학',
            '인문/철학'
          ],
          fan: [
            '프로야구',
            '팬클럽'
          ],
          game: [
            '게임'
          ],
          ani: [
            '만화/애니메이션'
          ],
          animal: [
            '반려동물/동물'
          ],
          edu: [
            '공부'
          ],
          it: [
            'IT'
          ],
          financial: [
            '경제/재테크'
          ],
          charity: [
            '봉사/자선'
          ],
          nature: [
            '꽃/식물'
          ],
          check : false,
          region : false,
          moimImg: '',
          moimInfo: '',
          moimCatg: '',
          moimRegion: "",
          regionList: [],
          selected: '', // v-chip 체크한 상태
          temp:'', //취소시 빈 변수 넘겨줌
          check : false,
          region : false
      }
    },
    methods: {
        showChip(e){
            this.moimCatg = e
      },
        save(){
            this.moimCatg = this.selected
            this.temp=this.selected
            this.$emit('Catg',this.moimCatg)
      },
        cancel(){
            this.selected =this.temp
      },
    }
  }
  </script>
<stlye>

</stlye>