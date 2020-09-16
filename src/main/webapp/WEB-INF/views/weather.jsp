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

</head>
<body>
	<div class="container">

		<form action="list" method="GET">
			<input type="text" name="q"> <input type="submit"
				value="Submit" />
		</form>
		<br> ${message} <br>

		<table border="1" cellpadding="10">
			<thead>
				<tr>
					<th>City</th>
					<th>Time</th>
					<th>Temperature</th>
					<th>Description</th>
					<th>Wind Speed</th>
					<th>Humidity</th>
					<th>Speed</th>
				</tr>
			</thead>
			<tbody>

				<tr>
					<td><a href="/weatherapp/${weather.city}">${weather.city}</a></td>
					<td>${weather.date}</td>
					<td>${weather.temp}</td>
					<td>${weather.des}</td>
					<td>${weather.speed}</td>
					<td>${weather.humidity}</td>
					<td>${weather.pressure}</td>
				</tr>

			</tbody>
		</table>

	</div>

	<script src="<c:url value="/resources/js/weather.js" />"></script>
</body>
</html>
