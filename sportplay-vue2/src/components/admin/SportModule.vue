<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>后台管理</el-breadcrumb-item>
      <el-breadcrumb-item>运动记录</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 运动记录主体部分 -->
    <el-card>
      <el-table :data="sportRecords" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="运动名称" prop="sportname"></el-table-column>
        <el-table-column label="开始时间" prop="startime" :formatter="formatDate"></el-table-column>
        <el-table-column label="结束时间" prop="endtime" :formatter="formatDate"></el-table-column>
        <el-table-column label="运动总时长（分钟）" prop="duration"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">
            </el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteSportRecord(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[1, 2, 5, 100]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <!-- 新增运动记录区域 -->
    <el-dialog title="添加运动记录" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
        <el-form-item label="用户名" prop="uid">
          <el-select v-model="addForm.uid" placeholder="请选择用户名">
            <el-option
              v-for="user in userList"
              :key="user.id"
              :label="user.username"
              :value="user.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运动名称" prop="sportname">
          <el-input v-model="addForm.sportname" placeholder="请输入运动名称"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="startime">
          <el-date-picker
            v-model="addForm.startime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            v-model="addForm.endtime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSportRecord">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改运动记录区域 -->
    <el-dialog title="修改运动记录" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="用户名" prop="uid">
          <el-select v-model="editForm.uid" placeholder="请选择用户名" :disabled="true">
            <el-option
              v-for="user in userList"
              :key="user.id"
              :label="user.username"
              :value="user.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运动名称" prop="sportname">
          <el-input v-model="editForm.sportname" placeholder="请输入运动名称"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="startime">
          <el-date-picker
            v-model="editForm.startime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            v-model="editForm.endtime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editSportRecord">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  created() {
    this.getUserList();
    this.getSportRecords();
  },
  data() {
    return {
      queryInfo: {
        query: '',
        pageNum: 1,
        pageSize: 5,
      },
      userList: [],
      sportRecords: [],
      total: 0,
      addDialogVisible: false,
      addForm: {
        uid: '',
        sportname: '',
        startime: '',
        endtime: '',
      },
      editDialogVisible: false,
      editForm: {},
      addFormRules: {
        uid: [
          { required: true, message: '请选择用户名', trigger: 'change' },
        ],
        sportname: [
          { required: true, message: '请输入运动名称', trigger: 'change' },
        ],
        startime: [
          { required: true, message: '请选择开始时间', trigger: 'change' },
        ],
        endtime: [
          { required: true, message: '请选择结束时间', trigger: 'change' },
        ],
      },
      editFormRules: {
        sportname: [
          { required: true, message: '请输入运动名称', trigger: 'change' },
        ],
        startime: [
          { required: true, message: '请选择开始时间', trigger: 'change' },
        ],
        endtime: [
          { required: true, message: '请选择结束时间', trigger: 'change' },
        ],
      },
    };
  },
  methods: {
    // 获取用户列表
    async getUserList() {
      try {
        const { data: res } = await this.$http.get('alluser', { params: this.queryInfo });
        if (res && res.data) {
          this.userList = res.data;
        } else {
          console.error("获取用户列表失败", res);
        }
      } catch (error) {
        console.error("获取用户列表时发生错误", error);
      }
    },
    // 获取运动记录列表
    // 获取运动记录列表
    async getSportRecords() {
        try {
            const { data: res } = await this.$http.get('getUsersWithSportRecords', { params: this.queryInfo });
            if (res && res.data) {
                this.sportRecords = res.data.flatMap(user => user.sportRecords.map(record => ({ ...record, username: user.username })));
                this.total = res.numbers; // 使用后端返回的总数
            } else {
                console.error("获取运动记录失败", res);
            }
        } catch (error) {
            console.error("获取运动记录时发生错误", error);
        }
    },

    // 页码改变事件
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getSportRecords();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getSportRecords();
    },
    // 新增运动记录
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    addUserFormValidate() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        try {
          const res = await this.$http.post('addsport', this.addForm);
          if (res.data === 'success') {
            this.$message.success('添加成功!');
            this.addDialogVisible = false;
            this.getSportRecords();
          } else {
            this.$message.error('添加失败!');
          }
        } catch (error) {
          console.error("添加运动记录时发生错误", error);
          this.$message.error('添加失败!');
        }
      });
    },
    addSportRecord() {
      this.addUserFormValidate();
    },
    // 删除运动记录
    async deleteSportRecord(id) {
      try {
        const confirmRes = await this.$confirm('确认删除该运动记录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).catch(err => err);
        if (confirmRes !== 'confirm') {
          return this.$message.info('已取消删除!');
        }
        const res = await this.$http.delete('deletesport?id=' + id);
        if (res.data === 'success') {
          this.$message.success('删除成功!');
          this.getSportRecords();
        } else {
          this.$message.error('删除失败!');
        }
      } catch (error) {
        console.error("删除运动记录时发生错误", error);
        this.$message.error('删除失败!');
      }
    },
    // 显示/隐藏修改运动记录对话框
    async showEditDialog(id) {
      try {
        const { data: res } = await this.$http.get('getupdatesport?id=' + id);
        if (res) {
          this.editForm = res;
          this.editDialogVisible = true;
        } else {
          console.error("获取运动记录失败", res);
        }
      } catch (error) {
        console.error("获取运动记录时发生错误", error);
      }
    },
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
    // 确认修改运动记录
    editUserFormValidate() {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return;
        try {
          const res = await this.$http.put('editsport', this.editForm);
          if (res.data === 'success') {
            this.$message.success('修改成功!');
            this.editDialogVisible = false;
            this.getSportRecords();
          } else {
            this.$message.error('修改失败!');
          }
        } catch (error) {
          console.error("修改运动记录时发生错误", error);
          this.$message.error('修改失败!');
        }
      });
    },
    editSportRecord() {
      this.editUserFormValidate();
    },
    // 格式化日期
    formatDate(row, column, cellValue) {
      return cellValue ? new Date(cellValue).toLocaleString() : '';
    },
  },
};
</script>

<style lang="less" scoped>
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 17px;
}
</style>
