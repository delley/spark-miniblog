<!DOCTYPE html>

<html>
<head>
  <title>Bem Vindo</title>
  <link rel='stylesheet' href='webjars/bootstrap/4.0.0/css/bootstrap.min.css'>
  <style type="text/css">
  .label {text-align: right}
  .error {color: red}
  </style>

</head>

<body>
	<div class="jumbotron">
		<h1 class="display-4">Hello, world!</h1>
		<p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
		<hr class="my-4">
		<p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
		<p class="lead">
			<a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
		</p>
	</div>
	Seja bem vindo, ${username}
	<ul>
		<li><a href="/">Home</a></li>
		<li><a href="/logout">Logout</a></li>
		<li><a href="/posts">Criar Novo Post</a></li>
	</ul>
</body>
</html>
