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

	       <h3 style="color:red;">Verdict : ${message}</h3>
	    
	    </div>
    </body>
    
</html>      