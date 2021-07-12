<template>
  <!-- Category Section Start -->
  <section class="gray-light">
    <div class="container">

      <div class="row justify-content-center">
        <div class="col-lg-7 col-md-9">
          <div class="sec-heading">
            <h2>Popular Jobs <span class="theme-cl-2">Category</span></h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
              labore et dolore magna aliqua.</p>
          </div>
        </div>
      </div>

      <div class="row justify-content-center">

        <div v-for="category in categories" :key="category.id" class="col-lg-3 col-md-4 col-sm-6">
          <div class="urip_cated shadow">
            <div class="urip_cated_avater">
              <i class="ti-car"></i>
            </div>
            <div class="urip_cated_caps">
              <h3 class="cats_urip_title">
                <a href="#">{{ category.title }}</a>
              </h3>
              <span>100k Jobs Found</span>
            </div>
          </div>
        </div>

      </div>
    </div>
  </section>
  <!-- Category Section End -->
</template>

<script>
export default {
  name: "LandingCategories",

  data() {
    return {
      categories: [],
    }
  },

  mounted() {
    this.fetchCategories()
  },

  created() {
    console.log("CREATED")
  },

  computed: {

  },

  methods: {
    async fetchCategories() {
      await this.$axios.$get(
        'http://localhost:9990/categories/',
        {
          headers: this.$store.state.headers
        }
      )
        .then(value => {
          console.log(value)
          this.categories = value.content
        })
    }
  }
}
</script>

<style scoped>

</style>
