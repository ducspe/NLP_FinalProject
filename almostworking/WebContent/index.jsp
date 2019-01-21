<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<title>Index</title>
		<jsp:include page="bootstrapinclude.jsp"/>
		<script type="text/javascript">
			$('trainBtn').click(function(){
			   $('waitMsg').show();
			});
		</script>
	</head>

   <body>
   <div class="container">
       <h1>Welcome to the <strong>Deception Detection Project</strong> testing session !</h1>
       
       <form action="${tut5classification.gui.GUI}" method="post">
           Please enter your text below
           <br>
           <textarea name="textarea1" rows="8" cols="80"></textarea>
           <br>
           <input type="submit" class="btn btn-success col-xs-3" name="action" value="detect">
       </form>
       
       <form action="${tut5classification.gui.GUI}" method="post" >
       		<input id="trainBtn" type="submit" class="btn btn-warning col-xs-3" name="action" value="train" />
       </form>
       
       <br/><br/><br/>
       
       <div id="waitMsg" class="hidden">
       		<p>Please about one minute until the training takes place. You will be redirected later...<p>
       </div>
       
   </div>
   </body>
   
</html>
