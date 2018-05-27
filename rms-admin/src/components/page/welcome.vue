<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-setting"></i>前言</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="ms-doc">
            <article>
                <h1>欢迎使用权限管理系统</h1>
            </article>
        </div>

    </div>
</template>

<script>
export default {
  data: function () {
    return {};
  },
  methods: {

  }
};
</script>

<style scoped>
    .ms-doc{
        width:100%;
        max-width: 1030px;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif;
    }
    .ms-doc article{
        padding: 30px;
        word-wrap: break-word;
        background-color: #fff;
        border: 1px solid #ddd;
        border-bottom-right-radius: 3px;
        border-bottom-left-radius: 3px;
    }
    .ms-doc article h1{
        font-size:32px;
        padding-bottom: 10px;
        margin: 0 auto;
        text-align: center;
    }
</style>
