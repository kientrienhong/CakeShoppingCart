<%-- 
    Document   : viewCart
    Created on : Jul 11, 2020, 12:12:25 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    </head>
    <body>
        <h1 class="text-center">Your cart</h1>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:set var="products" value="${cart.getItems()}"/>
        <c:if test="${not empty products}">
            <table class="table table-sm table-bordered">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Name</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Total</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${products.keySet()}" varStatus="counter">
                        <c:set var="product" value="${products.get(item)}"/>
                    <form action="deleteItemInCartMember">
                        <div class="modal fade" id="confirmModal${counter.index}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Are you sure want to delete this item?</h5> 
                                        <input type="hidden" name="txtId" value="${product.id}" />
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <input type="submit" value="Delete item" class="btn btn-danger" name="btAction"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <tr>
                        <td scope="row">${counter.count}</td>
                        <td>${product.name}</td>
                        <td>
                            <form action="modifyQuantityMember">
                                <input type="hidden" name="txtId" value="${product.id}" />
                                <input type="submit" value="-" class="btn btn-primary"/>
                                <input type="hidden" name="txtIsIncrease" value="no" />
                            </form>
                            ${product.quantity}
                            <form action="modifyQuantityMember">
                                <input type="hidden" name="txtId" value="${product.id}" />
                                <input type="hidden" name="txtIsIncrease" value="yes" />
                                <input type="submit" value="+" class="btn btn-primary"/>
                            </form>
                            <c:if test="${not empty requestScope.ERROR_OUT_STOCK && product.id eq param.txtId}">
                                <div class="alert alert-danger col-12" role="alert">
                                    ${requestScope.ERROR_OUT_STOCK}
                                </div>
                            </c:if>
                        </td>
                        <td>$${product.price}</td>
                        <td>$${product.price * product.quantity}</td>
                        <td>
                            <button type="button" data-toggle="modal" data-target="#confirmModal${counter.index}" class="btn btn-danger col-2" style="margin-left: 100px">Delete Article</button>
                        </td>
                    </tr>                        
                </c:forEach>
                <tr>
                    <td scope="row" colspan="4">Total Price: </td>
                    <td colspan="2">$${cart.totalPrice()}</td>
                </tr>
                <tr>
                    <td scope="row" colspan="4">                                
                        <a href="loadInfo">Go to shopping again</a>
                    </td>
                    <td colspan="2">
                        <form action="checkQuantity">
                            <input type="submit" value="Check out" name="btAction" class="btn btn-success"/>
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty products}">
        <p class="text-center">Your cart doesn't exist</p>
        <div class="text-center">
            <a href="">Back to store</a>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.ERROR_CHECKOUT}">
        <div class="alert alert-danger col-12 text-center" role="alert">
            ${requestScope.ERROR_CHECKOUT.quantityError}
        </div>
    </c:if>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
