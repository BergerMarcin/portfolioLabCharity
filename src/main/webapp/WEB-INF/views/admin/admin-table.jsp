<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="Marcin Berger, https://github.com/BergerMarcin">

  <title>Charity - Admin</title>

  <!-- Custom fonts for this template -->
  <link href="/resources/bootstrap/css/all.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="/resources/bootstrap/css/sb-admin-2.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="/resources/bootstrap/css/dataTables.bootstrap4.css" rel="stylesheet">

</head>

<body id="page-top">

  <%--  Seetings--%>
  <c:url var="url_main" value="/admin/bootstrap"/>
  <c:url var="url_categories" value="/admin/categories"/>
  <c:url var="url_institution" value="/admin/institution"/>
  <c:url var="url_donations" value="/admin/donations"/>
  <c:url var="url_users" value="/admin/users"/>
  <c:url var="url_admin" value="/admin/admin"/>

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${url_main}">
        <div class="sidebar-brand-icon">
          <i class="fas fa-fw fa-hand-holding-heart"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Charity Admin</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item">
        <a class="nav-link" href="${url_main}">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Admin Dashboard</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Tabele i operacje
      </div>

      <!-- Nav Item - Tables -->
<%--
      <li class="nav-item active">
        <a class="nav-link" href="${url_categories}">
          <i class="fas fa-fw fa-table"></i>
          <span>Kategorie datków</span></a>
      </li>
--%>
      <li class="nav-item active">
        <a class="nav-link" href="${url_institution}">
          <i class="fas fa-fw fa-table"></i>
          <span>Instytucje</span></a>
      </li>
<%--
      <li class="nav-item active">
        <a class="nav-link" href="${url_donations}">
          <i class="fas fa-fw fa-table"></i>
          <span>Datki</span></a>
      </li>
--%>
<%--
      <li class="nav-item active">
        <a class="nav-link" href="${url_users}">
          <i class="fas fa-fw fa-table"></i>
          <span>Użytkownicy</span></a>
      </li>
--%>
      <li class="nav-item active">
        <a class="nav-link" href="${url_admin}">
          <i class="fas fa-fw fa-table"></i>
          <span>Administratorzy</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

          <!-- Topbar Search -->
          <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
            <div class="input-group">
              <input type="text" class="form-control bg-light border-0 small" placeholder="Szukaj ..." aria-label="Search" aria-describedby="basic-addon2">
              <div class="input-group-append">
                <button class="btn btn-primary" type="button">
                  <i class="fas fa-fw fa-search"></i>
                </button>
              </div>
            </div>
          </form>

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

            <!-- Nav Item - Search Dropdown (Visible Only XS) -->
            <li class="nav-item dropdown no-arrow d-sm-none">
              <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-search fa-fw"></i>
              </a>

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')">
              <li class="nav-item dropdown no-arrow">
                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <span class="mr-2 d-none d-lg-inline text-gray-600 small">
                    Witaj ${currentUser.currentUserDTO.firstName} ${currentUser.currentUserDTO.lastName}!
                  </span>
                  <sec:authorize access="hasRole('ROLE_SUPERADMIN')">
                    <img class="img-profile rounded-circle" src="/resources/bootstrap/images/crown.svg">
                  </sec:authorize>
                  <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <img class="img-profile rounded-circle" src="/resources/bootstrap/images/shield-alt.svg">
                  </sec:authorize>
                  <sec:authorize access="hasRole('ROLE_USER')">
                    <img class="img-profile rounded-circle" src="/resources/bootstrap/images/user.svg">
                  </sec:authorize>
                </a>
                <!-- Dropdown - User Information -->
                <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                  <a class="dropdown-item" href="#">
                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                    Profil
                  </a>
                  <a class="dropdown-item" href="#">
                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                    Zmiana podstawowych danych
                  </a>
                  <a class="dropdown-item" href="#">
                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                    Zmiana email i hasła
                  </a>
                  <div class="dropdown-divider"></div>
                  <a class="dropdown-item" href="/logout" data-toggle="modal" data-target="#logoutModal">
                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                    Logout
                  </a>
                </div>
              </li>
            </sec:authorize>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Administratorzy</h1>
          <p class="mb-4">Wybierz konto administratora dla edycji danych</p>

          <!-- DataTale -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <sec:authorize access="!hasRole('ROLE_SUPERADMIN')">
                <div style="display: inline">
                  <h6 class="m-0 font-weight-bold text-primary">Tabela administratorów aktywnych</h6>
                  <p class="btn btn-light">Dodaj nowego administratora</p>
                </div>
              </sec:authorize>
              <sec:authorize access="hasRole('ROLE_SUPERADMIN')">
                <div style="display: inline">
                  <h6 class="m-0 font-weight-bold text-primary">Tabela administratorów (również nieaktywnych)</h6>
                  <c:url value="/admin/admin/add" var="addURL"></c:url>
                  <a href="${addURL}"><button type="button" class="btn btn-primary">Dodaj nowego administratora</button></a>
                </div>
              </sec:authorize>
            </div>

            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th scope="col">Lp.</th>
                      <th scope="col">ID</th>
                      <th scope="col">imię</th>
                      <th scope="col">nazwisko</th>
                      <th scope="col">email</th>
                      <th scope="col">aktywny</th>
                      <th scope="col">role</th>
                      <th scope="col">ulica</th>
                      <th scope="col">miasto</th>
                      <th scope="col">kod</th>
                      <th scope="col">telefon</th>
                      <th scope="col">edycja</th>
                      <th scope="col">usuń</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th scope="col">Lp.</th>
                      <th scope="col">ID</th>
                      <th scope="col">imię</th>
                      <th scope="col">nazwisko</th>
                      <th scope="col">email</th>
                      <th scope="col">aktywny</th>
                      <th scope="col">role</th>
                      <th scope="col">ulica</th>
                      <th scope="col">miasto</th>
                      <th scope="col">kod</th>
                      <th scope="col">telefon</th>
                      <th scope="col">edycja</th>
                      <th scope="col">usuń</th>
                    </tr>
                  </tfoot>
                  <tbody>
                  <c:forEach items="${userDTOList}" var="user" varStatus="stat">
                    <tr>
                      <td>${stat.count}</td>
                      <td>${user.id}</td>
                      <td>${user.firstName}</td>
                      <td>${user.lastName}</td>
                      <td>${user.email}</td>
                      <td>
                        <c:if test="${user.active}">tak</c:if>
                        <c:if test="${!user.active}">NIE</c:if>
                      </td>
                      <td>
                        <c:forEach items="${user.roleDTOList}" var="role">
                          ${role.name}<br>
                        </c:forEach>
                      </td>
                      <td>${user.userInfoDTO.street}</td>
                      <td>${user.userInfoDTO.city}</td>
                      <td>${user.userInfoDTO.zipCode}</td>
                      <td>${user.userInfoDTO.phone}</td>
                      <td>
                        <c:url value="/admin/admin/update" var="updateURL">
                          <c:param name="id" value="${user.id}"/>
                          <c:param name="em" value="${user.email}"/>
                        </c:url>
                        <a href="${updateURL}"><button type="button" class="btn btn-primary">Edytuj</button></a>
                      </td>
                      <td>
                        <sec:authorize access="hasRole('ROLE_SUPERADMIN')">
                          <c:url value="/admin/admin/delete" var="deleteURL">
                            <c:param name="id" value="${user.id}"/>
                            <c:param name="em" value="${user.email}"/>
                          </c:url>
                          <a href="${deleteURL}"><button type="button" class="btn btn-danger">Usuń</button></a>
                        </sec:authorize>
                        <sec:authorize access="!hasRole('ROLE_SUPERADMIN')">
                          <em style="font-size: 15px; color: gray" >Usuń</em>
                        </sec:authorize>
                      </td>
                    </tr>
                  </c:forEach>

                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Marcin Berger Website 2020</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-fw fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Gotowy do wylogowania?
<%--          Ready to Leave?--%>
          </h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Naciśnij "Logout", jeżeli jesteś gotowy do opuszczenia Twojej sesji
<%--        Select "Logout" below if you are ready to end your current session.</div>--%>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Anuluj
<%--          Cancel--%>
          </button>
          <form method="post" action="/logout">
            <set:csrfInput/>
<%--            <a class="btn btn-primary" href="/logout">Logout</a>--%>
            <a href="/logout"><button type="submit" class="btn btn-primary">Logout</button></a>
          </form>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="/resources/bootstrap/js/jquery.js"></script>
  <script src="/resources/bootstrap/js/bootstrap.bundle.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="/resources/bootstrap/js/jquery.easing.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="/resources/bootstrap/js/sb-admin-2.js"></script>

  <!-- Page level plugins -->
  <script src="/resources/bootstrap/js/jquery.dataTables.js"></script>
  <script src="/resources/bootstrap/js/dataTables.bootstrap4.js"></script>

  <!-- Page level custom scripts -->
  <script src="/resources/bootstrap/js/datatables-demo.js"></script>

</body>

</html>
