<template>
  <div>
    <SnsSidebar />
    <Collection />
  </div>
</template>
<script>
    import SnsSidebar from "@/components/sns/Common/SnsSidebar.vue";
    import Collection from "@/components/sns/Bookmark/Collection.vue";
    export default {
      name: "BookmarkMain",
      components: { SnsSidebar, Collection },
    };
</script>
  