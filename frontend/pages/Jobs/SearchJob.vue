<template>
  <div>
    <!-- ============================ Main Section Start ================================== -->
    <section class="gray-bg">
      <div class="container">
        <div class="row">

          <div class="col-lg-4 col-md-12 col-sm-12">
            <div class="simple-sidebar sm-sidebar">

              <div class="search-sidebar_header">
                <h4 class="ssh_heading">Search Filter</h4>
                <a @click.prevent="clearAll()" href="javascript:void(0);" class="clear_all">Clear All</a><a href="#search_open" data-toggle="collapse" aria-expanded="false" role="button" class="collapsed _filter-ico"><i class="fa fa-sliders"></i></a>
              </div>

              <!-- Find New Property -->
              <div class="sidebar-widgets collapse miz_show" id="search_open" data-parent="#search_open">

                <div class="search-inner p-0">

                  <div class="filter-search-box pb-0">
                    <div class="form-group">
                      <input v-model="filter.query" @keyup="searchByText()" type="text" class="form-control" placeholder="Search by text...">
                    </div>
                    <div class="form-group">
                      <input v-model="tagEntry" @keyup="addTag()" type="text" class="form-control" placeholder="Search by keywords...">
                    </div>
                  </div>

                  <div class="filter_wraps">
                    <!-- Job Tags Search -->
                    <div v-if="filter.tags.length > 0" class="single_search_boxed">
                      <div class="featured-category dark">
                        <ul>
                          <li v-for="(tag, index) in filter.tags" :key="index">
                            <a @click.prevent="removeTag(tag)" href="javascript:void(0);" data-toggle="tooltip" :data-original-title="tag">
                              {{ tag }}
                              <span>
                                <i class="fa fa-tag"></i>
                              </span>
                            </a>
                          </li>
                        </ul>
                      </div>
                    </div>

                    <!-- Job categories Search -->
                    <div class="single_search_boxed">
                      <div class="widget-boxed-header">
                        <h4>
                          <a href="#categories" data-toggle="collapse" aria-expanded="true" role="button">Job Categories</a>
                        </h4>

                      </div>
                      <div class="widget-boxed-body collapse show" id="categories" data-parent="#categories">
                        <div class="side-list no-border">
                          <!-- Single Filter Card -->
                          <div class="single_filter_card">
                            <div class="card-body pt-0">
                              <div class="inner_widget_link">
                                <ul class="no-ul-list filter-list">
                                  <li v-for="category in result.categories" :key="category.id">
                                    <input @click="selectCategories(category.id, 'a' + category.id)" :id="['a' + category.id]" class="checkbox-custom" name="ADA" type="checkbox">
                                    <label :for="['a' + category.id]" class="checkbox-custom-label">{{ category.title }}</label>
                                  </li>
                                </ul>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <!-- Job Locations Search -->
                    <div class="single_search_boxed">
                      <div class="widget-boxed-header">
                        <h4>
                          <a href="#locations" data-toggle="collapse" aria-expanded="false" role="button" class="collapsed">Job Locations</a>
                        </h4>

                      </div>
                      <div class="widget-boxed-body collapse" id="locations" data-parent="#locations">
                        <div class="side-list no-border">
                          <!-- Single Filter Card -->
                          <div class="single_filter_card">
                            <div class="card-body pt-0">
                              <div class="inner_widget_link">
                                <ul class="no-ul-list filter-list" id="cities-list">
                                  <li v-for="city in result.cities" :key="city.id">
                                    <input @click="selectCities(city.id, 'a' + city.id)" :id="['b' + city.id]" class="checkbox-custom" name="ADA" type="checkbox">
                                    <label :for="['b' + city.id]" class="checkbox-custom-label">{{ city.name }}</label>
                                  </li>
                                </ul>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="form-group filter_button pt-2">
                    <button type="submit" class="btn btn btn-theme-2 rounded full-width">22 Results Show</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Sidebar End -->

          </div>

          <!-- Item Wrap Start -->
          <div class="col-lg-8 col-md-12 col-sm-12">
            <div class="row">
              <div class="col-lg-12 col-md-12 col-sm-12">
                <!-- Filter Search -->
                <div class="_filt_tag786">
                  <div class="_tag782">
                    <div class="_tag780" v-if="totalElements != null">{{ totalElements }} Items found</div>
                    <div class="_tag780" v-else>Not found items</div>
                  </div>
                  <div class="_tag785">
                    <div class="__g72juy">
                      <a href="search-job-grid-2.html" class="_ujh_tyr"><i class="ti-layout-grid2"></i></a>
                      <a href="search-job-list-2.html" class="_ujh_tyr active"><i class="ti-view-list"></i></a>
                    </div>
                    <div class="_g78juy">
                      <select v-model="filter.order" @change="changeOrderResult()" class="form-control">
                        <option value="publishedAt">Recent Published</option>
                        <option value="more-offers">More Offers</option>
                        <option value="less-offers">Less Offers</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-lg-12 col-md-12 col-sm-12">

                <!-- Single Item -->
                <div v-for="job in jobs" :key="job.id" class="_large_jb_list72 shadow_0">
                  <div class="_large_jb_header">
                    <div class="_list_110">
                      <div class="_list_110_thumb">
                        <a href="employer-detail.html">
                          <img src="/assets/img/c-1.png" class="img-fluid" alt="">
                        </a>
                      </div>
                      <div class="_list_110_caption">
                        <h4 class="_jb_title">
                          <a href="job-detail.html">{{ job.title }}</a>
                        </h4>
                        <div class="_emp_jb">{{ job.user.email }}</div>
                      </div>
                    </div>
                    <div class="_list_right-info">
                      <ul>
                        <li>
                          <NuxtLink :to="{name: 'job_details', params: {slug: job.slug}}" class="_jb_apply theme-bt">View Job</NuxtLink>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="_large_jb_body">
                    <div class="_large_jb_body_list_info">
                      <ul>
                        <li><i class="ti-layout"></i>{{ job.category.title }}</li>
                        <li><i class="ti-timer"></i>{{ $moment(job.publishedAt, "YYYY-MM-DD").fromNow() }}</li>
                        <li><i class="ti-location-pin"></i>{{ job.city.name }}</li>
                        <li><i class="ti-user"></i>{{ job.offers.length }} Offers</li>
                      </ul>
                    </div>
                    <div class="_large_jb_body_list_explain">
                      <p>{{ job.description }}</p>
                      <div class="jb_req_skill">
                        <ul>
                          <li v-for="tag in job.tags" :key="tag.id">
                            <span>{{ tag.text }}&nbsp;<i class="fa fa-tag"></i></span>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
            </div>

            <Pagination v-if="result.pagination.totalElements > filter.size" :data="result.pagination" @navigate="fetchJobs"/>

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
import Pagination from "@/components/Pagination";
export default {
  name: "SearchJob",
  components: {Pagination},
  layout: 'job',

  data() {
    return {
      jobs: [],
      tagEntry: null,
      totalElements: null,
      result: {
        cities: [],
        categories: [],
        pagination: {
          last: true,
          totalPages: 1,
          totalElements: null,
          first: true,
          currentPage: 0,
        }
      },
      order: 'recent-published',
      filter: {
        published: true,
        deleted: false,
        column: "publishedAt",
        order: "publishedAt",
        ascending: true,
        start: "",
        end: "",
        size: 2,
        page: 0,
        query: "",
        cities: [],
        categories: [],
        tags: [],
      }
    }
  },

  mounted() {
    this.fetchJobs()
    this.fetchCities()
    this.fetchCategories()
    //moment("20111031", "YYYYMMDD").fromNow()
  },

  methods: {
    async fetchJobs(page = 0) {
      this.filter.page = page
      await this.$axios.$post(
        'http://localhost:9997/jobs_data',
        this.filter,
        {
          headers: this.$store.state.headers
        }
      )
        .then(value => {
          console.log(value)
          this.jobs = value.content
          this.totalElements = value.totalElements
          this.result.pagination.last = value.last
          this.result.pagination.first = value.first
          this.result.pagination.totalPages = value.totalPages
          this.result.pagination.totalElements = value.totalElements
          this.result.pagination.currentPage = value.number
        })
    },

    async fetchCategories() {
      await this.$axios.$get(
        'http://localhost:9990/categories/',
        {
            headers: this.$store.state.headers
          }
      )
      .then(value => {
        this.result.categories = value.content
      })
    },

    async fetchCities() {
      await this.$axios.$get(
        'http://localhost:9997/cities',
        {
            headers: this.$store.state.headers
          }
      )
      .then(value => {
        this.result.cities = value
      })
    },

    selectCategories(catId, idEl) {
      let el = document.getElementById(idEl)
      if (el.checked) {
        if (this.filter.categories.indexOf(catId) === -1)
          this.filter.categories.push(catId)
      }
      else {
        this.filter.categories = this.filter.categories.filter(value => value != catId)
      }
      this.fetchJobs()
    },

    selectCities(cityId, idEl) {
      let el = document.getElementById(idEl)
      if (el.checked) {
        if (this.filter.cities.indexOf(cityId) === -1)
          this.filter.cities.push(cityId)
      }
      else {
        //let index = this.filter.categories.indexOf(cityId)
        this.filter.cities = this.filter.cities.filter(value => value != cityId)
      }
      this.fetchJobs()
    },

    changeOrderResult() {
      this.fetchJobs();
    },

    searchByText() {
      if (this.filter.query.trim().length > 4) {
        this.fetchJobs()
      }
    },

    addTag() {
      if (this.tagEntry != null && this.tagEntry.includes(';')) {
        let entry = this.tagEntry.slice(0, -1).trim()
        if (this.filter.tags.indexOf(entry) === -1) {
          this.filter.tags.push(entry)
        }
        this.tagEntry = ''
        this.fetchJobs()
      }
    },

    removeTag(tag) {
      this.filter.tags = this.filter.tags.filter(value => value != tag)
      this.fetchJobs()
    },

    clearAll() {
      //Clear All Tags
      this.filter.tags = []
      //Clear Query Search
      this.filter.query = ''
      //Clear Categories
      this.filter.categories = []
      //Clear Cities
      this.filter.cities = []

      //Unselect All CheckBox
      let checkBoxElements = document.querySelectorAll("input[name='ADA']")
      for (let i = 0; i < checkBoxElements.length; i++) {
        checkBoxElements[i].checked = false
      }
      this.fetchJobs(0)
    }
  }

}
</script>

<style scoped>

</style>
