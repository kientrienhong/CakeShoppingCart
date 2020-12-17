<%-- 
    Document   : checkOut
    Created on : Oct 12, 2020, 9:44:04 AM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check out Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="DTO" value="${sessionScope.DTO}"/>
        <div class="d-flex justify-content-center" style="margin-top: 2%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Check out form
                </div>
                <div class="card-body text-center">
                    <c:set var="error" value="${requestScope.ERROR_CHECKOUT}"></c:set>
                        <form action="checkOutMember">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Recipient's Name: </label>
                                <div class="col-5">
                                    <input type="text" name="txtRecipientName" 
                                    <c:if test="${not empty DTO}">
                                        value="${DTO.name}"
                                    </c:if>
                                    <c:if test="${empty DTO}">
                                        value="${param.txtRecipientName}" 
                                    </c:if>                                   
                                    class="form-control"/>
                            </div>
                        </div>

                        <c:if test="${not empty error.nameError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.nameError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Address: </label>
                            <div class="col-5">
                                <input type="text" name="txtAddress" 
                                       class="form-control"
                                       <c:if test="${not empty DTO}">
                                           value="${DTO.address}"
                                       </c:if>
                                       <c:if test="${empty DTO}">
                                           value="${param.txtAddress}" 
                                       </c:if> />
                            </div>
                        </div>                            
                        <c:if test="${not empty error.addressError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.addressError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Phone: </label>
                            <div class="col-5">
                                <input type="text" name="txtPhone" 
                                       <c:if test="${not empty DTO}">
                                           value="${DTO.phone}"
                                       </c:if>
                                       <c:if test="${empty DTO}">
                                           value="${param.txtPhone}" 
                                       </c:if>  class="form-control"/>
                            </div>
                        </div>
                        <c:if test="${not empty error.phoneError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.phoneError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <input type="hidden" name="txtUserId" value="${DTO.id}" />
                        <button type="button" data-toggle="modal" data-target="#confirmModal" class="btn btn-primary" >Check out</button>

                        <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Confirm check out</h5> 
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                        <input type="submit" value="Check out" class="btn btn-success" name="btAction"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
