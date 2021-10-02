<template>
  <div>
    <div class="header header-transparent dark-text">
      <div class="container">
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12">
            <nav id="navigation" class="navigation navigation-landscape">
              <div class="nav-header">
                <AppLogo/>
                <div class="nav-toggle"></div>
              </div>
              <div class="nav-menus-wrapper">
                <ul class="nav-menu">

                  <li class="active">
                    <NuxtLink to="/">
                      Home
                    </NuxtLink>
                  </li>

                  <li>
                    <NuxtLink to="/Jobs/SearchJob">
                      Explore
                      <span class="submenu-indicator"></span>
                    </NuxtLink>
                    <ul class="nav-dropdown nav-submenu">
                      <li>
                        <NuxtLink to="/Jobs/SearchJob">
                          Search Job
                        </NuxtLink>
                      </li>
                      <li>
                        <NuxtLink to="/">
                          Services
                        </NuxtLink>
                      </li>
                      <li>
                        <NuxtLink to="/">
                          Freelancers
                          <span class="submenu-indicator"></span>
                        </NuxtLink>
                        <ul class="nav-dropdown nav-submenu">
                          <li>
                            <NuxtLink to="/">Search Freelancers</NuxtLink>
                          </li>
                          <li>
                            <NuxtLink to="/">Search Freelancers 02</NuxtLink>
                          </li>
                          <li>
                            <NuxtLink to="/">Search Freelancers List</NuxtLink>
                          </li>
                          <li>
                            <NuxtLink to="/">Freelancer Detail</NuxtLink>
                          </li>
                        </ul>
                      </li>
                    </ul>
                  </li>

                  <li>
                    <NuxtLink to="/">Dashboard</NuxtLink>
                  </li>

                  <li>
                    <NuxtLink to="/">
                      Pages
                      <span class="submenu-indicator"></span>
                    </NuxtLink>
                    <ul class="nav-dropdown nav-submenu">
                      <li><NuxtLink to="/">About Us</NuxtLink></li>
                      <li><NuxtLink to="/">Blogs Page</NuxtLink></li>
                      <li><NuxtLink to="/">Blog Detail</NuxtLink></li>
                      <li><NuxtLink to="/">Pricing</NuxtLink></li>
                      <li><NuxtLink to="/">Contact Us</NuxtLink></li>
                      <li><NuxtLink to="/">Components</NuxtLink></li>
                      <li><NuxtLink to="/">FAQ's</NuxtLink></li>
                      <li><NuxtLink to="/">CheckOut</NuxtLink></li>
                    </ul>
                  </li>

                </ul>

                <ul class="nav-menu nav-menu-social align-to-right">
                  <li>
                    <NuxtLink to="/" data-toggle="modal" data-target="#upload-resume">
                      <i class="fa fa-upload mr-1"></i>Upload Resume
                    </NuxtLink>
                  </li>

                  <li class="add-listing dark-bg">
                    <NuxtLink v-if="access_token == null || access_token === ''" to="/" data-toggle="modal" data-target="#login">
                      <i class="ti-user mr-1"></i> Sign in
                    </NuxtLink>
                    <a v-else href="javascript:void(0);" data-toggle="modal" data-target="#login">
                      <i class="ti-user mr-1"></i> Carols DENVER
                    </a>
                  </li>
                </ul>
              </div>
            </nav>
          </div>
        </div>
      </div>
    </div>

    <div class="clearfix"></div>
  </div>
</template>

<script>
import AppLogo from "@/components/AppLogo";
export default {
  name: "TopHeader",

  components: {AppLogo},

  data() {
    return {
      access_token: localStorage.getItem('access_token'),
    }
  },

  mounted() {
    this.access_token = localStorage.getItem('access_token')
  },

  methods: {
  }
}
</script>

<style scoped>

</style>
