<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Weather App</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script type="text/javascript">
	$(document).on('click', '#delete-button', function() {
		var id = $(this).parent().parent().children()[0].textContent;
		var city = $(this).parent().parent().children()[1].textContent;
		$.ajax({
			type : "POST",
			url : "/weatherapp/delete/" + id,
			cache: false,
			async: false,
			success : function() {
				alert("Deleted!");
				window.location.reload();
			}
		})
	});
</script>
</head>
<body>
	<div class="container">

		${message}

		<table border="1" cellpadding="10">
			<thead>
				<tr>
					<th>Time</th>
					<th>Temperature</th>
					<th>Description</th>
					<th>Wind Speed</th>
					<th>Humidity</th>
					<th>Speed</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>


				<c:forEach items="${weather}" var="w">
					<tr>
						<td style="display: none;">${w.id}</td>
						<td style="display: none;">${w.city}</td>
						<td>${w.date}</td>
						<td>${w.temp}</td>
						<td>${w.des}</td>
						<td>${w.speed}</td>
						<td>${w.humidity}</td>
						<td>${w.pressure}</td>
						<!-- <td><a href="/weatherapp/delete/${w.id}">Delete</a></td> -->
						<td><a href="" id="delete-button">Delete</a></td>
					</tr>
				</c:forEach>


			</tbody>
		</table>

	</div>


</body>
</html>
