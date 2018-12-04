package cn.edu.dlmu.wechat.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
public class AuthorizeInterceptor implements Interceptor {
    public void intercept(Invocation inv){
        System.out.println("被拦截的方法："+inv.getMethodName());
        //更改state的值
        Object[] o = inv.getArgs();
        System.out.println(o.toString());
        String state = (String)inv.getArg(1);
        if(state.equals("wx")){
            inv.setArg(1,"first");
        }
        else{
            inv.setArg(1,"again");
        }
        System.out.println(inv.getArg(1));
        inv.invoke();
    }
    /***
     * 访问 主页 简单举报 等页面时  先到 拦截器（或控制器）
     *
     * 判断 session 中openID 是否存在
     *
     * 不存在
     *      采用静默授权获取用户的 OpenID 存入 session
     *
 *     判断用户ACCESS_TOKEN 是否存在
 *     不存在
 *         调用手动授权 获得ACCESS_TOKEN，REFRESH_TOKEN
 *     存在
 *         判断是否过期
 *         过期
 *             使用refresh_TOKEN 刷新  若 refresh_token也过期 则 调用用户手动授权
     * 拉取用户信息
     */
}
