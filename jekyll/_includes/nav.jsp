<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">{{ site.title }}</a>
    </div>
    <div class="navbar-collapse collapse navbar-inverse-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/">Home</a></li>
      </ul>
      <form class="navbar-form navbar-left">
        <input type="search" class="form-control col-md-8" placeholder="Search">
      </form>
      <ul class="nav navbar-nav navbar-right">
        <sec:authorize access="isFullyAuthenticated()">
        <li><a href="/user/courses/">My Courses</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()" >
        <li><a class="" href="/dashboard"><span class="profile-pic-wrapper"><img src="${user.profileImageURL}" width="32" height="32" class="img-circle profile-pic" /></span> ${user.nickname}</a></li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)"> <span class="fa fa-caret-down"></span></a>
          <ul class="dropdown-menu">
            <sec:authorize access="isFullyAuthenticated()">
            <li><a href="javascript:void(0)">${fn:escapeXml(user.givenName)} ${fn:escapeXml(user.familyName)}</a></li>
            <li><a href="/user/profile/"><i class="fa fa-user fa-fw"></i> Profile</a></li>
            <li><a href="/user/settings/"><i class="fa fa-cogs fa-fw"></i> Settings</a></li>
            <li class="divider"></li>
            </sec:authorize>
            <li class=""><a href="/auth/signout/"> <i class="fa fa-ban fa-fw"></i> Sign out</a></li>
          </ul>
        </li>
        </sec:authorize>
        <sec:authorize access="isAnonymous()" >
        <li><a href="/auth/signin">Sign in</a></li>
        </sec:authorize>
      </ul>
    </div>
  </div>
</nav>
