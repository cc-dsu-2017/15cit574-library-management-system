{% include jspdefaults.jsp %}
{% include jspheaders.jsp %}
<!doctype html>
<html class="no-js" lang="">
  {% include head.jsp %}
  <body class="{{ page.body.class }}">
    <div class="image-preloader"></div>
    {% assign levels = page.url | split: '/' %}

    {% include header.jsp %}

    {{ content }}

    {% include footer.jsp %}

    {% if config.google.analytics %}
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
      ga('create', '{{site.config.google.analytics.id}}', 'auto');
      ga('send', 'pageview');
    </script>
    {% endif %}

    <!-- Built with love using Web Starter Kit -->
  </body>

</html>
