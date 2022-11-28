<template>
  <div>
    <v-btn @click="print">
        인쇄하기
    </v-btn>
  </div>
</template>

<script>
export default {
    methods: {
        print() {
            window.print();
        },
    }
}
</script>

<style>

</style>