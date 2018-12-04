package cn.edu.dlmu.wechat.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * Created by yokiqust on 17-9-28.
 */
public class AuthInterceptor implements Interceptor {

    public void intercept(Invocation invocation) {
        Controller c = invocation.getController();
        Boolean b = c.getSessionAttr("IsAuthed");
        if (b != null) {
            if (b) {
                invocation.invoke();
            } else {
                c.redirect("/login");
            }
        }else{
            c.redirect("/login");
        }
    }
}
