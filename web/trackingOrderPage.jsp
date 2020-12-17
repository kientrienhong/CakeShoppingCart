<%-- 
    Document   : trackingOrderPage
    Created on : Oct 13, 2020, 9:42:50 AM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tracking Order Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="DTO" value="${sessionScope.DTO}"/>
        <div class="d-flex justify-content-center" style="margin-top: 2%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Tracking order
                </div>
                <form action="trackOrderServletMember">
                    <div class="card-body text-center">
                        <div class="form-group row">
                            <div class="col-3">
                                <label for="idOrder">ID Order: </label>
                            </div>
                            <div class="col-9">
                                <input class="form-control mr-sm-2" id="idOrder" type="text" name="txtIdOrder" value="${param.txtIdOrder}" />
                            </div>
                        </div> 
                        <input type="hidden" name="txtUserId" value="${DTO.id}" />
                        <input type="submit" value="Track" class="btn btn-primary"/>
                    </div>
                </form>
                <a href="loadInfo" class="text-center" style="padding-bottom: 1%">Go back to store page</a>
            </div>
        </div>
        <c:if test="${not empty param.txtIdOrder}">
            <c:set var="orderDetail" value="${requestScope.ORDER_DETAIL}"/>
            <c:if test="${not empty orderDetail}" var="checkOrder">
                <c:set var="list" value="${requestScope.LIST_ORDER_DETAIL}"/>
                <c:set var="listNameProduct" value="${requestScope.LIST_NAME}"/>
                <c:set var="payment" value="${requestScope.PAYMENT}"/>
                <div class="d-flex justify-content-center" style="margin: 1%">
                    <div class="card border-secondary w-50 text-center">
                        <div class="card-header">
                            Order Detail
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <label>Order id: ${orderDetail.orderId}</label>
                            </div>
                            <div class="form-group">
                                <label>Order date: ${orderDetail.orderDate}</label>
                            </div>
                            <div class="form-group">
                                <label>Shipping address: ${orderDetail.shippingAddress}</label>
                            </div>
                            <div class="form-group">
                                <label>Total price: $${orderDetail.totalPrice}</label>
                            </div>
                            <div class="form-group">
                                <label>Order: ${orderDetail.userId}</label>
                            </div>
                            <div class="form-group">
                                <label>Phone: ${orderDetail.phone}</label>
                            </div>
                            <div class="form-group">
                                <label>Type of paid: ${payment.type}</label>
                            </div>
                            <div class="form-group">
                                <label>Paid: ${payment.status}</label>
                            </div>
                        </div>
                    </div>
                </div>       
                <table class="table table-sm table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">No.</th>
                            <th scope="col">Name</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Price</th>
                            <th scope="col">Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${list}" varStatus="counter">
                            <tr>
                                <td scope="row">${counter.count}</td>
                                <td>${listNameProduct.get(counter.index)}</td>
                                <td>
                                    ${item.quantity}
                                </td>
                                <td>$${item.price}</td>
                                <td>$${item.price * item.quantity}</td>
                            </tr>                        
                        </c:forEach>
                        <tr>
                            <td scope="row" colspan="4">Total Price: </td>
                            <td colspan="1">$${orderDetail.totalPrice}</td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${!checkOrder}">
                <h3 class="text-center" style="margin-top: 1%">Not found!</h3>
            </c:if>
        </c:if>
    </body>
</html>
