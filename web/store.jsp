<%-- 
    Document   : store
    Created on : Oct 3, 2020, 9:17:41 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>Store Page</title>
        <style>
            .card-columns{
                column-count: 4;
                margin-top: 2%;
            }
        </style>
    </head>
    <body>
        <c:set var="DTO" value="${sessionScope.DTO}"/>
        <c:set var="LIST_CATEGORY" value="${requestScope.LIST_CATEGORY}"/>
        <c:set var="errorPrice" value="${requestScope.ERROR_PRICE}"/>
        <c:set var="ROLE_NAME" value="${requestScope.NAME_ROLE}"/>
        <nav class="navbar navbar-light bg-light justify-content-between" style="position: relative; width: 100%">
            <a class="navbar-brand">Cake store</a>
            <c:if test="${empty DTO}" var="checkLogin">
                <a href="login" class="btn btn-primary">Login</a>
            </c:if>
            <c:if test="${ROLE_NAME != 'admin'}">
                <form action="viewCartMember" class="form-inline">
                    <input type="submit" value="Cart(${requestScope.CART_SIZE})" class="btn btn-primary" style="position: absolute; right: 10%; top: 50%; transform: translate(-50%, -50%)"/>
                </form>
            </c:if>
            <c:if test="${ROLE_NAME == 'member' || ROLE_NAME == 'googleAccount'}">
                <a href="trackOrderPageMember" class="btn btn-primary">Track your order</a>
            </c:if>
            <c:if test="${!checkLogin}">
                <c:if test="${ROLE_NAME eq 'admin'}">
                    <form action="loadInfo" class="form-inline">
                        <input type="hidden" name="txtIsCreatePage" value="yes" />
                        <input type="submit" value="Create cake" class="btn btn-primary" style="position: absolute; right: 10%; top: 50%; transform: translate(-50%, -50%)"/>
                    </form>
                </c:if>
                <form class="form-inline" action="logOut">
                    <h4 class="mr-sm-2" style="position: absolute; left: 50%; top: 50%; transform: translate(-50%, -50%)">
                        ${DTO.name}
                    </h4>
                    <input class="btn btn-danger" type="submit" value="Log out"/>
                </form>
            </c:if>
        </nav>
        <div class="d-flex justify-content-center" style="margin-top: 2%">
            <div class="card" style="width: 50%">
                <div class="card-body">
                    <form action="search">
                        <div class="form-group row">
                            <div class="col-2">
                                <label class="align-middle" for="inputName">Name</label>
                            </div>
                            <div class="col-4">
                                <input class="form-control mr-sm-2" id="inputName" type="search" placeholder="Name..." aria-label="Search" name="txtName" value="${param.txtName}">
                            </div>
                            <div class="col-3">
                                <label for="exampleFormControlSelect1" class="mr-sm-2" style="margin-left: 50px">Category</label>
                            </div>
                            <div class="col-3">
                                <select name="categoryCB" class="form-control mr-sm-2" id="exampleFormControlSelect1">
                                    <c:forEach items="${LIST_CATEGORY}" var="categoryDTO">
                                        <option value="${categoryDTO.id}_${categoryDTO.name}">${categoryDTO.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-2">
                                <label class="align-middle">Price Range</label>
                            </div>
                            <div class="col-10">
                                <div class="form-group row">
                                    <div class="col-2">
                                        <label for="inputPriceFrom">From: </label>
                                    </div>
                                    <div class="col-8">
                                        <input class="form-control mr-sm-2" id="inputPriceFrom" type="text" name="txtPriceFrom" value="${param.txtPriceFrom}" />
                                    </div>
                                </div>
                                <c:if test="${not empty errorPrice.priceFromError}">
                                    <div class="form-group row">
                                        <div class="col-2"></div>
                                        <div class="alert alert-danger col-8" role="alert">
                                            ${errorPrice.priceFromError}
                                        </div>
                                    </div>
                                </c:if>
                                <div class="form-group row">
                                    <div class="col-2">
                                        <label for="inputPriceTo">To: </label>
                                    </div>
                                    <div class="col-8">
                                        <input class="form-control mr-sm-2" id="inputPriceTo" type="text" name="txtPriceTo" value="${param.txtPriceTo}" />
                                    </div>
                                </div>
                                <c:if test="${not empty errorPrice.priceToError}">
                                    <div class="form-group row">
                                        <div class="col-2"></div>
                                        <div class="alert alert-danger col-8" role="alert">
                                            ${errorPrice.priceToError}
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty errorPrice.priceError}">
                                    <div class="form-group row">
                                        <div class="col-2"></div>
                                        <div class="alert alert-danger col-8" role="alert">
                                            ${errorPrice.priceError}
                                        </div> 
                                    </div>
                                </c:if>

                            </div>
                        </div>
                        <input type="hidden" name="txtUserRoleId" value="${DTO.roleId}"/>
                        <div class="d-flex justify-content-center">
                            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="Search"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <c:if test="${not empty param.categoryCB}">
            <c:set var="LIST_PRODUCT" value="${requestScope.LIST_PRODUCT}"/>
            <c:if test="${not empty LIST_PRODUCT}" var="checkSearch">
                <div class="card-columns">
                    <c:forEach items="${LIST_PRODUCT}" var="productDTO">
                        <div class="card"> 
                            <img class="card-img-top" src="./img/${productDTO.image}" alt="Card image cap" style="height: 400px">
                            <div class="card-body">
                                <h5 class="card-title text-center">Name: ${productDTO.name}</h5>
                                <p class="card-text text-center">Description: ${productDTO.description}</p>
                                <p class="card-text text-center">Category: ${productDTO.categoryName}</p>
                                <p class="card-text text-center">Date Created: ${productDTO.dateCreated}</p>
                                <p class="card-text text-center">Date Expired: ${productDTO.dateExpired}</p>
                                <p class="card-text text-center">Remain: ${productDTO.quantity}</p>
                                <p class="card-text text-center">Price: ${productDTO.price}$</p>
                            </div>
                            <div class="card-footer text-center" style="width: 100%">
                                <div class="form-group row text-center" style="width: 20%; margin: 0 auto">
                                    <c:if test="${ROLE_NAME != 'admin'}">
                                        <c:url var="addCart" value="addProductToCartMember">
                                            <c:param name="categoryCB" value="${param.categoryCB}"/>
                                            <c:param name="txtPriceFrom" value="${param.txtPriceFrom}"/>
                                            <c:param name="txtPriceTo" value="${param.txtPriceTo}"/>
                                            <c:param name="txtCurrentPage" value="${param.txtCurrentPage}"/>
                                            <c:param name="txtName" value="${param.txtName}"/>
                                            <c:param name="txtId" value="${productDTO.id}"/>
                                        </c:url>
                                        <a href="${addCart}" class="btn btn-primary">Add to Cart</a>
                                    </c:if>
                                    <c:if test="${ROLE_NAME eq 'admin'}">
                                        <form action="getStatusServletAdmin" method="POST">
                                            <input type="hidden" name="txtIsUpdatePage" value="yes" />
                                            <input type="hidden" name="txtId" value="${productDTO.id}" />
                                            <input type="hidden" name="txtNameUpdate" value="${productDTO.name}" />
                                            <input type="hidden" name="txtDescription" value="${productDTO.description}" />
                                            <input type="hidden" name="txtCategoryId" value="${productDTO.categoryId}" />
                                            <input type="hidden" name="txtExpiredDate" value="${productDTO.dateExpired}" />
                                            <input type="hidden" name="txtQuantity" value="${productDTO.quantity}" />
                                            <input type="hidden" name="txtPrice" value="${productDTO.price}" />
                                            <input type="hidden" name="categoryCB" value="${param.categoryCB}" />
                                            <input type="hidden" name="txtPriceFrom" value="${param.txtPriceFrom}" />
                                            <input type="hidden" name="txtPriceTo" value="${param.txtPriceTo}" />
                                            <input type="hidden" name="txtCurrentPage" value="${param.txtCurrentPage}" />
                                            <input type="hidden" name="txtName" value="${param.txtName}" />
                                            <input type="hidden" name="txtUserRoleId" value="${DTO.roleId}" />
                                            <input type="submit" value="Update" class="btn btn-success" />
                                        </form>
                                    </c:if>
                                </div>
                                <c:if test="${not empty requestScope.ERROR_OUT_STOCK && productDTO.id eq param.txtId}">
                                    <div class="alert alert-danger col-12" role="alert">
                                        ${requestScope.ERROR_OUT_STOCK}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <c:set var="totalPage" value="${requestScope.TOTAL_PAGE}"></c:set>
                <c:if test="${totalPage gt 1}">
                    <ul class="pagination justify-content-center">
                        <c:forEach var="page" begin="1" step="1"  end="${totalPage}">
                            <li <c:if test="${page eq currentPage}" var="checkCurrentPage">
                                    class="page-item active"
                                </c:if>
                                <c:if test="${!checkCurrentPage}">
                                    class="page-item"
                                </c:if>>
                                <c:url var="searchPage" value="search">
                                    <c:param name="txtName" value="${param.txtName}"></c:param>
                                    <c:param name="categoryCB" value="${param.categoryCB}"/>
                                    <c:param name="txtPriceFrom" value="${param.txtPriceFrom}"/>
                                    <c:param name="txtPriceTo" value="${param.txtPriceTo}"/>
                                    <c:param name="txtCurrentPage" value="${page}"/>
                                    <c:param name="txtUserRoleId" value="${DTO.roleId}"/>
                                </c:url>
                                <a class="page-link" href="${searchPage}">${page}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </c:if>
            <c:if test="${!checkSearch && empty errorPrice}">
                Not found!
            </c:if>
        </c:if>
        <c:if test="${not empty requestScope.CHECK_OUT_SUCCESS}">
            <script>
                window.addEventListener("load", function () {
                    alert("${requestScope.CHECK_OUT_SUCCESS}");
                }
            </script>
        </c:if>

    </body>
</html>
