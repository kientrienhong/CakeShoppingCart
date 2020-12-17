<%-- 
    Document   : createCake
    Created on : Oct 5, 2020, 5:51:31 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create cake page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="error" value="${requestScope.ERROR_CREATE}"/>
        <c:set var="LIST_CATEGORY" value="${requestScope.LIST_CATEGORY}"/>

        <div class="d-flex justify-content-center" style="margin-top: 10%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Create cake
                </div>
                <div class="card-body">
                    <form action="createCakeServletAdmin">
                        <div class="form-group">
                            <label>Name: </label>
                            <input class="form-control col-6" type="text" name="txtName" value="${param.txtName}" /> 
                        </div>
                        <c:if test="${not empty error.nameError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.nameError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Description: </label>
                            <input class="form-control col-6" type="text" name="txtDescription" value="${param.txtDescription}" /> 
                        </div>
                        <c:if test="${not empty error.descriptionError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.descriptionError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Price ($): </label>
                            <input type="text" name="txtPrice" class="form-control col-6" value="${param.txtPrice}"/> 
                        </div>
                        <c:if test="${not empty error.priceError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.priceError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Expired Date:  </label>
                            <input type="text" name="txtExpiredDate" class="form-control col-6" placeholder="yyyy-MM-dd" value="${param.txtExpiredDate}">
                        </div>
                        <c:if test="${not empty error.expiredDateError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.expiredDateError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Quantity:  </label>
                            <input type="text" name="txtQuantity" class="form-control col-6" value="${param.txtQuantity}">
                        </div>
                        <c:if test="${not empty error.quantityError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.quantityError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Category: </label>
                            <select name="categoryId" class="form-control col-4" id="exampleFormControlSelect1">
                                <c:forEach items="${LIST_CATEGORY}" var="categoryDTO">
                                    <option value="${categoryDTO.id}">${categoryDTO.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Upload file image: </label>
                            <input type="file" id="myFile" name="filename" value="filename"></input>
                        </div>
                        <c:if test="${not empty error.imageError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.imageError}
                            </div>
                        </c:if>
                        <input type="submit" value="Create" class="btn btn-primary"/>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
