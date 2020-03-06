<h1>Java / Spring Boot Notebook Server</h1>

<p>Interactive notebooks are experiencing a rise in popularity. Notebooks offer an environment for Data scientists to comfortably share research, collaborate with others and explore and visualize data. The data usually comes from executable code that can be written in the client (e.g. Python, SQL) and is sent to the server for execution. Popular notebook technologies which this approach are Apache Zeppelin and Jupyter Notebooks.
</p>
<h1>Installation (on Windows machines)</h1>
The project requires :
<ul>
<li>Maven 3+ installed and configured in system path</li>
<li>Java 8+ installed and configured in system path</li>
</ul>
See <a href ="https://www.mkyong.com/maven/how-to-install-maven-in-windows/">Installation/Configuration of Maven/Java</a>
<br/>
<h1>Usage</h1>
<p>Create a POST request to the <code>/execute</code> endpoint with a JSON object such as:</p>
<pre><code>{
“code”: “%&lt;interpreter-name&gt; &lt;code&gt;”
}
</code></pre>
<p>The endpoint parses this input and compute what the output of the python program is.
The code is formatted like this:</p>
<pre><code>%&lt;interpreter-name&gt;&lt;whitespace&gt;&lt;code&gt;
</code></pre>
<p>The returned output is :</p>
<pre><code>{
“result”: “&lt;interpretation-result&gt;”
}
</code></pre>
<p>If a user uses a variable in a piece of code, it will be accessible on subsequent executions. For example. The following requests are send:</p>
<pre><code>{
“code”: “%python a = 1”
}
</code></pre>
<p>This returns:</p>
<pre><code>{
“result”: “”
}
</code></pre>
<p>Then a second piece of code is sent, which uses a result from the previous request. The state of the interpreter is preserved after each call:</p>
<pre><code>{
“code”: “%python print a+1”
}
</code></pre>
<p>This returns :</p>
<pre><code>{
“result”: “2”
}
</code></pre>
<p>
Sessions
The application can be used by multiple users at the same time, it differentiate them from information in the request.
Requests with the same sessionId can access the same variables, but requests with a different sessionId don’t have this access.
</p>
<h1>Server response codes</h1>
<ul>
<li>
<p><strong>200 OK</strong>:</p>
<p>The interpreter API returns a 200 OK response code when the interpreter successfully execute the code.</p>
<p>The response will have the format described above and might containg erros in case of execution failure.</p>
</li>
<li>
<p><strong>400 BAD_REQUEST</strong>:</p>
<p>The API might return a 400 BAD_REQUEST as response code in case the request doesnt follow the correct format or the language is not supported.</p>
</li>
<li>
<p><strong>500 INTERNAL_SERVER_ERROR</strong>:</p>
<p>If this happens, it means that something went wrong with the server, just wait for a while and try again.</p>
</li>
</ul>
<h1>Further help</h1>
<p>To get started with GraalVM visite <a href="https://www.graalvm.org/" rel="nofollow">the home page</a>.</p>
<p>To get more information about spring boot and maven go to <a href="https://spring.io/guides/gs/spring-boot/" rel="nofollow">the official guides</a>.</p>
<hr>
<p>This project was generated from <a href="https://start.spring.io" rel="nofollow">Spring Initializr</a>.</p>
