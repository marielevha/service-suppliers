export default {
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'frontend',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      { rel: 'stylesheet', type: 'text/css', href: '/assets/css/main.css' },
      { rel: 'stylesheet', type: 'text/css', href: '/assets/css/styles.css' },
      { rel: 'stylesheet', type: 'text/css', href: '/assets/css/plugins.css' },
    ],
    script: [
      { type: 'text/javascript', src: '/assets/js/jquery.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/bootstrap.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/popper.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/custom.js', body: true },
      { type: 'text/javascript', src: '/assets/js/metisMenu.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/materialize.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/counterup.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/owl.carousel.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/select2.min.js', body: true },
      { type: 'text/javascript', src: '/assets/js/ion.rangeSlider.min.js', body: true },
    ]
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [
    '~/assets/css/main.css',
    //'~/assets/css/styles.css',
    //'~/assets/css/plugins.css',
  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    '~/plugins/others-vue-modules.js'
    //'@/plugins/others-vue-modules'
    //{ src: '~/plugins/others-vue-modules.js', mode: 'server' }
  ],

  mode: 'spa',

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    // https://go.nuxtjs.dev/pwa
    '@nuxtjs/pwa',
    '@nuxtjs/moment',
  ],

  // Axios module configuration: https://go.nuxtjs.dev/config-axios
  axios: {
    baseURL: 'http://localhost:9990',
    proxy: true
  },

  /*proxy: {
    '/categories': { target: 'http://localhost:9990/categories' }
  },*/

  // PWA module configuration: https://go.nuxtjs.dev/pwa
  pwa: {
    manifest: {
      lang: 'en'
    }
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
    //transpile: ['vue-currency-input']
  },

  router: {
    extendRoutes(routes, resolve) {
      routes.push({
        name: 'job_details',
        path: '/Jobs/DetailsJob/:slug',
        component: resolve(__dirname, 'pages/Jobs/DetailsJob.vue')
      })
    }
  }
}
