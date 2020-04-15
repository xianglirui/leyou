<template>
  <div>
    <blog-head/>
    <blog-content/>
  </div>
</template>

<script>
  import BlogHead from "./BlogHead";
  import BlogContent from "./BlogContent";
  export default {
    name: "BlogAll",
    components:{
      BlogHead,
      BlogContent
    }
  }
</script>

<style scoped>

</style>
