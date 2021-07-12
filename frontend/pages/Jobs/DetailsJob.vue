<template>
  <div>
    <!-- ============================ Main Section Start ================================== -->
    <section>
      <div class="container">
        <div class="row">

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
                        <li><span><i class="ti-location-pin"></i>Canada, USA</span></li>
                        <li><span><i class="ti-timer"></i>10 min ago</span></li>
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

          <div class="col-lg-4 col-md-12 col-sm-12">

            <div class="_jb_summary">
              <h4>Intrested in this job?</h4>

              <div class="_avg_res_time">
                <div class="_avg_res_icon">
                  <i class="fa fa-bolt"></i>
                </div>
                <div class="_avg_res_caption">
                  <h5>Fast Response Time</h5>
                  <span>Avg. response time: 02 days</span>
                </div>
              </div>

              <div class="_apply_form_form">
                <div class="form-row">
                  <div class="col-lg-6 col-md-6 col-sm-12">
                    <div class="form-group">
                      <label>Your Price</label>
                      <div class="input-group">
                        <div class="input-group-prepend">
                          <span class="input-group-text">$</span>
                        </div>
                        <input type="text" class="form-control">
                      </div>
                    </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12">
                    <div class="form-group">
                      <label>Days To Complete</label>
                      <div class="input-group">
                        <div class="input-group-prepend">
                          <span class="input-group-text">Days</span>
                        </div>
                        <input type="text" class="form-control">
                      </div>
                    </div>
                  </div>
                </div>

                <div class="form-group">
                  <label>Cover Letter</label>
                  <textarea class="form-control" rows="5"></textarea>
                </div>

                <div class="form-group">
                  <button type="button" class="btn_applynow">Apply For This Job</button>
                </div>

              </div>
            </div>

          </div>

        </div>
      </div>
    </section>
    <!-- ============================ Main Section End ================================== -->

    <!-- ============================ Call To Action Start ================================== -->
    <section class="call-to-act" style="background:#2944c1 url(/assets/img/landing-bg.png) no-repeat">
      <div class="container">
        <div class="row justify-content-center">

          <div class="col-lg-7 col-md-8">
            <div class="clt-caption text-center mb-4">
              <h3 class="text-light">Subscribe Now!</h3>
              <p class="text-light">Simple pricing plans. Unlimited web maintenance service</p>
            </div>
            <div class="inner-flexible-box subscribe-box">
              <div class="input-group">
                <input type="text" class="form-control large" placeholder="Enter your mail here">
                <button class="btn btn-subscribe bg-dark" type="button"><i class="fa fa-arrow-right"></i></button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- ============================ Call To Action End ================================== -->

  </div>
</template>

<script>
export default {
  name: "DetailsJob",
  layout: 'job',

  data() {
    return {
      job: this.$route.params.job
    }
  }
}
</script>

<style scoped>

</style>
