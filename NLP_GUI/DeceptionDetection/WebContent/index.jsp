<!DOCTYPE html>
<html lang="en">

	<head>
		<title>Index</title>
		<jsp:include page="bootstrapinclude.jsp"/>
	</head>

   <body>
   <div class="container">
       <h1>Welcome to the <strong>Deception Detection Project</strong> testing session !</h1>
       <form ACTION="testresult.jsp" METHOD="POST">
           Please enter your text below
           <br>
           <textarea name="textarea1" rows="8" cols="80"></textarea>
           <br>
           <input type="submit" value="Submit">
       </form>
   </div>
   </body>
   
</html>
