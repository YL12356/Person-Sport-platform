<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>运动平台</el-breadcrumb-item>
      <el-breadcrumb-item>营养配餐</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 用户信息输入 -->
    <el-card style="margin-top: 20px;">
      <h1 class="main-title">用户信息输入</h1>
      <el-form :model="userInfo" label-width="120px">
        <el-form-item label="年龄">
          <el-input v-model="userInfo.age" type="number" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="userInfo.gender" placeholder="请选择性别">
            <el-option label="男" value="male"></el-option>
            <el-option label="女" value="female"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="体重 (kg)">
          <el-input v-model="userInfo.weight" type="number" placeholder="请输入体重"></el-input>
        </el-form-item>
        <el-form-item label="身高 (cm)">
          <el-input v-model="userInfo.height" type="number" placeholder="请输入身高"></el-input>
        </el-form-item>
        <el-form-item label="活动水平">
          <el-select v-model="userInfo.activityLevel" placeholder="请选择活动水平">
            <el-option label="低" value="low"></el-option>
            <el-option label="中" value="medium"></el-option>
            <el-option label="高" value="high"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-check" @click="generateMealPlan" class="custom-button">生成配餐计划</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 配餐计划 -->
    <el-card style="margin-top: 20px;" v-if="mealPlan.length > 0">
      <h1 class="main-title">配餐计划</h1>
      <div v-for="(meal, index) in mealPlan" :key="index">
        <h2>{{ meal.time }}</h2>
        <el-table :data="meal.items" style="width: 100%">
          <el-table-column prop="name" label="食材" width="180"></el-table-column>
          <el-table-column prop="calories" label="卡路里" width="180"></el-table-column>
          <el-table-column prop="protein" label="蛋白质" width="180"></el-table-column>
          <el-table-column prop="fat" label="脂肪" width="180"></el-table-column>
          <el-table-column prop="carbs" label="碳水化合物" width="180"></el-table-column>
        </el-table>
        <div class="meal-description">{{ meal.description }}</div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInfo: {
        age: '',
        gender: '',
        weight: '',
        height: '',
        activityLevel: '',
      },
      mealPlan: [],
      mealPlanData: {
        male: {
          low: [
            { time: '早餐', items: [{ name: '燕麦粥', calories: 150, protein: 10, fat: 4, carbs: 25 }, { name: '鸡蛋', calories: 150, protein: 13, fat: 11, carbs: 1 }], description: '富含蛋白质和纤维，有助于饱腹感。' },
            { time: '午餐', items: [{ name: '鸡胸肉沙拉', calories: 200, protein: 30, fat: 2, carbs: 15 }, { name: '全麦面包', calories: 100, protein: 4, fat: 2, carbs: 20 }], description: '高蛋白低脂肪，适合减肥。' },
            { time: '晚餐', items: [{ name: '烤鱼', calories: 250, protein: 30, fat: 10, carbs: 0 }, { name: '蒸蔬菜', calories: 25, protein: 2, fat: 0, carbs: 5 }], description: '低卡路里高营养，适合健康饮食。' },
          ],
          medium: [
            { time: '早餐', items: [{ name: '牛奶燕麦粥', calories: 200, protein: 15, fat: 6, carbs: 30 }, { name: '香蕉', calories: 100, protein: 1, fat: 0, carbs: 27 }], description: '富含蛋白质和维生素，有助于肌肉恢复。' },
            { time: '午餐', items: [{ name: '牛肉炒饭', calories: 350, protein: 20, fat: 12, carbs: 40 }, { name: '黄瓜', calories: 15, protein: 1, fat: 0, carbs: 3 }], description: '营养均衡，适合增肌。' },
            { time: '晚餐', items: [{ name: '豆腐汤', calories: 150, protein: 12, fat: 2, carbs: 20 }, { name: '糙米', calories: 150, protein: 4, fat: 1, carbs: 35 }], description: '低脂肪高纤维，适合减肥。' },
          ],
          high: [
            { time: '早餐', items: [{ name: '酸奶', calories: 150, protein: 15, fat: 6, carbs: 10 }, { name: '蓝莓', calories: 50, protein: 1, fat: 0, carbs: 15 }], description: '富含蛋白质和抗氧化剂，有助于肌肉恢复。' },
            { time: '午餐', items: [{ name: '鸡腿肉', calories: 250, protein: 40, fat: 2, carbs: 0 }, { name: '菠菜', calories: 25, protein: 2, fat: 0, carbs: 3 }], description: '高蛋白低脂肪，适合增肌。' },
            { time: '晚餐', items: [{ name: '三文鱼', calories: 300, protein: 30, fat: 15, carbs: 0 }, { name: '藜麦', calories: 150, protein: 8, fat: 2, carbs: 28 }], description: '富含Omega-3脂肪酸和蛋白质，适合健康饮食。' },
          ],
        },
        female: {
          low: [
            { time: '早餐', items: [{ name: '燕麦粥', calories: 150, protein: 10, fat: 4, carbs: 25 }, { name: '苹果', calories: 50, protein: 1, fat: 0, carbs: 12 }], description: '富含纤维和维生素，有助于饱腹感。' },
            { time: '午餐', items: [{ name: '鸡肉沙拉', calories: 250, protein: 25, fat: 5, carbs: 10 }, { name: '藜麦', calories: 150, protein: 8, fat: 2, carbs: 28 }], description: '高蛋白低脂肪，适合减肥。' },
            { time: '晚餐', items: [{ name: '烤鸡胸肉', calories: 200, protein: 30, fat: 2, carbs: 10 }, { name: '蒸西兰花', calories: 50, protein: 3, fat: 0, carbs: 11 }], description: '低卡路里高营养，适合健康饮食。' },
          ],
          medium: [
            { time: '早餐', items: [{ name: '牛奶燕麦粥', calories: 200, protein: 15, fat: 6, carbs: 30 }, { name: '草莓', calories: 50, protein: 1, fat: 0, carbs: 12 }], description: '富含蛋白质和维生素，有助于肌肉恢复。' },
            { time: '午餐', items: [{ name: '牛肉炒饭', calories: 350, protein: 20, fat: 12, carbs: 40 }, { name: '黄瓜', calories: 15, protein: 1, fat: 0, carbs: 3 }], description: '营养均衡，适合增肌。' },
            { time: '晚餐', items: [{ name: '豆腐汤', calories: 150, protein: 12, fat: 2, carbs: 20 }, { name: '糙米', calories: 150, protein: 4, fat: 1, carbs: 35 }], description: '低脂肪高纤维，适合减肥。' },
          ],
          high: [
            { time: '早餐', items: [{ name: '酸奶', calories: 150, protein: 15, fat: 6, carbs: 10 }, { name: '葡萄', calories: 70, protein: 1, fat: 0, carbs: 18 }], description: '富含蛋白质和抗氧化剂，有助于肌肉恢复。' },
            { time: '午餐', items: [{ name: '鸡腿肉', calories: 250, protein: 40, fat: 2, carbs: 0 }, { name: '菠菜', calories: 25, protein: 2, fat: 0, carbs: 3 }], description: '高蛋白低脂肪，适合增肌。' },
            { time: '晚餐', items: [{ name: '三文鱼', calories: 300, protein: 30, fat: 15, carbs: 0 }, { name: '藜麦', calories: 150, protein: 8, fat: 2, carbs: 28 }], description: '富含Omega-3脂肪酸和蛋白质，适合健康饮食。' },
          ],
        },
      },
    };
  },
  methods: {
    generateMealPlan() {
      const age = parseFloat(this.userInfo.age);
      const weight = parseFloat(this.userInfo.weight);
      const height = parseFloat(this.userInfo.height);

      if (isNaN(age) || isNaN(weight) || isNaN(height) || !this.userInfo.gender || !this.userInfo.activityLevel) {
        this.$message.error('请输入有效的信息');
        return;
      }

      // 根据用户信息生成配餐计划
      this.mealPlan = this.mealPlanData[this.userInfo.gender][this.userInfo.activityLevel];
    },
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

.el-card {
  margin-top: 20px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); /* 初始状态的阴影 */
  transition: box-shadow 0.3s ease; /* 添加过渡效果 */
}

.main-title {
  font-size: 24px;
  color: #303133;
  margin-bottom: 20px;
}

.nutrient-image {
  width: 383.73px;
  height: 500px;
  display: block;
  margin-top: 20px;
  border-radius: 8px; /* 圆角 */
}

.el-button {
  margin-top: 20px;
}

.custom-button {
  width: 200px; /* 增加按钮宽度 */
  font-size: 18px; /* 改变字体大小 */
  font-weight: bold; /* 加粗字体 */
  background: linear-gradient(90deg, #4CAF50, #55D693); /* 添加渐变背景 */
  border: none; /* 去掉边框 */
  transition: background 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.custom-button:hover {
  background: linear-gradient(90deg, #55D693, #4CAF50); /* 悬停时改变背景 */
}

.custom-button:active {
  transform: scale(0.98); /* 点击时缩小按钮 */
}

.el-table {
  margin-top: 20px;
}

.meal-description {
  font-size: 14px;
  color: #999;
  margin-top: 10px;
}
</style>
