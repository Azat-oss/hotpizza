<#import "layout.ftl" as l>
<@l.layout "Вход">
<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h2 class="card-title text-center mb-4"><i class="bi bi-box-arrow-in-right"></i> Вход в систему</h2>

                <#if RequestParameters.error??>
                    <div class="alert alert-danger">Неверный email или пароль</div>
                </#if>
                <#if RequestParameters.logout??>
                    <div class="alert alert-success">Вы вышли из системы</div>
                </#if>
                <#if success??>
                    <div class="alert alert-success">${success}</div>
                </#if>

                <form method="post" action="/login">
                    <@l.csrfInput/>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="username" class="form-control" required autofocus value="user@shop.ru">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Пароль</label>
                        <input type="password" name="password" class="form-control" required value="user123">
                    </div>
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Войти</button>
                    </div>
                </form>

                <hr>
                <div class="text-center">
                    <small class="text-muted">Нет аккаунта? <a href="/register">Зарегистрируйтесь</a></small>
                </div>
                <hr>
                <div class="bg-light p-3 rounded">
                    <h6 class="text-muted">Демо-доступы:</h6>
                    <ul class="small mb-0">
                        <li><b>USER:</b> user@shop.ru / user123</li>
                        <li><b>ADMIN:</b> admin@shop.ru / admin123</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</@l.layout>