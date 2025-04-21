<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>运动平台</el-breadcrumb-item>
      <el-breadcrumb-item>运动科普</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 运动列表主体部分 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="12" v-for="intro in introductions" :key="intro.kid">
          <router-link :to="{ name: 'Detail', params: { id: intro.kid } }">
            <el-card class="box-card">
              <el-image
                :src="intro.img"
                fit="cover"
                :lazy="true"
                :alt="intro.title"
                class="image"
                @error="setDefaultImg"
              >
                <div slot="error">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
              <div>
                <el-link :underline="false" class="title">{{ intro.title }}</el-link>
                
              </div>
            </el-card>
          </router-link>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  created() {
    this.getIntroductions();
  },
  data() {
    return {
      introductions: [], // 运动列表
    };
  },
  methods: {
    // 获取运动列表
    async getIntroductions() {
      try {
        const { data: res } = await axios.get('/Introduction/list');
        this.introductions = res;
      } catch (error) {
        console.error('无法获取运动简介列表', error);
      }
    },
    // 图片加载失败时的默认图片
    setDefaultImg(event) {
      event.target.src = '/default-img.png'; // 默认图片路径
    },
  },
};
</script>

<style lang="less" scoped>
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 17px;
}

.el-col {
  @media (max-width: 768px) {
    flex: 0 0 100%; // 在移动端显示为一行一张图片
  }
}

.box-card {
  margin-bottom: 20px;
  text-align: center;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); // 初始状态的阴影
  transition: box-shadow 0.3s ease; // 添加过渡效果
}

.image {
  width: 100%;
  height: 375.05px; /* 固定高度 */
  display: block;
  cursor: pointer;
  object-fit: cover; /* 确保图片适应容器 */
}

.title {
  margin-top: 10px;
  font-size: 18px; // 增加字体大小
  color: #555555; // 更柔和的颜色，更易读
  font-family: 'Roboto', sans-serif; // 使用现代字体
  font-weight: bold; // 加粗字体
  line-height: 1.4; // 设置行高
  padding: 10px; // 增加内边距
  text-decoration: none !important; // 去掉下划线
}
</style>
