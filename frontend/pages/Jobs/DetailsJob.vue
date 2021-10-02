<template>
  <div>
    <!-- ============================ Main Section Start ================================== -->
    <section>
      <div class="container">
        <div v-if="job != null" class="row">

          <div class="col-lg-8 col-md-12 col-sm-12">
            <div class="_job_detail_box">

              <div class="_jobs_details_single mb-4">
                <div class="_jb_details01">

                  <div class="_jb_details01_flex">
                    <div class="_jb_details01_authors">
                      <img src="/assets/img/c-7.png" class="img-fluid" alt="" />
                    </div>
                    <div class="_jb_details01_authors_caption">
                      <h4 class="jbs_title">{{ job.title }}<img src="/assets/img/verify.svg" class="ml-1" width="12" alt=""></h4>
                      <ul class="jbx_info_list">
                        <li><span><i class="ti-briefcase"></i>InVision</span></li>
                        <li><span><i class="ti-credit-card"></i>$35k-50k PA</span></li>
                        <li><span><i class="ti-location-pin"></i>{{ job.city.name }}</span></li>
                        <li><span><i class="ti-timer"></i>{{ $moment(job.publishedAt, "YYYY-MM-DD").fromNow() }}</span></li>
                      </ul>
                      <ul class="jbx_info_list">
                        <li v-for="tag in job.tags" :key="tag.id">
                          <span class="jb_types fulltime">
                            {{ tag.text }}&nbsp;&nbsp;
                            <i class="fa fa-tag"></i>
                          </span>
                        </li>
                      </ul>
                    </div>
                  </div>

                  <div class="_jb_details01_last">
                    <ul class="_flex_btn">
                      <li><a href="#" class="_saveed_jb"><i class="fa fa-heart"></i></a></li>
                    </ul>
                  </div>

                </div>
              </div>

              <div class="_job_detail_single">
                <h4>Job Summary</h4>
                <p>{{ job.description }}</p>
              </div>

              <div class="_job_detail_single">
                <h4>Job Duties:</h4>
                <p>{{ job.description }}</p>
                <ul>
                  <li>Other duties as requested</li>
                </ul>
              </div>

              <div class="_job_detail_single">
                <h4>Skill & Experience</h4>
                <ul>
                  <li>Need 3+ EXPERIENCE IN Web Designing</li>
                </ul>
              </div>
            </div>
          </div>

          <SendOffer :job="job"/>

        </div>
      </div>
    </section>
    <!-- ============================ Main Section End ================================== -->

  </div>
</template>

<script>
import SendOffer from "@/components/SendOffer";
export default {
  name: "DetailsJob",
  components: {SendOffer},
  layout: 'job',

  data() {
    return {
      slug: this.$route.params.slug,
      job: null
    }
  },

  mounted() {
    console.log(this.slug)
    this.fetchJobBySlug()
  },

  methods: {
    async fetchJobBySlug() {
      await this.$axios.$get(
        `http://localhost:9997/jobs/${this.slug}/one`,
        {
          headers: this.$store.state.headers
        }
      )
        .then(value => {
          console.log(value)
          this.job = value
        })
    },
  }
}
</script>

<style scoped>

</style>
