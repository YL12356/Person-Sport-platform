<template>
    <!-- 登录框容器 -->
    <div class="login_container">
        <!-- 登录块 -->
        <div class="login_box">
            <!-- 头像 -->
            <div class="avatar_box">
                <img src="../assets/logo.jpg" alt="">
            </div>
            <!-- 表单区域 -->
            <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" label-width="0" class="login_form">
                <!-- 用户名 -->
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" prefix-icon="iconfont icon-denglu-copy"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="iconfont icon-mima" type="password"></el-input>
                </el-form-item>
                <!-- 按钮 -->
                <el-form-item class="btns">
                    <el-button type="primary" @click="login">登录</el-button>
                    <el-button type="info" @click="resetLoginForm()">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            //表单数据
            loginForm: {
                username: 'admin',
                password: '123456'
            },
            //表单验证规则
            loginRules: {
                //校验用户名
                username: [
                    { required: true, message: '用户名为必填项', trigger: 'blur' },
                    { min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur' }
                ],
                //校验密码
                password: [
                    { required: true, message: '用户密码为必填项', trigger: 'blur' },
                    { min: 6, max: 10, message: '长度在 6 ~ 10 个字符', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        //重置
        resetLoginForm() {
            this.$refs.loginFormRef.resetFields()
        },
        //登录并跳转到相应页面
        async login() {
            this.$refs.loginFormRef.validate(async valid => {
                if (valid) {
                    try {
                        const { data: res } = await this.$http.post("/login", this.loginForm)
                        if (res.flag == "ok") {
                            this.$message.success("操作成功！！！")
                            // 根据用户角色跳转不同页面
                            this.$router.push({ path: '/home' })
                            window.sessionStorage.setItem("user", JSON.stringify(res.user))
                        } else {
                            this.$message.error("用户名或密码错误，请重新输入！")
                        }
                    } catch (error) {
                        console.error('Error during login:', error)
                        this.$message.error("服务器错误，请重试！")
                    }
                } else {
                    return
                }
            })
        }
    }
}
</script>

<style lang="less" scoped>
// 根节点样式
.login_container {
    background-color: #2b4b6b;
    height: 100%;
}

//登录框样式
.login_box {
    width: 450px;
    height: 300px;
    /* 调整高度以适应去掉的元素 */
    background-color: #fff;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

    .avatar_box {
        width: 130px;
        height: 130px;
        border: 1px solid #eee;
        border-radius: 50%;
        padding: 5px;
        box-shadow: 0 0 5px #ddd;
        position: absolute;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: #eee;

        img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            background-color: #eee;
        }
    }
}

.btns {
    display: flex;
    justify-content: flex-end;
}

.login_form {
    position: absolute;
    bottom: 0%;
    width: 100%;
    padding: 0 10px;
    box-sizing: border-box;
}
</style>
