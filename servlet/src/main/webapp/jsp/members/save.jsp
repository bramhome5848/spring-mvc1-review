<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // request, response 사용 가능
    // 회원 저장 서블릿 코드(MemberSaveServlet)와 같다
    // 다른점이 있다면 HTML 중심으로 하고, 자바코드를 부분부분 입력하였다.
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("save.jsp");
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    System.out.println("member = " + member);
    memberRepository.save(member);

    /*
     * 서블릿과 JSP 한계
     * 서블릿 - 뷰 화면을 위한 HTML 을 만드는 작업이 자바 코드에 섞여 지저분하고 복잡하다.
     * JSP
     - 코드의 상위 절반은 저장을 위한 비즈니스 로직이고, 나머지 하위는 결과를 HTML 로 보여주기 위한 뷰 영역이다.
     - JAVA 코드, 데이터를 조회하는 리포지토리 등등 다양한 코드가 모두 JSP 에 노출되어 있다.
     - JSP 가 너무 많은 역할을 가지게 되고 유지 보수가 쉽지 않다.

     * MVC 패턴의 등장
     - 비즈니스 로직은 서블릿 처럼 따로 처리하고, JSP 는 목적에 맞게 HTML 로 화면(View)을 그리는 일에 집중하도록 분리
     */
%>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>