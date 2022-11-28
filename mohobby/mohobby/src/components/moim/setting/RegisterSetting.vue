<template>
  <div id="container">
    <v-expansion-panels v-model="panel" multiple>
      <v-card-title class="text-5">
        가입 조건 설정
      </v-card-title>
      <v-expansion-panel>
        <v-expansion-panel-header v-slot="{ open }">
          <v-row no-gutters>
            <v-col cols="4">
              성별
            </v-col>
            <v-col cols="8" class="text--secondary">
              <v-fade-transition leave-absolute>
                <div class="memberCount" v-if="open" key="0">
                </div>
              </v-fade-transition>
            </v-col>
          </v-row>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-row justify="space-around" no-gutters>
            <v-spacer />
            <v-col cols="3">
              <v-select
                :items="sex"
                v-model="selected"
                @change="select()"
                label="성별"
                dense
              ></v-select>
            </v-col>
          </v-row>
        </v-expansion-panel-content>
        </v-expansion-panel>

      <v-expansion-panel>
        <v-expansion-panel-header v-slot="{ open }">
          <v-row no-gutters>
            <v-col cols="4">
              나이
            </v-col>
            <v-col cols="8" class="text--secondary">
              <v-fade-transition leave-absolute>
                <span v-if="open"></span>
              </v-fade-transition>
            </v-col>
          </v-row>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-row justify="space-around" no-gutters>
            <v-col cols="3">
              <v-select
                :items="age"
                label="최소나이"
                v-model="min"
                dense
              ></v-select>
            </v-col>

            <v-col cols="3">
              <v-select
                :items="age"
                label="최대나이"
                v-model="max"
                dense
              ></v-select>
            </v-col>
          </v-row>
        </v-expansion-panel-content>
      </v-expansion-panel>

      <v-expansion-panel>
        <v-expansion-panel-header v-slot="{ open }">
          <v-row no-gutters>
            <v-col cols="4">
              최대 멤버 수
            </v-col>
            <v-col cols="8" class="text--secondary">
              <v-fade-transition leave-absolute>
                <div class="memberCount" v-if="open" key="0">
                </div>
              </v-fade-transition>
            </v-col>
          </v-row>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-row justify="space-around" no-gutters>
            <v-spacer />
            <v-col cols="3">
              <v-select
                :items="count"
                label="선택"
                v-model="count"
                dense
              ></v-select>
            </v-col>
          </v-row>
        </v-expansion-panel-content>
        
      </v-expansion-panel>
    </v-expansion-panels>
    
    <v-card-actions class="mr-5 mb-5 mt-5">
    <v-spacer />
    <v-btn @click="updateSetting()">저장</v-btn>
    </v-card-actions>
 
  </div>
</template>
<script>
import { min } from 'moment';

export default {
  data: () => ({
    panel: [0, 1, 2],
    moimId: 1,
    selected:'',
    gender: '',
    sex: ['남성','여성','모두'],
    count: [10, 30, 50, 100],
    age: [],
    max: '',
    min: ''
    }),
    methods: {
      selectAge() {
        for(let i=2022; i>=1923; i--){
            this.age.push(i);
        }
      },
      updateSetting(){
          const vm = this;
          if(this.selected == "남성"){
            this.gender = 1
          } else if(this.selected == "여성"){
            this.gender = 2
          } else {
            this.gender = 0
          }
   
        this.axios.put("/updateSetting",{
          gender : this.gender,
          maxAge : this.max,
          minAge : this.min,
          maxPeople : this.count,
          moimId : this.moimId
        }).then(function (resp) {
          console.log(resp)                                                                                                                                                                                                                                          
          vm.$swal('변경이 완료되었습니다!')
          vm.$router.push({name : "moimBoard"})
        }).catch(function (error) {
          console.log(error)
        })
      }
    },
    created() {
      this.selectAge()
    }
  
}
</script>
<style scoped>
#container {
  max-width: 60%;
  margin: 2% auto;
}
</style>