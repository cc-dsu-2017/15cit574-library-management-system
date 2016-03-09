<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  {% include title.jsp %}
  <meta name="description" content="{% if page.excerpt %}{{ page.excerpt | strip_html | strip_newlines | truncate: 160 }}{% else %}{{ site.description }}{% endif %}">


  <!-- Disable tap highlight on IE -->
  <meta name="msapplication-tap-highlight" content="no">

  <!-- Web Application Manifest -->
  <link rel="manifest" href="manifest.json">

  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <meta name="application-name" content="Web Starter Kit">
  <link rel="icon" sizes="192x192" href="images/touch/chrome-touch-icon-192x192.png">

  <!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Web Starter Kit">
  <link rel="apple-touch-icon" href="images/touch/apple-touch-icon.png">

  <!-- Tile icon for Win8 (144x144 + tile color) -->
  <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
  <meta name="msapplication-TileColor" content="#2F3BA2">

  <!-- Color the status bar on mobile devices -->
  <meta name="theme-color" content="#2F3BA2">

  <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
  <!--
  <link rel="canonical" href="http://www.example.com/">
  -->

  <!-- Material Design Lite page styles:
  You can choose other color schemes from the CDN, more info here http://www.getmdl.io/customize/index.html
  Format: material.color1-color2.min.css, some examples:
  material.red-teal.min.css
  material.blue-orange.min.css
  material.purple-indigo.min.css
  -->
  <!-- <link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.indigo-pink.min.css"> -->

  <!-- Material Design icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" >
  <link href='https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en' rel='stylesheet' type='text/css'>

  <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
  <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

  <link rel="canonical" href="{{ page.url | replace:'index.html','' | prepend: site.baseurl | prepend: site.url }}">
  <link rel="alternate" type="application/rss+xml" title="{{ site.title }}" href="{{ "/feed.xml" | prepend: site.baseurl | prepend: site.url }}">

  {% for css in site.imports.css %}
  <link rel="stylesheet" href="{% if css[1].url %}{{ css[1].url }}{% else %}{{ css[1] }}{% endif %}" {% if css[1].media %} media="{{ css[1].media }}" {% endif %}/>
  {% endfor %} {% if site.imports.modernizr %}
  <script src="{{ site.imports.modernizr }}" type="text/javascript"></script>
  {% endif %}
  <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script>window.jQuery || document.write('<script src="{{ "/bower_components/jquery/dist/jquery.min.js" | prepend: site.baseurl }}"><\/script>')</script> -->
  <script src="{{ site.imports.jquery }}"></script>
  <script type="text/javascript">
    importScript = function(src, defer, id, callback) {
      var sp = document.createElement('script');
      sp.type = "text/javascript";
      sp.src = src;
      sp.async = true;
      id && (sp.id = id);
      defer && (sp.defer = "defer");
      callback && (sp.onload = sp.onreadystatechange = function() {
        var rs = this.readyState;
        if(rs && rs != 'complete' && rs != 'loaded') return;
        try{ callback(); } catch(e) {console.error(e)}
      });
      var s=document.getElementsByTagName('script')[0];
      s.parentNode.insertBefore(sp,s);
    };

    {% for js in site.imports.js %}
    importScript('{% if js[1].url %}{{ js[1].url }}{% else %}{{ js[1] }}{% endif %}',
      {% if js[1].defer %}{{ js[1].defer }}{% else %}false{% endif %},
     '{% if js[1].id %}{{ js[1].id }}{% else %}js-{{ js[0] }}{% endif %}',
      {% if js[1].callback %}{{ js[1].callback }}{% else %}null{% endif %});
    {% endfor %}
  </script>
</head>
