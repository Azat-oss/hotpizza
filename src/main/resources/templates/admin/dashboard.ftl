<#import "/layout.ftl" as l>
<@l.layout "Админ-панель">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="mb-0"><i class="bi bi-shield-lock"></i> Панель администратора</h1>
</div>

<div class="row row-cols-1 row-cols-md-3 g-3 mb-4">
    <div class="col">
        <a href="/admin/products" class="text-decoration-none">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <div style="font-size: 2.5rem;">🍕</div>
                    <h3>${productCount!'0'}</h3>
                    <p class="text-muted mb-0">Продуктов в каталоге</p>
                </div>
            </div>
        </a>
    </div>
    <div class="col">
        <a href="/admin/users" class="text-decoration-none">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <div style="font-size: 2.5rem;">👥</div>
                    <h3>${userCount!'0'}</h3>
                    <p class="text-muted mb-0">Зарегистрированных пользователей</p>
                </div>
            </div>
        </a>
    </div>
    <div class="col">
        <a href="/products" class="text-decoration-none">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <div style="font-size: 2.5rem;">🛍️</div>
                    <h3>Каталог</h3>
                    <p class="text-muted mb-0">Открыть витрину</p>
                </div>
            </div>
        </a>
    </div>
</div>

<div class="row">
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-header bg-warning bg-opacity-25"><b>Управление каталогом</b></div>
            <div class="card-body">
                <p class="text-muted">Добавляйте, редактируйте и удаляйте продукты из каталога.</p>
                <a href="/admin/products" class="btn btn-outline-primary"><i class="bi bi-list"></i> Все продукты</a>
                <a href="/admin/products/new" class="btn btn-success"><i class="bi bi-plus-lg"></i> Добавить продукт</a>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-header bg-danger bg-opacity-25"><b>Управление пользователями</b></div>
            <div class="card-body">
                <p class="text-muted">Просматривайте и удаляйте пользователей.</p>
                <a href="/admin/users" class="btn btn-outline-primary"><i class="bi bi-people"></i> Все пользователи</a>
            </div>
        </div>
    </div>
</div>
</@l.layout>