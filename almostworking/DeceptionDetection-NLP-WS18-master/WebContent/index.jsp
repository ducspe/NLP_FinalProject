<!DOCTYPE html>
<html lang="en">

	<head>
		<title>Index</title>
		<jsp:include page="bootstrapinclude.jsp"/>
	</head>

   <body>
   <div class="container">
       <h1>Welcome to the <strong>Deception Detection Project</strong> testing session !</h1>
       <form action="/tut5classification/GUI" method="post">
           Please enter your text below
           <br>
           <textarea name="textarea1" rows="8" cols="80"></textarea>
           <br>
           <input type="submit" name="action" value="detect">
       </form>
       
       <form action="/tut5classification/GUI" method="post" >
       		<input type="submit" name="action" value="train" />
       </form>
   </div>
   </body>
   
</html>
