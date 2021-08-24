<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <p>Designed by
        <strong>
          <router-link to="/" class="me-login-design-color">ADKCodeXD</router-link>
        </strong>
      </p>
    </div>
  </el-footer>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {},
    mounted() {
    }
  }
</script>

<style>

  .el-footer {
    min-width: 100%;
    box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: fixed;
    bottom: 0;
    left: 0;
    z-index: 1024;
  }

  .me-footer {
    text-align: left;
    line-height: 40px;
    font-family: 'Open Sans', sans-serif;
    font-size: 10px;

  }

  .me-login-design-color {
    color: #ec5e1c !important;
  }

</style>
