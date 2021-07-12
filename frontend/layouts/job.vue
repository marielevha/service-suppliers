<template>
  <div class="blue-skin">
    <div id="man-wrapper">

      <TopHeader2/>
      <PageTitle/>
      <Nuxt />
      <Footer/>
    </div>
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import PageTitle from "@/components/PageTitle";
import AppLogo from "@/components/AppLogo";
import TopHeader2 from "@/components/TopHeader2";

export default {
  name: "job",

  components: {TopHeader2, AppLogo, PageTitle, Footer}
}
</script>

<style scoped>

</style>
