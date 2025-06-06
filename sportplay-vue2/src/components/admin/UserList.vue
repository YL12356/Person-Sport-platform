<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>后台管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb> 

    <!-- 用户列表主体部分 -->
    <el-card>
        
        <el-row :gutter="25">    <!-- gutter:间隙属性 -->
            <!-- 搜索区域 -->
            <el-col :span="10">
                <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getUserList">
                    <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
                </el-input> 
            </el-col>

            <!-- 搜索按钮 -->
            <el-col :span="4">
                <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
            </el-col> 
        </el-row>

        <!-- 用户列表 border：边框 stripe：条纹 -->
        <el-table :data="userList" border stripe>
            <el-table-column type="index"></el-table-column> <!-- 索引列 -->  
            <el-table-column label="用户名" prop="username"></el-table-column> 
            <el-table-column label="邮箱" prop="email"></el-table-column> 
            <el-table-column label="密码" prop="password"></el-table-column> 
            <el-table-column label="角色" prop="role"></el-table-column> 
            <el-table-column label="状态" prop="state">
                <!-- 作用域插槽 -->
                <template slot-scope="scope">
                    <!-- {{scope.row}} -->  <!-- 每一行封存的数据 -->
                    <el-switch v-model="scope.row.state" @change="userStateChange(scope.row)"></el-switch>
                </template>    
            </el-table-column> 
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <!-- 修改 -->
                    <el-button type="primary" icon="el-icon-edit" 
                    size="mini" @click="showEditDialog(scope.row.id)">
                    </el-button>
                    <!-- 删除 -->
                    <el-button type="danger" icon="el-icon-delete" 
                    size="mini" @click="deleteUser(scope.row.id)">
                    </el-button>
                    <!-- 权限 -->
                    <el-tooltip effect="dark" content="分配权限"
                    placement="top-start" :enterable="false">   <!-- 文字提示 enterable 隐藏 -->
                        <el-button type="warning" icon="el-icon-setting"
                        size="mini">
                        </el-button>
                    </el-tooltip>
                </template>    
            </el-table-column> 
        </el-table> 

        <!-- 分页 -->
         <!-- size-change:每页最大变化 -->
         <!-- current-change:当前最大变化 -->
         <!-- layout:功能组件 -->
        <div>
            <el-pagination
                @size-change="handleSizeChange" 
                @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum"
                :page-sizes="[1, 2, 5, 100]"
                :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div> 
    </el-card> 

    <!-- 新增用户区域 -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible"
     width="50%" @close="addDialogClosed">
        <el-form :model="addForm" :rules="addFormRules" 
        ref="addFormRef" label-width="70px">
            <!-- 用户名 -->
            <el-form-item label="用户名" prop="username">
                <el-input v-model="addForm.username"></el-input>
            </el-form-item>
            <!-- 密码 -->
            <el-form-item label="密码" prop="password">
                <el-input v-model="addForm.password"></el-input>
            </el-form-item> 
            <!-- 邮箱 -->
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="addForm.email"></el-input>
            </el-form-item>    
        </el-form>
        <!-- 内容底部区域 -->
        <span slot="footer" class="dialog-footer">
            <el-button @click="addDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addUser">确 定</el-button>
        </span> 
    </el-dialog>

    <!-- 修改用户区域 -->
    <el-dialog title="修改用户信息" :visible.sync="editDialogVisible"
     width="50%" @close="editDialogClosed">
        <el-form :model="editForm" :rules="editFormRules" 
        ref="editFormRef" label-width="70px">
            <!-- 用户名 -->
            <el-form-item label="用户名" prop="username">
                <el-input v-model="editForm.username" disabled></el-input>
            </el-form-item>
            <!-- 密码 -->
            <el-form-item label="密码" prop="password">
                <el-input v-model="editForm.password"></el-input>
            </el-form-item> 
            <!-- 邮箱 -->
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="editForm.email"></el-input>
            </el-form-item>    
        </el-form>
        <!-- 内容底部区域 -->
        <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editUserInfo">确 定</el-button>
        </span> 
    </el-dialog>
  </div>
</template>

<script>
export default {
    created(){
        this.getUserList()
    },
    data(){
        return{
            //查询信息实体
            queryInfo:{
                query:'',   //查询信息
                pageNum:1,
                pageSize:5,
            },
            userList:[],    //用户列表
            total:0,        //总记录数
            addDialogVisible:false,   //添加用户弹窗
            //添加表单信息
            addForm:{
                username:'',
                password:'',
                email:''
            },
            //修改用户信息
            editForm:{},
            //显示/隐藏修改用户栏
            editDialogVisible:false,
            //添加用户的表单验证
            addFormRules:{
                username:[
                    {required:true, message:'请输入用户名', trigger:'blur'},
                    {min:2, max:8, message:'长度在 2 到 8 个字符', trigger:'blur'}
                ],
                password:[
                    {required:true, message:'请输入密码', trigger:'blur'},
                    {min:6, max:8, message:'长度在 6 到 8 个字符', trigger:'blur'}
                ],
                email:[
                    {required:true, message:'请输入邮箱', trigger:'blur'},
                    {min:5, max:15, message:'邮箱格式不正确', trigger:'blur'}
                ],
            },
            //修改用户的表单验证
            editFormRules:{
                password:[
                    {required:true, message:'请输入密码', trigger:'blur'},
                    {min:6, max:8, message:'长度在 6 到 8 个字符', trigger:'blur'}
                ],
                email:[
                    {required:true, message:'请输入邮箱', trigger:'blur'},
                    {min:5, max:15, message:'邮箱格式不正确', trigger:'blur'}
                ],
            }
        }
    },
    methods:{
        // 获取用户列表
        async getUserList(){
            const {data:res} = await this.$http.get('alluser',{params:this.queryInfo})
            this.userList = res.data    //用户列表数据封装
            this.total = res.numbers    //总用户数封装
        },
        //最大数
        handleSizeChange(newSize){
            this.queryInfo.pageSize = newSize
            this.getUserList()
        },
        //pageNum的触发动作
        handleCurrentChange(newPage){
            this.queryInfo.pageNum = newPage
            this.getUserList()
        },
        //修改用户状态
        async userStateChange(userInfo){
            const {data:res} = await this.$http.put(`userstate?id=${userInfo.id}&state=${userInfo.state}`)
            if(res != 'success'){
                userInfo.id = !userInfo.id
                return this.$message.error('操作失败!')
            }
            this.$message.success('操作成功!')
        },
        //监听添加用户
        addDialogClosed(){
            this.$refs.addFormRef.resetFields()
        },  
        addUser(){
            this.$refs.addFormRef.validate(async valid => {
                if(!valid)  return;
                const {data:res} = await this.$http.post('adduser',this.addForm)
                if(res != 'success'){
                    return this.$message.error('添加失败!')
                }
                this.$message.success('添加成功!')
                this.addDialogVisible = false
                this.getUserList()
            })
        }, 
        //根据主键删除用户信息
        async deleteUser(id){
            const confirmRes = await this.$confirm('确认删除该用户吗？','提示',{
                confirmButtonText:'确定',
                cancelButtonText:'取消',
                type:'warning'
            }).catch(err => err)
            if(confirmRes != 'confirm') 
                return this.$message.info('已取消删除!')
            const {data:res} = await this.$http.delete('deleteuser?id='+id)
            if(res != 'success')
                return this.$message.error('删除失败!')
            this.$message.success('删除成功!')
            this.getUserList()
        },
        //显示/隐藏对话框
        async showEditDialog(id){
            const {data:res} = await this.$http.get('getupdate?id='+id)
            this.editForm = res  //查询出用户信息并反填回编辑表单
            this.editDialogVisible = true  //开启编辑菜单
        },
        //关闭窗口
        editDialogClosed(){
            this.$refs.editFormRef.resetFields()   //关闭窗口,重置信息
        },
        //确认修改
        editUserInfo(){
            this.$refs.editFormRef.validate(async valid => {
                if(!valid)  return;
                //发起修改请求
                const {data:res} = await this.$http.put('edituser',this.editForm)
                if(res != 'success'){
                    return this.$message.error('修改失败!')
                }
                this.$message.success('修改成功!')
                this.editDialogVisible = false   //关闭窗口
                this.getUserList()   //刷新用户列表
            })
        },
    }
}
</script>

<style lang="less" scoped>
.el-breadcrumb{
    margin-bottom: 15px;
    font-size: 17px;
}
</style>