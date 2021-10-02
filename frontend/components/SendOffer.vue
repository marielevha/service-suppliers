<template>
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
                <input v-model="offer.price" type="number" class="form-control">
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
                <input v-model="offer.daysToComplete" type="number" class="form-control">
              </div>
            </div>
          </div>
        </div>

        <div class="form-row">
          <div class="col-lg-12 col-md-6 col-sm-12">
            <div class="form-group">
              <label>Start date</label>
              <div class="input-group">
                <div class="input-group-prepend">
                  <span class="input-group-text">&</span>
                </div>
                <input v-model="offer.startDate" type="datetime-local" class="form-control">
              </div>
            </div>
          </div>
          <div class="col-lg-12 col-md-6 col-sm-12">
            <div class="form-group">
              <label>End date</label>
              <div class="input-group">
                <div class="input-group-prepend">
                  <span class="input-group-text">&</span>
                </div>
                <input v-model="offer.endDate" type="datetime-local" class="form-control">
              </div>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label>Cover Letter</label>
          <textarea v-model="offer.description" class="form-control" rows="5"></textarea>
        </div>

        <div class="form-group">
          <button @click.prevent="sendOffer()" type="button" class="btn_applynow">Send your offer</button>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "SendOffer",

  props: [
    'job'
  ],

  data() {
    return {
      offer: {
        job: null,
        price: 0,
        daysToComplete: 0,
        title: 'JOB OFFER',
        description: '',
        startDate: this.$moment().format('YYYY-MM-DDTHH:mm'),
        endDate: this.$moment().format('YYYY-MM-DDTHH:mm'),
      }
    }
  },

  mounted() {
    this.offer.job = this.job
    this.offer.title = this.job.title
  },

  methods: {
    async sendOffer() {
      await this.$axios.$post(
        'http://localhost:9997/offers',
        this.offer,
        {
          headers: this.$store.state.headers
        }
      )
      .then(response => {
        //Alert success ...
        //Clear all input ...
        console.log(response)
      })
    },

    clearAll() {
      this.offer.price = 0
      this.offer.daysToComplete = 0
      this.offer.description = ''
      this.offer.startDate = null
      this.offer.endDate = null
    }
  }
}
</script>

<style scoped>

</style>
