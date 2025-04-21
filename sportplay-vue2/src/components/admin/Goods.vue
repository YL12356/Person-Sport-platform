<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>后台管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品模块</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 商品列表主体部分 -->
    <el-card>
      <el-row :gutter="25">
        <!-- 搜索区域 -->
        <el-col :span="10">
          <el-input placeholder="请输入运动类型" v-model="searchCategory" clearable @clear="fetchGoods">
            <el-button slot="append" icon="el-icon-search" @click="fetchGoods"></el-button>
          </el-input>
        </el-col>

        <!-- 添加商品按钮 -->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加商品</el-button>
        </el-col>
      </el-row>

      <!-- 商品列表 border：边框 stripe：条纹 -->
      <el-table :data="goodsList" border stripe>
        <el-table-column type="index"></el-table-column> <!-- 索引列 -->
        <el-table-column label="运动类型" prop="category"></el-table-column>
        <el-table-column label="商品名" prop="pname"></el-table-column>
        <el-table-column label="商品图片" prop="pimg">
          <template slot-scope="scope">
            <img :src="scope.row.pimg" alt="商品图片" width="100" />
          </template>
        </el-table-column>
        <el-table-column label="价格" prop="price">
          <template slot-scope="scope">
            ￥{{ scope.row.price }}
          </template>
        </el-table-column>
        <!-- 删除按钮 -->
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteGoods(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加商品区域 -->
    <el-dialog title="添加商品" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="120px">
        <!-- 运动类型 -->
        <el-form-item label="运动类型" prop="category">
          <el-input v-model="addForm.category"></el-input>
        </el-form-item>
        <!-- 商品名 -->
        <el-form-item label="商品名" prop="pname">
          <el-input v-model="addForm.pname"></el-input>
        </el-form-item>
        <!-- 商品图片URL -->
        <el-form-item label="商品图片URL" prop="pimg">
          <el-input v-model="addForm.pimg"></el-input>
        </el-form-item>
        <!-- 价格 -->
        <el-form-item label="价格" prop="price">
          <el-input v-model.number="addForm.price" type="number" step="0.01"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addGoods">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  created() {
    this.fetchGoods();
  },
  data() {
    return {
      searchCategory: '',
      goodsList: [],
      addDialogVisible: false, // 添加商品弹窗
      addForm: {
        category: '',
        pname: '',
        pimg: '',
        price: 0
      },
      addFormRules: {
        category: [
          { required: true, message: '请输入运动类型', trigger: 'blur' },
          { min: 1, max: 255, message: '长度不超过 255 个字符', trigger: 'blur' }
        ],
        pname: [
          { required: true, message: '请输入商品名', trigger: 'blur' },
          { min: 1, max: 255, message: '长度不超过 255 个字符', trigger: 'blur' }
        ],
        pimg: [
          { required: true, message: '请输入商品图片URL', trigger: 'blur' },
          { min: 1, max: 255, message: '长度不超过 255 个字符', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' },
          { type: 'number', message: '请输入数字', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    async fetchGoods() {
      try {
        const response = await axios.get('/goods', {
          params: {
            category: this.searchCategory
          }
        });
        this.goodsList = response.data.goodsList;
      } catch (error) {
        console.error('获取商品数据失败:', error);
      }
    },
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    async addGoods() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        try {
          const response = await axios.post('/addgoods', this.addForm);
          if (response.data !== 'success') {
            return this.$message.error('添加失败!');
          }
          this.$message.success('添加成功!');
          this.addDialogVisible = false;
          this.fetchGoods();
        } catch (error) {
          console.error('添加商品失败:', error);
          this.$message.error('添加失败!');
        }
      });
    },
    async deleteGoods(id) {
      const confirmRes = await this.$confirm('确认删除该商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      if (confirmRes !== 'confirm') return this.$message.info('已取消删除!');
      try {
        const response = await axios.delete('/deletegoods?id=' + id);
        if (response.data !== 'success') {
          return this.$message.error('删除失败!');
        }
        this.$message.success('删除成功!');
        this.fetchGoods();
      } catch (error) {
        console.error('删除商品失败:', error);
        this.$message.error('删除失败!');
      }
    }
  }
};
</script>

<style lang="less" scoped>
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 17px;
}

.el-table img {
  max-width: 100%;
  height: auto;
}

.el-form-item label {
  white-space: nowrap;
}

.el-dialog .el-form-item {
  margin-bottom: 20px;
}
</style>
