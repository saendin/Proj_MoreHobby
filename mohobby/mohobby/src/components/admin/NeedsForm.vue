<template>
    <main>
   <div>
                  <label for="prodName" style="margin-right: 20px;">상품명</label>
                  <input type="hidden" id="classId" v-model="classId" :name='"list[" + i + "].classId"'>
                  <input type="text" id="prodName" class="input" v-model="prodName" :name='"list[" + i + "].prodName"' style="width: 250px; margin-right: 20px">
                  <label for="prodPrice" style="margin-right: 20px;">상품가격</label>
                  <input type="text" class="input" id="prodPrice" v-model="prodPrice" :name='"list[" + i + "].prodPrice"' style="width: 250px; margin-right: 20px">
                  <label for="prodImg" style="margin-right: 20px;">상품사진</label>
                  <input type="file" class="input" id="prodImg" :name='"prodImgList"' style="width: 250px;">
                </div>
    </main>
</template>
<script>
export default {
    components: {},
    props : {
        i : Number,
        classId : Number,
    },
    data() {
        return {
            prodName : "",
            prodPrice : "",
    }
},
    beforeCreate() {},
    created() {},
    beforeMount() {},
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeUnmount() {},
    unmounted() {},
    methods: {}
}
</script>
<style scoped>
.container {
    margin: 20px;
}

.label {
  font-size: 17px;
  display: block;
}

.input {
  padding: 10px;
  width: 1200px;
  border: 1px solid lightgrey;
  margin-top: 10px;
  margin-bottom: 25px;
}
</style>