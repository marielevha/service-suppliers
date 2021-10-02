<template>
  <div class="modal fade" id="login" tabindex="-1" role="dialog" aria-labelledby="registermodal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered login-pop-form" role="document">
      <div class="modal-content" id="registermodal">
        <div class="modal-header">
          <h4>Sign In</h4>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="ti-close"></i></span></button>
        </div>
        <div class="modal-body">

          <div class="login-form">
            <form>

              <div class="form-group">
                <label for="email">User Name</label>
                <input v-model="auth.email" id="email" type="email" class="form-control" placeholder="john@gmail.com">
              </div>

              <div class="form-group">
                <label for="password">Password</label>
                <input v-model="auth.password" id="password" type="password" class="form-control" placeholder="*******">
              </div>

              <div class="form-group">
                <button @click.prevent="login" type="submit" class="btn dark-2 btn-md full-width pop-login">Login</button>
              </div>

            </form>
          </div>

          <div class="form-group text-center">
            <span>Or Signin with</span>
          </div>

          <div class="social_logs mb-4">
            <ul class="shares_jobs text-center">
              <li><a href="#" class="share fb"><i class="fa fa-facebook"></i></a></li>
              <li><a href="#" class="share gp"><i class="fa fa-google"></i></a></li>
              <li><a href="#" class="share ln"><i class="fa fa-linkedin"></i></a></li>
            </ul>
          </div>

        </div>
        <div class="modal-footer">
          <div class="mf-link"><i class="ti-user"></i>Haven't An Account?<a href="javascript:void(0)" class="theme-cl"> Sign Up</a></div>
          <div class="mf-forget"><a href="#"><i class="ti-help"></i>Forget Password</a></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: "LoginModal",

  data() {
    return {
      auth: {
        email: "",
        password: ""
      }
    }
  },

  computed: {
    ...mapGetters([
      'user/getAccessToken',
      'user/getRefreshToken',
    ])
  },

  methods: {
    async login() {
      await this.$axios.$post(
        'http://localhost:9999/login',
        this.auth,
        /*{
          headers: this.$store.state.headers
        }*/
      )
      .then(response => {
        localStorage.setItem('access_token', response.access_token)
        localStorage.setItem('refresh_token', response.refresh_token);
        //Alert success ...
        //Clear all input ...
        console.log(response)
      })
    },
  }
}
</script>

<style scoped>

</style>
