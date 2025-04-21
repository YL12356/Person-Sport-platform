<template>
  <div>
    <!-- 欢迎信息 -->
    <h1>爱运动 爱自由</h1>
    <p>欢迎来到个人运动平台！</p>
    <!-- 走马灯组件 -->
    <el-carousel :interval="2500" arrow="always" style="width: 100%; margin-bottom: 20px;" direction="vertical">
      <el-carousel-item v-for="item in carouselItems" :key="item.id">
        <img :src="item.image" alt="走马灯图片" style="width: 100%; height: 100%; object-fit: cover;" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  data() {
    return {
      carouselItems: [
        { id: 1, image: require('../assets/img/bike.jpg') },
        { id: 2, image: require('../assets/img/fitness.jpg') },
        { id: 3, image: require('../assets/img/yoga.jpg') },
        { id: 4, image: require('../assets/img/swim.jpg') },
        { id: 5, image: require('../assets/img/football.jpg') },
      ],
    };
  },
};
</script>

<style lang="less" scoped>
/* 走马灯样式 */
.el-carousel {
  height: 520px;
}

.el-carousel__container {
  height: 100%;
  /* 确保走马灯容器的高度为 100% */
}

.el-carousel__item {
  height: 520px;
  /* 确保走马灯项目的高度与走马灯容器一致 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.el-carousel__item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 欢迎信息样式 */
h1 {
  font-size: 50px;
  margin-bottom: 5px;
  margin-top: 0;
  text-align: center; 
  font-family: 'Arial Black', sans-serif;/* 使用更醒目的字体 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);/* 添加阴影效果 */
}

p {
  font-size: 16px;
  color: #606266;
  text-align: center; 
  margin-bottom: 10px;
  margin-top: 0;
}
</style>
