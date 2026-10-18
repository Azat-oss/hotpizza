<#import "layout.ftl" as l>
<@l.layout "Регистрация">
<div class="row justify-content-center">
    <div class="col-md-7">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h2 class="card-title text-center mb-4"><i class="bi bi-person-plus"></i> Регистрация</h2>

                <form method="post" action="/register" novalidate>
                    <@l.csrfInput/>

                    <div class="mb-3">
                        <label class="form-label">Имя <span class="text-danger">*</span></label>
                        <input type="text" name="name" class="form-control" required minlength="2" maxlength="50"
                               value="${(userForm.name())!''}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email <span class="text-danger">*</span></label>
                        <input type="email" name="email" class="form-control" required
                               value="${(userForm.email())!''}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Пароль <span class="text-danger">*</span></label>
                        <input type="password" name="password" class="form-control" required minlength="6">
                        <div class="form-text">Минимум 6 символов</div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Телефон <span class="text-danger">*</span></label>
                        <input type="text" name="phone" class="form-control" required
                               placeholder="+7-999-123-45-67" value="${(userForm.phone())!''}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Адрес доставки</label>
                        <textarea name="address" class="form-control" rows="2">${(userForm.address())!''}</textarea>
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-success"><i class="bi bi-check-lg"></i> Зарегистрироваться</button>
                        <a href="/login" class="btn btn-outline-secondary">Уже есть аккаунт? Войти</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@l.layout>