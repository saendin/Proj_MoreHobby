<template>
  <div>
    <h1>결제 실패 {{ classId }}</h1>
  </div>
</template>

<script>
export default {
    props: {
        resultInfo: {
          type: Object
        },
        classId: {
          type: String
        },
        classInfo: {
          type: Object
        },
        date: {
          type: Date
        }
    }
}
</script>

<style>

</style>