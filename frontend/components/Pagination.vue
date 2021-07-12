<template>
  <div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12">
      <ul class="pagination">
        <li v-if="data.totalPages > 1" class="page-item">
          <a @click="navigate(currentPage - 1)" :class="['page-link', data.first === true ? 'disabled' : '']" :href="jsVoid" aria-label="Previous">
            <span class="ti-arrow-left"></span>
            <span class="sr-only">Previous</span>
          </a>
        </li>

        <li v-for="i in data.totalPages" :class="['page-item', (data.currentPage + 1) == i ? 'active' : '']">
          <a @click="navigate(i-1)" class="page-link" :href="jsVoid">{{ i }}</a>
        </li>

        <li v-if="data.totalPages > 1" class="page-item">
          <a @click="navigate(currentPage + 1)" :class="['page-link', data.last === true ? 'disabled' : '']" :href="jsVoid" aria-label="Next">
            <span class="ti-arrow-right"></span>
            <span class="sr-only">Next</span>
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "Pagination",

  props: [
    'data'
  ],

  data() {
    return {
      currentPage: 0,
      jsVoid: 'javascript:void(0);'
    }
  },

  methods: {
    navigate(currentPage) {
      this.currentPage = currentPage
      this.$emit('navigate', currentPage)
    }
  }
}
</script>

<style scoped>

</style>
