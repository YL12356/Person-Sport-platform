<template>
  <!-- 引入container布局 -->
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header>
      <div>
        <img src="../assets/sport.png" alt>
        <span>个人运动平台</span>
      </div>
      <el-button type="info" @click="logout">安全退出</el-button>
    </el-header>
    <!-- 主体 -->
    <el-container>
      <!-- 主体---侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-Button" @click="toggleCollapse">|||</div>
        <el-menu background-color="#545c64" text-color="#fff" active-text-color="#e6c400" unique-opened
          :collapse="isCollapse" :collapse-transition="false" :router="true" :default-active="activePath">

          <!-- 一级菜单 -->
          <el-submenu :index="item.id + ''" v-for="item in menuList" :key="item.id">
            <template slot="title">
              <i :class="iconsObject[item.id]"></i>
              <span>{{ item.title }}</span>
            </template>

            <!-- 二级菜单 -->
            <el-menu-item :index="it.path" v-for="it in item.sList" :key="it.id" @click="saveNavState(it.path)">
              <i :class="iconsObject[it.id]"></i>
              <span>{{ it.title }}</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 主体---内容 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      //菜单列表
      menuList: [],
      isCollapse: false, //侧边栏伸缩
      iconsObject: {
        '100': 'iconfont icon-guanliyuan',
        '200': 'iconfont icon-yundong',
        '101': 'iconfont icon-denglu-copy',
        '103': 'iconfont icon-yundong',
        '104': 'iconfont icon-shangpin',
        '201': 'iconfont icon-shu',
        '202': 'iconfont icon-qialuli',
        '203': 'iconfont icon-shiwu',
        '204': 'iconfont icon-shangpin',
      },
      activePath: '/welcome',    //默认路径,
    }
  },
  //onload事件 --- 页面一加载就会干的事
  created() {
    //查询menuList
    this.getMenuList()
    //取出session里的path，动态修改activePath
    this.activePath = window.sessionStorage.getItem('activePath')
  },
  methods: {
    logout() {
      window.sessionStorage.clear();  // 清除sessionStorage
      this.$router.push("/login");    // 退出首页并回到登录页面
    },
    //获取导航菜单方法
    async getMenuList() {
      const { data: res } = await this.$http.get('menus')
      if (res.flag !== 200) return this.$message.error("获取列表失败！")
      this.menuList = res.menus
    },
    //控制侧边栏伸缩
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    //保存当前路径
    saveNavState(activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    }
  }
}
</script>

<style lang="less" scoped>
// 布局器样式
.home-container {
  height: 100%;
}

// 头样式
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between; // 两端对齐
  padding-left: 0%; // 左侧留白
  align-items: center;
  color: #fff;
  font-size: 18px;

  div {
    display: flex;
    align-items: center;

    span {
      margin-left: 15px;
    }
  }
}

// 侧边样式
.el-aside {
  background-color: #333744;

  .el-menu {
    border-right: none;
  }

  .el-menu-item.is-active {
    color: #e6c400 !important; // 设置二级菜单激活时的颜色
  }
}

// 主体样式
.el-main {
  border-color: #eaedf1;
}

img {
  width: 75px;
  height: 75px;
}

//  |||按钮样式
.toggle-Button {
  background-color: #4A5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer; // 鼠标手势
}
</style>
