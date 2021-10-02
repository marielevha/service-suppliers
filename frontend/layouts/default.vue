<template>
  <div class="blue-skin">
    <div id="man-wrapper">
      <TopHeader/>
      <Nuxt />
      <Footer/>
      <LoginModal/>
      <ResumeModal/>
    </div>
  </div>
</template>
<script>
import TopHeader from "@/components/TopHeader";
import Footer from "@/components/Footer";
import LoginModal from "@/components/modals/LoginModal";
import ResumeModal from "@/components/modals/ResumeModal";
export default {
  name: "default",
  components: {ResumeModal, LoginModal, Footer, TopHeader}
}
</script>

<style scoped>

</style>
