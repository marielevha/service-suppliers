<template>
  <div class="blue-skin">
    <div id="man-wrapper">
      <TopHeader/>
      <Nuxt />
      <Footer/>
    </div>
  </div>
</template>
<script>
import TopHeader from "@/components/TopHeader";
import Footer from "@/components/Footer";
export default {
  name: "default",
  components: {Footer, TopHeader}
}
</script>

<style scoped>

</style>
