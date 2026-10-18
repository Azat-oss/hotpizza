<#macro layout title>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title!"FoodShop"}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { padding-top: 70px; background-color: #f8f9fa; min-height: 100vh; display: flex; flex-direction: column; }
        .container-main { flex: 1; }
        .product-card { height: 100%; transition: transform .2s; }
        .product-card:hover { transform: translateY(-4px); }
        .product-card img { height: 200px; object-fit: cover; }
        .product-card .card-text { font-size: .85rem; color: #6c757d; }
        .navbar-brand i { color: #ffc107; }
        footer { background-color: #212529; color: #adb5bd; padding: 20px 0; margin-top: 40px; }
        .badge-category { font-size: .7rem; }
        .price-tag { color: #198754; font-weight: 700; font-size: 1.1rem; }
        .order-status-pending { color: #fd7e14; }
        .order-status-paid { color: #198754; font-weight: 700; }
        .order-status-delivered { color: #0d6efd; }
        .order-status-cancelled { color: #dc3545; text-decoration: line-through; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/"><i class="bi bi-shop"></i> FoodShop</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="nav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link" href="/">Главная</a></li>
                    <li class="nav-item"><a class="nav-link" href="/products">Каталог</a></li>
                    <#if currentUser??>
                        <li class="nav-item">
                            <a class="nav-link" href="/cart">
                                Корзина
                                <#if cartCount?? && cartCount gt 0>
                                    <span class="badge bg-danger">${cartCount}</span>
                                </#if>
                            </a>
                        </li>
                        <li class="nav-item"><a class="nav-link" href="/orders">Заказы</a></li>
                        <li class="nav-item"><a class="nav-link" href="/profile">Профиль</a></li>
                       <#if currentUser?? && currentUser.role()?string == "ADMIN">
                            <li class="nav-item"><a class="nav-link text-warning" href="/admin"><i class="bi bi-shield-lock"></i> Админка</a></li>
                        </#if>
                    </#if>
                </ul>
                <ul class="navbar-nav">
                    <#if currentUser??>
                        <li class="nav-item"><span class="nav-link text-light"><i class="bi bi-person-circle"></i> ${currentUser.name()!''}</span></li>
                        <li class="nav-item">
                            <form method="post" action="/logout" class="d-inline">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-outline-light btn-sm">Выйти</button>
                            </form>
                        </li>
                    <#else>
                        <li class="nav-item"><a class="nav-link" href="/login">Вход</a></li>
                        <li class="nav-item"><a class="btn btn-warning btn-sm mt-1" href="/register">Регистрация</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container container-main mt-4">
        <#if success??>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle"></i> ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </#if>
        <#if error??>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </#if>
        <#nested>
    </div>

    <footer class="text-center">
        <div class="container">
            <p class="mb-0">&copy; 2026 FoodShop — учебный проект на Spring Boot + Oracle</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</#macro>

<#macro csrfInput>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</#macro>