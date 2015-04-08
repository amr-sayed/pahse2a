<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title> Messges</title> 
<script type="text/javascript" src="/jquery/jquery.js"></script>
<style type="text/css"></style> 
<style type="text/css">div{padding:8px;}</style> 
</head>


<body>
	<h1>send messeg</h1>
	<input type="button" value="new reciver" id="addButton"> <input type="button" value="Remove recver" id="removeButton"> <input type="button" value="Get  Value s" id="getButtonValue">
	
	<script type="text/javascript">
			$(document).ready(
					function(){
						var counter=2;
						$("#addButton").click( function()
						{
									if(counter>10){
										alert("Only 10 textboxes allow");
										return false;
										}
									var newTextBoxDiv=$(document.createElement('div')).attr("id",'TextBoxDiv'+counter);
									newTextBoxDiv.after().html('<label>Frined #'+counter+' : </label>'+'<input type="text" name="textbox'+counter+'" id="textbox'+counter+'" value="" >');
									newTextBoxDiv.appendTo("#TextBoxesGroup");counter++;
						});
						$("#removeButton").click(function(){
							if(counter==1){ alert("No more textbox to remove"); return false;   }
						    counter--;
						    $("#TextBoxDiv"+counter).remove();
						});
						$("#getButtonValue").click(function()
						{
							
							 
							var msg=  '{"Messeg" : "'+$('#MM').val()+'" , "Recevers":{' ;
							
							for(i=1;i<counter;i++)
							{
								
								msg+='"Recever'+i+'" : "'+$('#textbox'+i).val()+'"';
								if(i+1 != counter) msg+=',';
							}
							msg+= '}}';
							alert(msg); 
							
							 
							
							
							 $.ajax({
							        url: '/social/sendmessgtouser',
							        type: 'POST',
							        data: { json:msg},
							        dataType: 'json'
							    });
							
						});
						
						
						
						
					});
	</script> 
	<div id="TextBoxesGroup"><div id="TextBoxDiv1"> <label>Frined #1 : </label><input type="text" name="textbox1" id="textbox1" value=""></div></div>
 
 
 	Messg : <input type="text" name="MM" id="MM" value="">
 
  <form action="/social/sendmessgtouser" method="post">
  
 <br>
  <input type="submit" value="invoke send">
  
 </body>


















 
</html>
