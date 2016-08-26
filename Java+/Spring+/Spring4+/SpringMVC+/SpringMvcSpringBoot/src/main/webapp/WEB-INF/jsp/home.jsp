<html>
<head>
<title>Spring MVC Home Page</title>
</head>
<body>
	<ul>
		<li><a href="/hello">Hello</a></li>
		<li><a href="/form/student">Form</a></li>
		<li><a href="/redirect">Redirect</a></li>
		<li>Response
			<ul>
				<li><a href="/respose_entity">ResponseEntity</a></li>
				<li><a href="/respose_body">@ResponseBody</a></li>
			</ul>
		</li>
		<li>Exception
			<ul>
				<li><a href="/throw_io_exception">Throw an IOException</a></li>
				<li><a href="/throw_enexpected_exception">Throw an
						unexpected exception</a></li>
			</ul>
		</li>
		<li>Arguments
			<ul>
				<li><a href="/http">Http request, response and session</a></li>
				<li><a href="/request_param?code=abc">@RequestParam (by specified name)</a></li>
				<li><a href="/all_request_params?name=John&surname=Bush">@RequestParam (all params)</a></li>
				<li><a href="/path_variable/find/1234">@PathVariable</a></li>
			</ul>
		</li>

	</ul>
</body>
</html>