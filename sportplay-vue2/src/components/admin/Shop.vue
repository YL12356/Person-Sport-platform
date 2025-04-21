<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 20px;">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>运动平台</el-breadcrumb-item>
            <el-breadcrumb-item>运动购物</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 搜索区域 -->
        <el-input placeholder="搜索运动类型或商品名" v-model="searchQuery" clearable @clear="fetchGoods"
            style="width: 300px; margin-bottom: 20px;">
            <template #append>
                <el-button icon="el-icon-search" @click="fetchGoods" class="custom-search-button" />
            </template>
        </el-input>

        <!-- 商品列表 -->
        <el-row :gutter="20">
            <el-col v-for="item in filteredGoods" :key="item.id" :span="6">
                <el-card :body-style="{ padding: '20px' }" class="product-card">
                    <div class="image-container">
                        <img :src="item.pimg" alt="商品图片" class="product-image" />
                    </div>
                    <div class="product-info">
                        <h3>{{ item.pname }}</h3>
                        <p>分类：{{ item.category }}</p>
                        <p>价格：￥{{ item.price }}</p>
                        <button class="custom-add-to-cart-button" @click="addToCart(item)"
                            style="margin-top: 10px;">加入购物车</button>
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 没有找到商品时的提示 -->
        <div v-if="filteredGoods.length === 0" style="text-align: center; margin-top: 20px;">
            <el-empty description="没有找到相关商品" />
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            searchQuery: '',
            goodsList: [],
        }
    },
    computed: {
        filteredGoods() {
            return this.goodsList.filter(item => {
                const query = this.searchQuery.toLowerCase();
                return (
                    item.category.toLowerCase().includes(query) ||
                    item.pname.toLowerCase().includes(query)
                );
            });
        },
    },
    created() {
        this.fetchGoods();
    },
    methods: {
        async fetchGoods() {
            try {
                const response = await axios.get('/goods');
                this.goodsList = response.data.goodsList;
            } catch (error) {
                console.error('获取商品数据失败:', error);
            }
        },
        addToCart(item) {
            this.$message.success(`${item.pname} 已添加到购物车!`);
        },
    },
};
</script>

<style lang="less" scoped>
/* 全局样式 */
.product-card {
    width: 300px;
    /* 设置卡片宽度 */
    height: 550px;
    /* 设置卡片高度 */
    margin-bottom: 20px;
    /* 卡片上下间距 */
    transition: transform 0.2s;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    cursor: pointer;
    overflow: hidden;
    /* 避免内容溢出 */

    &:hover {
        transform: scale(1.05);
    }
}

/* 图片容器样式 */
.image-container {
    height: 200px;
    /* 统一图片高度 */
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}

.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    /* 确保图片填满容器 */
    border-radius: 4px;
}

/* 商品信息样式 */
.product-info {
    margin-top: 10px;
    /* 调整内容与图片之间的间距 */
    display: flex;
    flex-direction: column;
    gap: 5px;
    /* 内容项之间的间距 */
}

/* 自定义搜索按钮样式 */
.custom-search-button {
    background-color: #d3dce6;
    /* 浅灰色背景 */
    border: none;
    color: black;
    /* 文字颜色为黑色 */
    padding: 10px 15px;
    cursor: pointer;
    font-size: 16px;
    border-radius: 6px;
    transition: background-color 0.3s, transform 0.2s;

    &:hover {
        background-color: #d3dce6;
        transform: scale(1.05);
        /* 鼠标悬停时的放大效果 */
    }
}

/* 自定义加入购物车按钮样式 */
.custom-add-to-cart-button {
    width: 100%;
    /* 满宽按钮 */
    margin-top: 10px;
    /* 与其他内容的间距 */
    background-color: #b8412c;
    /* 深蓝色背景 */
    border: none;
    color: white;
    padding: 10px 15px;
    cursor: pointer;
    font-size: 16px;
    border-radius: 6px;
    transition: background-color 0.3s, transform 0.2s;

    &:hover {
        background-color: #d64f38;
        /* 悬停时的深色深一点 */
        transform: scale(1.05);
        /* 鼠标悬停时的放大效果 */
    }
}
</style>
