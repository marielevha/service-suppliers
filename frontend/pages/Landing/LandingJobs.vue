<template>
  <section class="min-sec">
    <div class="container">

      <div class="row justify-content-center">
        <div class="col-lg-7 col-md-9">
          <div class="sec-heading">
            <h2>Top Featured <span class="theme-cl-2">Jobs</span></h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
              labore et dolore magna aliqua.</p>
          </div>
        </div>
      </div>

      <div class="row justify-content-center">

        <div v-for="job in jobs" :key="job.id" class="col-xl-3 col-lg-4 col-md-4 col-sm-6">
          <div class="job_grid_02">
            <div class="jobs-like">
              <label class="toggler toggler-danger">
                <input type="checkbox">
                <i class="fa fa-heart"></i>
              </label>
            </div>
            <div class="jb_types internship">{{ job.category.title }}</div>
            <div class="jb_grid_01_thumb">
              <NuxtLink to="/Jobs/SearchJob">
                <img src="/assets/img/c-3.png" class="img-fluid" alt="">
              </NuxtLink>
            </div>
            <div class="jb_grid_01_caption">
              <h4 class="_jb_title">
                <NuxtLink to="/Jobs/SearchJob">
                  {{ job.title }}
                </NuxtLink>
              </h4>
              <div class="_emp_jb">A.K. Infra Technology</div>
            </div>
            <div class="jb_grid_01_footer">
              <NuxtLink to="/Jobs/SearchJob" class="_jb_apply">
                View Job
              </NuxtLink>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12">
          <div class="mt-3 text-center">
            <NuxtLink to="/Jobs/SearchJob" class="_browse_more-2 light">Browse More Jobs</NuxtLink>
          </div>
        </div>
      </div>

    </div>
  </section>
</template>

<script>
export default {
  name: "LandingJobs",

  data() {
    return {
      jobs: [],
    }
  },

  mounted() {
    this.fetchJobs()
  },

  methods: {
    async fetchJobs() {
      await this.$axios.$post(
        'http://localhost:9997/jobs_data',
        {
          published: true,
          deleted: false,
          column: "createdAt",
          ascending: true,
          start: "",
          end: "",
          query: "query"
        },
        {
          headers: this.$store.state.headers
        }
      )
      .then(value => {
        console.log(value)
        this.jobs = value.content
      })
    }
  }
}
</script>

<style scoped>

</style>
