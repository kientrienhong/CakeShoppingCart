<%-- 
    Document   : checkOutSuccess
    Created on : Oct 13, 2020, 9:23:45 AM
    Author     : Treater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check out success</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <div class="d-flex justify-content-center" style="margin-top: 2%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Check out successfully
                </div>
                <div class="card-body text-center">
                    <div class="form-group row">
                        <div class="col-3">
                            <label>Your order id: </label>
                        </div>
                        <div class="col-9">
                            <label>${requestScope.ORDER_ID}</label>
                        </div>
                    </div>
                </div>
                <a href="loadInfo" class="text-center">Return to store page</a>
            </div>
        </div>
    </body>
</html>
