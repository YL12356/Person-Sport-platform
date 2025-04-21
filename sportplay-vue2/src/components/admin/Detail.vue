<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>后台管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/introduction' }">运动科普</el-breadcrumb-item>
      <el-breadcrumb-item v-if="introduction.title">{{ introduction.title }}</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 运动详情主体部分 -->
    <el-card v-if="introduction.title" style="margin-top: 20px;">
      <el-image :src="introduction.img" fit="cover" :lazy="true" :alt="introduction.title" class="image"
        @error="setDefaultImg">
        <div slot="error">
          <i class="el-icon-picture-outline"></i>
        </div>
      </el-image>
      <h1 class="title">{{ introduction.title }}</h1>
      <p class="content">{{ introduction.content }}</p>
      <el-button type="primary" icon="el-icon-back" @click="$router.push({ name: 'Introduction' })"
        style="margin-top: 20px; width: auto; background-color: #192a56;">
        返回
      </el-button>
    </el-card>
    <div v-else>
      <p>加载中...</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  created() {
    this.getIntroduction();
  },
  data() {
    return {
      introduction: {},
    };
  },
  methods: {
    // 根据ID获取简介
    async getIntroduction() {
      try {
        const id = this.$route.params.id;
        const { data: res } = await axios.get(`/Introduction/${id}`);
        this.introduction = res;
      } catch (error) {
        console.error('无法获取运动简介详情', error);
        this.introduction = { title: '加载失败' }; // 设置默认标题以显示错误
      }
    },
    // 图片加载失败时的默认图片
    setDefaultImg(event) {
      event.target.src = '/default-img.png'; // 默认图片路径
    },
  },
};
</script>

<style scoped>
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 17px;
}

.image {
  width: 100%;
  height: 500px;
  /* 固定高度 */
  margin-bottom: 20px;
  display: block;
}

.title {
  font-size: 24px;
  color: #303133;
}

.content {
  white-space: pre-wrap;
  /* 保留文本中的换行符 */
  margin-bottom: 20px;
}

.el-button {
  margin-top: 20px;
  width: auto;
  /* 使按钮宽度自适应内容 */
  background-color: #637ab1;
  color: white;
  /* 确保按钮文字颜色可读 */
}
</style>
