<template>
  <div>
    <div class="address">
      <button class="button" @click="search">주소 찾기</button>
      <input type="text" id="address" placeholder="주소를 검색하세요." v-model="address" />
    </div>
    <h3>kakao map test</h3>
    <div class="kmap" style="width:500px;height:400px;" ref="map"></div>
  </div>
</template>

<script>

export default {
    data() {
      return{
        address: '',
        posX: 0,
        posY: 0,
      }
    },
    mounted() {
      
    },
    methods: {
      search: function(){
        new window.daum.Postcode({
          oncomplete: (data) => {
              var roadAddr = data.roadAddress; // 도로명 주소 변수
              // 주소 정보를 해당 필드에 넣는다.
              this.address = roadAddr;
              // 지도 띄우기
              this.floatingMap();
          }
        }).open();
      },
      floatingMap: function(){
        let kakao = window.kakao;

        var mapContainer = this.$refs.map, // 지도를 표시할 div 
            mapOption = {
                center: new kakao.maps.LatLng(128.5932793, 35.8690334), // 지도의 중심좌표
                level: 2 // 지도의 확대 레벨
            };  

        // 지도를 생성합니다    
        var map = new kakao.maps.Map(mapContainer, mapOption); 

        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(this.address, function(result, status) {

            // 정상적으로 검색이 완료됐으면 
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:6px 0;">수강장소</div>'
                });
                infowindow.open(map, marker);

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            } 
        });

      }
    },
    components: {  }
}
</script>

<style scoped>
.test{ display:flex; justify-content: center; align-items: center; height:100vh; }
.button{ width: 200px; height:40px; background-color:#ffffff; border:1px #a8a8a8 solid; color:black; display:flex; align-items: center; justify-content: center; cursor:pointer; }
</style>