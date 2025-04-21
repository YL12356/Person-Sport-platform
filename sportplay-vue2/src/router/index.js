import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Home from '../components/Home'
import Welcome from '../components/Welcome'
import UserList from '../components/admin/UserList'
import SportModule from '../components/admin/SportModule'
import Calories from '../components/admin/Calories'
import Goods from '../components/admin/Goods'
import Introduction from '../components/admin/Introduction'
import Detail from '../components/admin/Detail'
import Food from '../components/admin/Food'
import Shop from '../components/admin/Shop'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/home',
    component: Home,
    redirect: '/welcome',
    children: [
      {
        path: '/welcome', component: Welcome
      },
      {
        path: '/user', component: UserList
      },
      {
        path: '/sport', component: SportModule
      },
      {
        path: '/goods', component: Goods
      },
      {
        path: '/Introduction', component: Introduction, name: 'Introduction'
      },
      {
        path: '/Introduction/:id', component: Detail, name: 'Detail'
      },
      {
        path: '/calories', component: Calories
      },
      {
        path: '/food', component: Food
      },
      {
        path: '/shop', component: Shop
      },
    ]
  }
]

const router = new VueRouter({
  routes
})

//出现问题时使用
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}


//挂载路由导航守卫
router.beforeEach((to, from, next) => {
  //to:即将要进入的路径
  //from:从哪个路径进入的
  //next:是一个函数，表示放行
  if (to.path == '/login') return next()
  //获取user
  const userFlag = window.sessionStorage.getItem("user")   //取出当前用户
  if (!userFlag) return next('/login')    //无值，返回登录页

  const user = JSON.parse(userFlag)
  next()  //符合要求，放行
})

export default router
