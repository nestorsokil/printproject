<%
    request.getSession().invalidate();
    response.sendRedirect(request.getContextPath());
%>
fake logout page