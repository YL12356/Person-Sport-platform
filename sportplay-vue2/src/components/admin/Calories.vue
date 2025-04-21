<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>运动平台</el-breadcrumb-item>
      <el-breadcrumb-item>卡路里</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡路里计算器 -->
    <el-card style="margin-top: 20px;">
      <h1 class="main-title">卡路里计算器</h1>
      <el-form :model="form" label-width="120px">
        <el-form-item label="体重 (kg)">
          <el-input v-model="form.weight" type="number" placeholder="请输入体重"></el-input>
        </el-form-item>
        <el-form-item label="运动类型">
          <el-select v-model="form.activity" placeholder="请选择运动类型">
            <el-option label="游泳" value="swimming"></el-option>
            <el-option label="跳绳" value="jump_rope"></el-option>
            <el-option label="跑步" value="running"></el-option>
            <el-option label="快走" value="fast_walking"></el-option>
            <el-option label="爬楼梯" value="climbing_stairs"></el-option>
            <el-option label="打球" value="playing_sports"></el-option>
            <el-option label="拳击" value="boxing"></el-option>
            <el-option label="瑜伽" value="yoga"></el-option>
            <el-option label="跳舞" value="dancing"></el-option>
            <el-option label="HIIT" value="hiit"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运动时间 (分钟)">
          <el-input v-model="form.duration" type="number" placeholder="请输入运动时间"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-check" @click="calculateCalories" class="custom-button">
            计算卡路里
          </el-button>
        </el-form-item>
      </el-form>
      <div v-if="calories !== null" class="result">
        <h2>你将消耗约 {{ calories }} 卡路里</h2>
        <div v-for="(food, index) in foodCalories" :key="index" class="food-comparison">
          <p>相当于 {{ food.quantity }} {{ food.unit }} 的 {{ food.name }}</p>
        </div>
      </div>
    </el-card>

    <!-- 关于卡路里的信息 -->
    <el-card style="margin-top: 20px;">
      <h1 class="main-title">关于卡路里</h1>
      <el-row :gutter="20">
        <el-col :span="16">
          <p class="info-content">
            卡路里是衡量食物或运动时能量的一种单位。在运动时，身体会消耗卡路里提供能量。了解卡路里消耗有助于科学减肥和健康锻炼。
          </p>
          <p class="info-content">
            卡路里是能量的单位，1卡路里等于将1克水在标准大气压下升高1摄氏度所需的能量。在食物标签上，卡路里通常指千卡路里（Calories），即1000卡路里。
          </p>
          <p class="info-content">
            在运动时，人体通过代谢过程将糖原和脂肪等能量来源转化为机械能，以驱动身体活动。这个过程中消耗的能量就是卡路里。了解卡路里消耗对于制定合理的减肥计划和锻炼计划非常重要。
          </p>
          <p class="info-content">
            不同的运动类型和强度会产生不同的能量消耗。例如，高强度间歇训练（HIIT）比一般散步消耗更多的卡路里。此外，个人的体重、肌肉量、心率和运动持续时间也会影响卡路里的消耗量。
          </p>
          <p class="info-content">
            通过上述卡路里计算器，您可以估算在特定运动类型和时间下，您将消耗多少卡路里。这对于跟踪您的日常能量消耗、优化饮食结构和调整锻炼计划都非常有帮助。
          </p>
        </el-col>
        <el-col :span="8">
          <el-image
            src="https://images.pexels.com/photos/19259147/pexels-photo-19259147.jpeg?auto=compress&cs=tinysrgb&w=600"
            fit="cover" :lazy="true" :alt="'卡路里'" class="calories-image" @error="setDefaultImg">
            <div slot="error">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        weight: '',
        activity: '',
        duration: '',
      },
      calories: null,
      activityCalories: {
        swimming: 6,
        jump_rope: 12,
        running: 10,
        fast_walking: 4,
        climbing_stairs: 10,
        playing_sports: 5,
        boxing: 8,
        yoga: 3,
        dancing: 6,
        hiit: 20,
      },
      foodCalories: [],
    };
  },
  methods: {
    // 根据用户输入计算卡路里消耗
    calculateCalories() {
      const weight = parseFloat(this.form.weight);
      const duration = parseFloat(this.form.duration);
      const activity = this.form.activity;

      if (isNaN(weight) || isNaN(duration) || !activity) {
        this.$message.error('请输入有效的信息');
        return;
      }

      const calorieBurnRate = this.activityCalories[activity];
      this.calories = (weight * calorieBurnRate * duration / 60).toFixed(2);

      // 将卡路里消耗与常见食物进行对比
      this.compareWithFood();
    },
    // 将卡路里消耗与常见食物进行对比
    compareWithFood() {
      const calories = parseFloat(this.calories);
      const foodExamples = [
        { name: '苹果', caloriesPerUnit: 52, unit: '个' }, // 一个苹果约 52 卡路里
        { name: '香蕉', caloriesPerUnit: 105, unit: '根' }, // 一根香蕉约 105 卡路里
        { name: '橙子', caloriesPerUnit: 47, unit: '个' }, // 一个橙子约 47 卡路里
        { name: '葡萄', caloriesPerUnit: 67, unit: '克' }, // 葡萄 100 克约 67 卡路里
        { name: '鸡蛋', caloriesPerUnit: 155, unit: '个' }, // 一个鸡蛋约 155 卡路里
        // 牛奶的单位是 250 毫升，需要转换为克
        { name: '牛奶', caloriesPerUnit: 54, unit: '毫升' }, // 250毫升牛奶约 54 卡路里
        // 面包的单位是 100 克
        { name: '面包', caloriesPerUnit: 74, unit: '克' }, // 面包 100 克约 74 卡路里
        { name: '土豆', caloriesPerUnit: 86, unit: '个中等大小' }, // 中等大小土豆约 86 卡路里
        // 鸡肉、牛肉的单位是 100 克
        { name: '鸡肉', caloriesPerUnit: 165, unit: '克' }, // 鸡肉 100 克约 165 卡路里
        { name: '牛肉', caloriesPerUnit: 250, unit: '克' }, // 牛肉 100 克约 250 卡路里
      ];

      this.foodCalories = foodExamples.map(food => {
        let quantity;

        // 处理单位为克的食物
        if (food.unit === '克') {
          quantity = (calories / food.caloriesPerUnit * 100).toFixed(0); // 计算为克数
        }
        // 处理牛奶，单位是毫升
        else if (food.unit === '毫升') {
          quantity = (calories / food.caloriesPerUnit * 250).toFixed(0); // 计算为毫升数
        }
        // 处理中等大小的食物
        else if (food.unit === '个中等大小') {
          quantity = (calories / food.caloriesPerUnit).toFixed(2); // 计算数量，这里假设“中等大小”的单位可以类似于“个”
        }
        // 其他食物保持原始单位
        else {
          quantity = (calories / food.caloriesPerUnit).toFixed(2); // 计算数量，如个、根等
        }

        return {
          name: food.name,
          quantity: quantity,
          unit: food.unit,
        };
      });
    },
    // 图片加载失败时的默认图片
    setDefaultImg(event) {
      event.target.src = '/default-img.png'; // 默认图片路径
    },
  },
  computed: {
    activityCaloriesLabel() {
      return {
        swimming: '游泳',
        jump_rope: '跳绳',
        running: '跑步',
        fast_walking: '快走',
        climbing_stairs: '爬楼梯',
        playing_sports: '打球',
        boxing: '拳击',
        yoga: '瑜伽',
        dancing: '跳舞',
        hiit: 'HIIT',
      };
    },
  },
};
</script>

<style scoped>
/* 面包屑导航样式 */
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 17px;
}

/* 页面卡片样式 */
.el-card {
  margin-top: 20px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  /* 初始状态的阴影 */
  transition: box-shadow 0.3s ease;
  /* 添加过渡效果 */
}

/* 标题样式 */
.main-title {
  font-size: 24px;
  color: #303133;
  margin-bottom: 20px;
}

/* 文本样式 */
.info-content {
  font-size: 16px;
  color: #666;
  line-height: 1.6;
  text-align: left;
  margin-bottom: 20px;
}

/* 结果展示样式 */
.result {
  margin-top: 20px;
  font-size: 20px;
  color: #333;
}

/* 食物对比样式 */
.food-comparison {
  font-size: 16px;
  color: #999;
  margin-top: 10px;
}

/* 按钮样式 */
.el-button {
  margin-top: 20px;
}

.custom-button {
  width: 200px;
  /* 增加按钮宽度 */
  font-size: 18px;
  /* 改变字体大小 */
  font-weight: bold;
  /* 加粗字体 */
  background: linear-gradient(90deg, #4CAF50, #55D693);
  /* 添加渐变背景 */
  border: none;
  /* 去掉边框 */
  color: white;
  /* 确保文字颜色为白色 */
}

.custom-button:hover {
  background: linear-gradient(90deg, #4CAF50, #55D693);
  /* 悬停时保持背景 */
}

.custom-button:active {
  background: linear-gradient(90deg, #4CAF50, #55D693);
  /* 点击时保持背景 */
}

/* 食物图片样式 */
.calories-image {
  width: 383.73px;
  height: 500px;
  display: block;
  margin-top: 20px;
  border-radius: 8px;
  /* 圆角 */
}

/* 行布局样式 */
.el-row {
  align-items: center;
  /* 垂直居中对齐 */
}

/* 列布局适配 */
.el-col {
  @media (max-width: 768px) {
    flex: 0 0 100%;
    /* 在移动端显示为一行一张图片 */
  }
}

/* 卡路里信息部分 */
.calories-section {
  text-align: left;
}

.calories-section h1 {
  font-size: 24px;
  color: #303133;
  margin-bottom: 20px;
}

.calories-section p {
  font-size: 16px;
  color: #666;
  line-height: 1.6;
  margin-bottom: 20px;
}
</style>
