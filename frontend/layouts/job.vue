<template>
  <div class="blue-skin">
    <div id="man-wrapper">

      <TopHeader2/>
      <PageTitle/>
      <Nuxt />
      <Footer/>
      <LoginModal/>
    </div>
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import PageTitle from "@/components/PageTitle";
import AppLogo from "@/components/AppLogo";
import TopHeader2 from "@/components/TopHeader2";
import LoginModal from "@/components/modals/LoginModal";

export default {
  name: "job",

  components: {LoginModal, TopHeader2, AppLogo, PageTitle, Footer}
}
</script>

<style scoped>

</style>
