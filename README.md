# HttpServlet 학습
- 출처: 인프런 김영한님 강의 '스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술' 
- https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-mvc-1/questions?s=index.html&page=1&type=question
- Spring MVC 이전의 자바 웹 백엔드의 기술들을 이해하고 어떻게 진화하면서 Spring MVC가 나오게 되었는지 이해한다.

## 학습 목차

- [웹 어플리케이션](#웹-어플리케이션)
- [서블릿](#서블릿)
- [서블릿과 JSP](#서블릿과-JSP)
- [MVC 패턴](#MVC-패턴)
- [서블릿 구조 개선](#서블릿-구조-개선)
---

## 웹 어플리케이션

### 웹 서버 , 웹 애플리케이션 서버(WAS)

- 공통점
    -HTTP 기반
- 차이점
    - 웹 서버는 웹 브라우저 클라이언트로부터 HTTP 요청을 받아 정적 리소스(.html, .jpeg, .css) 제공한다.
    - WAS는 DB조회나 애플리케이션 코드를 실행하여 동적인 컨텐츠를 제공하기 위해 존재한다.

### 웹 시스템 구성 
 ![web-system.PNG](./image/chapter1/web-system.PNG)
 
 - 정적 리소스는 웹 서버가 처리하고 애플리케이션 로직 같은 동적 처리는 WAS에게 넘겨준다.
 - 역할을 나눈 덕에 요구 사항에 형태와 양에 따라 서버의 크기를 증설하거나 줄일 수 있다.
 - WAS가 오류로 인해 작동 하지 않으면 웹 서버가 오류 화면을 제공할 수 있다.
 
 ### 서블릿을 지원하는 WAS를 사용해야 하는 이유
 ![was.PNG](./image/chapter1/was.PNG)
 - 개발자는 HTTP 통신에 관련된 개발을 하지 않고, 서비스에 필요한 기능 구현에만 집중할 수 있게 도와준다.
---
## 서블릿
### 정의 
- 클라이언트의 HTTP 요청에 대해 특정 기능을 수행, HTML문서를 생성등의 응답을 하는 인터넷 서버 프로그램
- 클라이언트 요청을 처리하고 그 결과를 다시 클라이언트에게 전송하는 Servlet 클래스의 구현 규칙을 지킨 자바 프로그램 

### 서블릿 컨테이너
- 서블릿의 객체를 생성, 초기화, 호출, 종료하는 생명주기를 관리한다.
- 서블릿 객체를 싱글톤으로 관리하여 모든 클라이언트의 요청은 동일한 서블렛 객체 인스턴스에 접근하게 하여 효율적으로 관리한다.
- 클라이언트의 Request를 받고 Response를 내보낼수 있도록 웹 서버와 소켓을 만들어 통신을 가능케 한다.
- 
    
    ![servlet-container.PNG](./image/chapter2/servlet-container.PNG)
    
### HttpServletRequest, HttpServletResponse
- HttpServletRequest
    - 서블릿이 HTTP 요청 메시지를 파싱하여 **HttpServletRequest**라는 객체에 담는다. 개발자가 애플리케이션 코드로써 쉽게 조회할 수 있도록 돕는다.
    - 자바 서블릿에서는 요청 메시지에 각 필드들에 담긴 정보들을 조회할 수 있는 다양한 메서드를 지원한다.
- HttpServletResponse
    - 요청 메시지를 처리하고 응답 메시지를 담는 객체로 HTTP 응답코드와, header 와 body를 생성할 수 있다.
    - 요청 메시지와 마찬 가지로 헤더에 각 필드마다 정보를 삽입할 수 있는 다양한 메서드를 지원한다.

### 응답이 HTML일때
```
 @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("  <div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
```
- 응답 메시지에 HTML 코드들을 한줄씩 담아서 보내주어야 한다.
### 템플릿 엔진의 필요성
- 서블릿 덕분에 동적으로 원하는 HTML을 마음껏 만들수 있지만, 코드가 매우 비효율 적이다. 이것이 바로 템플릿 엔진이 나온 이유이다. 
템플릿 엔진을 사용하면 HTML 문서에서 필요한 곳만 코드를 적용해서 동적으로 변경할 수 있다.
---
## JSP
### 회원 관리 예제에서 모든 회원을 조회하는 jsp
```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="java.util.List" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
    <%
        for (Member member : members) {
            out.write("    <tr>");
            out.write("        <td>" + member.getId() + "</td>");
            out.write("        <td>" + member.getUsername() + "</td>");
            out.write("        <td>" + member.getAge() + "</td>");
            out.write("    </tr>");
        }
    %>
    </tbody>
</table>

</body>
</html>
```
- 이전에 서블릿으로 HTML을 개발할 때보다 화면을 만드는 작업을 분리할 수 있었고 동적으로 생성해야 하는 부분에 대해서만 자바 코드를 적용한 모습이다.
- 그런데 결국 코드의 절반은 비즈니스 로직에 해당하고 나머지가 HTML을 위한 코드로 나뉘어져 있다.
- 프론트와 백엔드의 구분이 없어보이고, 코드를 관리 및 보수를 하는데 있어서 어려움이 있어 보인다.
- 그래서 비즈니스 로직은 서블릿처럼 독립된 곳에서 처리하고, JSP는 HTML View를 생성하는 작업에 집중할 수 있도록 도와주는 MVC 패턴이 등장하게 된다.
---
## MVC 패턴


---
## 서블릿 구조 개선




