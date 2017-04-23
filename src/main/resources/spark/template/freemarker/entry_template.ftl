<!doctype HTML>
<html
<head>
    <title>
        Blog Post
    </title>
</head>
<body>
<#if username??>
    Bem vindo, ${username} <a href="/logout">Logout</a> | <a href="/posts">New Post</a>

    <p>
</#if>

<a href="/">Blog Home</a><br><br>

<h2>${post["title"]}</h2>
Posted ${post["date"]?datetime}<i> By ${post["author"]}</i><br>
<hr>
${post["body"]}
<p>
    <em>Tags</em>:
    <#if post["tags"]??>
        <#list post["tags"] as tag>
            ${tag}
        </#list>
    </#if>
<p>
</body>
</html>


