<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Verdict</title>
		<jsp:include page="bootstrapinclude.jsp"/>
    </head>
    <body>
		<div class="container">
	        <h1 class="text-center">Test Results</h1>
	        <br/>
	        <h2>You typed:</h2>
	        <h3 style="color:blue;"> 
	        <%
	        StringBuffer text = new StringBuffer(request.getParameter("textarea1"));
	  
	        int loc = (new String(text)).indexOf('\n');
	        while(loc > 0){
	            text.replace(loc, loc+1, "<br>");
	            loc = (new String(text)).indexOf('\n');
	       }
	       out.println(text); 
	       
	       
	       %>
	       </h3>

	       <br/>

	       <h3 style="color:red;">Press Button to see the verdict :</h3>
	       <form action="${tut5classification.gui.GUI}" method="post" >
       			<input type="submit" class="btn btn-success col-xs-3" name="action" value="showresult" />
       	   </form>
	    
	    </div>
    </body>
    
</html>      