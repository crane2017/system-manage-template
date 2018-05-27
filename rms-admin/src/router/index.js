import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: resolve => require(['../components/page/login.vue'], resolve)
    },
    {
      path: '/welcome',
      component: resolve => require(['../components/common/home.vue'], resolve),
      children: [
        {
          path: '/',
          component: resolve => require(['../components/page/welcome.vue'], resolve)
        },
        {
          path: '/account',
          component: resolve => require(['../components/page/system-manage/account.vue'], resolve)
        },
        {
          path: '/role',
          component: resolve => require(['../components/page/system-manage/role.vue'], resolve)
        },
        {
          path: '/menu',
          component: resolve => require(['../components/page/system-manage/menu.vue'], resolve)
        }
      ]
    }

  ]
});

/**
 * vue-router提供的钩子函数beforeEach()
 * to: Route: 即将要进入的目标 路由对象
 * from: Route: 当前导航正要离开的路由
 * next: Function: 一定要调用该方法来 resolve 这个钩子。执行效果依赖 next 方法的调用参数。
 * next(): 进行管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed （确认的）。
 * next(false): 中断当前的导航。如果浏览器的 URL 改变了（可能是用户手动或者浏览器后退按钮），那么 URL 地址会重置到 from 路由对应的地址。
 * next('/') 或者 next({ path: '/' }): 跳转到一个不同的地址。当前的导航被中断，然后进行一个新的导航。
 */
router.beforeEach((to, from, next) => {
  let Expression = /http(s)?:\/\/([\w-]+.)+[\w-]+([\w- .?%&=]*)?/;
  let objExp = new RegExp(Expression);
  let path = to.path.substr(0, 1) === '/' ? to.path.substr(1) : to.path;
  console.log('router===>path', path);
  if (objExp.test(path)) {
    window.open(path);
  } else {
    if (to.matched.some(r => r.meta.requireAuth)) {
      let username = sessionStorage.getItem('ms_username');
      if (username) {
        next();
      } else {
        next({
          path: '/login',
          query: {redirect: to.fullPath}
        });
      }
    } else {
      next();
    }
  }
});

export default router;
