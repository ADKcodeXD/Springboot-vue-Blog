<template>
  <el-card class="box-card" style="border-radius: 20px;background-color: rgb(233, 255, 220);">
    <img src="../../assets/img/touxiang.jpg" style="width: 130px; height: auto; border-radius: 90px; margin: auto;left: 50%;">
    <h1 class="me-author-name">ADK-<small>Blog</small></h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i>东莞&佛山</span>
      <span><i class="me-icon-job"></i> &nbsp;java入门开发</span>
    </div>
    <div class="me-author-tool">
      <span><i @click="showTool(qq)" :title="qq.title" class="iconfont icon-qq"></i>790532173</span>
      <span><i @click="showTool(bilibili)" :title="bilibili.title" class="iconfont icon-bilibili-fill"></i><a href="https://space.bilibili.com/2988768" target="_blank">鉄造廃物</a></span>
    </div>
  </el-card>

</template>

<script>
  export default {
    name: 'CardMe',
    data() {
      return {
        qq: {title: 'QQ', message: '790532173'},
        bilibili: {
          title: '哔哩哔哩',
          message: '<a target="_blank" href="https://space.bilibili.com/473844125">https://space.bilibili.com/473844125</a>'
        }
      }
    },
    methods: {
      showTool(tool) {
        this.$message({
          duration: 0,
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: '<strong>' + tool.message + '</strong>'
        });
      }
    }
  }
</script>

<style scoped>
  .me-author-name {
    text-align: left;
    font-size: 30px;
    border-bottom: 1px solid #5FB878;
  }

  .me-author-description {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: left;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
