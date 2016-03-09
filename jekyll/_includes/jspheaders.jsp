{% for type in page.jsp.imports %}
<%@ page import="{{ classPath }}" %>{% endfor %}
{% for kv in page.jsp.taglib %}
<%@ taglib prefix="{{ kv[0] }}" uri="{{ kv[1] }}" %>{% endfor %}
