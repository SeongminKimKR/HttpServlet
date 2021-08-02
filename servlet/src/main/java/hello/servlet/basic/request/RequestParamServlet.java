package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 파라미터 전송기능
 * http://localhost:8080/request-param?username=hello&age=20
 */
@WebServlet (name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[전체 파라미터 조회] -start");

        /**
         * key : paraName
         * value : getParameter(paraName)
         */
        request.getParameterNames().asIterator()
                .forEachRemaining(paraName -> System.out.println(paraName + "="+request.getParameter(paraName)));

        System.out.println("[전체 파라미터 조회] -end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        String age = request.getParameter("age");
        System.out.println("age = " + age);
        System.out.println();

        /**
         * 하나의 파라미터의 복수의 값이 있을때는 getParameter()를 사용하면 안된다.
         * ->getParameterValues() 사용
         */
        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }
        System.out.println();

        response.getWriter().write("ok");
    }
}
