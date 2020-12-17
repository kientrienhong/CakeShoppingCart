<%-- 
    Document   : login
    Created on : Oct 4, 2020, 11:13:35 AM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <div class="d-flex justify-content-center" style="margin-top: 10%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Login Page
                </div>
                <div class="card-body">
                    <form action="loginServlet" method="POST">
                        <div class="form-group">
                            <label>Email: </label>
                            <input class="form-control col-6" type="text" name="txtEmail" value="" /> 
                        </div>
                        <div class="form-group">
                            <label>Password: </label>
                            <input class="form-control col-6" type="password" name="txtPassword" value="" /> 
                        </div>
                        <c:set var="invalid" value="${requestScope.INVALID}"/>
                        <c:if test="${not empty requestScope.INVALID}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${requestScope.INVALID}
                            </div>
                        </c:if>
                        <input type="submit" value="Login" class="btn btn-primary" name="btAction"/>
                        <a class="btn btn-primary" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/JavaWebLab2/loginGoogle&response_type=code
                           &client_id=16791085400-tldk9nm6lckuoe7lpl54hemqbdhc1q5s.apps.googleusercontent.com&approval_prompt=force">Login With Google</a>  
                        <a href="">Back to store</a>
                    </form>
                </div>
            </div>
        </div>    
    </body>
</html>
