package com.qxd.birth.web.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by xiangqong.qu on 16/9/20 14:17.
 */
public class TestJettyService {

    public static void main(String[] args) {
        Server server = new Server(8088);// 服务器端口

        WebAppContext context = new WebAppContext();
        context.setContextPath("/birth");//应用访问Context
        //web.xml描述符号的位置，注意 eclipse和idea的位置路径不一样
        context.setDescriptor("./birth.web/src/main/webapp/WEB-INF/web.xml");
        //web资源的路径
        context.setResourceBase("./birth.web/src/main/webapp");
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        try {
            System.out.println("[HINT] Don't forget to set VM options \" -Xms512m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=256m -Djava.awt.headless=true \");");
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
