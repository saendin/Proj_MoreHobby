<template>
    <div ref="trigger">-</div> <!--1. 보여주는 div를 trigger이라는 ref로 설정해준다.-->
</template>
<script>
import { defineComponent, onMounted, onUnmounted, ref } from 'vue';

export default defineComponent({
  setup(props, { emit }) {
    const trigger = ref();
    const options = {
      root: null,
      threshold: 1 // 해당 컴포넌트가 화면에 얼마나 보였을 때 감지할 것인지
                   // 1 = 100%, 01 = 10%
    };
    let observer = null;
    const handleIntersect = (entry) => {
      if (entry.isIntersecting) emit('triggerIntersected', trigger.value); //triggerIntersected
    };

    onMounted(() => { //observer등록
      try {
        observer = new IntersectionObserver((entries) => {  
          handleIntersect(entries[0]); //옵저버가 감지되면 handleIntersect이라는 함수를 실행
                                       // 그 함수는 부모로 triggerIntersected 이벤트를 emit으로 보낸다.
        }, options);
        observer.observe(trigger.value);
      } catch (err) {
        console.error(err);
      }
    });

    onUnmounted(() => {
      observer.value.disconnect(); //observer 연결 해제
    });

    return {
      trigger
    };
  }
});
</script>