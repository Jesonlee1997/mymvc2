package me.jesonlee.mymvc2.core;

import me.jesonlee.mymvc2.http.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 框架的核心处理器，负责调配整个请求周期
 * Created by Administrator on 2017/3/7 0007.
 */
public class DispatcherServlet extends HttpServlet {
    //映射管理器
    private MappingManager mappingManager = MappingManager.getMappingManager();

    private ViewManager templateManager = new ViewManager();


    @Override
    public void init() throws ServletException {
        // 初始化映射管理器
        mappingManager.init();


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解析请求
        Request request = HttpRequestResolver.resolve(req);
        System.out.println(request);

        //对url进行处理
        //url = UrlUtil.fixUrl(url); TODO:处理url

        //执行url对应的method方法
        RequestHandler requestHandler = mappingManager.getHandler(request);

        //调用处理器的handle方法处理请求
        if (requestHandler == null) {
            //TODO: 返回404page
        }

        ModelAndView mv = null;
        mv = requestHandler.handle(request);

        // 返回viewName对应的页面
        // TODO: 渲染页面
        templateManager.render(mv, req, resp);
    }
}