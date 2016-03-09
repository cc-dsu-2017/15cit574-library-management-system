---
layout: auth
title: Sign up
jsp:
  taglib:
    form: http://www.springframework.org/tags/form
---
<p>
  Welcome to the Spring Security GAE sample application, <sec:authentication property="principal.nickname" />.
  Please enter your registration details in order to use the application.
</p>

<form:form id="signup" method="post" modelAttribute="signupForm" cssClass="form">
  <fieldset>
    <div class="form-group label-floating">
      <label class="control-label">Email</label>
      <p class="form-control" >${user.email}</p>
      <p class="help-block">You should really write something here</p>
    </div>
    <div class="form-group label-floating">
      <form:label path="givenName" cssClass="control-label">Given Name</form:label>
      <form:errors path="givenName" cssClass="fieldError" />
      <form:input path="givenName" cssClass="form-control" />
      <p class="help-block">You should really write something here</p>
    </div>
    <div class="form-group label-floating">
      <form:label path="familyName" cssClass="control-label">Family Name</form:label>
      <form:errors path="familyName" cssClass="fieldError" />
      <form:input path="familyName" cssClass="form-control"  />
    </div>
  </fieldset>
  <input type="submit" class="btn btn-primary" value="Sign Up" />
</form:form>
