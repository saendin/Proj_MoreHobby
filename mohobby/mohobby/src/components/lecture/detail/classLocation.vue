<template lang="">
    <v-container fluid>
        <v-card flat justify="center" align="center" style="padding-top: 20px">
            <h1>{{ info.address }}</h1>
            <div class="kmap" style="width:800px;height:400px;" ref="map"></div>
        </v-card>
    </v-container>
</template>
<script>
export default {
    data() {
        return {
            info: {},
        }
    },
    props: {
        classId: {
            type: String,
            default: 1,
        },
    },
    created() {
        this.getInfo();
    },
    methods: {
        getInfo() {
            this.axios('/class/detail/'+this.classId, {
                params: {
                    memberId : this.$store.state.id,
                }
            }).then( res => {
                this.info = res.data;
                this.floatingMap();
            })
        },
        floatingMap() {
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
            geocoder.addressSearch(this.info.address, function(result, status) {

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
                        content: '<div style="width:150px;text-align:center;padding:6px 0;">강의 장소</div>'
                    });
                    infowindow.open(map, marker);

                    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                    map.setCenter(coords);
                } 
            });
        },
    },
}
</script>
<style>
    
</style>