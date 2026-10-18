<#import "/layout.ftl" as l>
<@l.layout "Профиль">
<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card shadow-sm mb-3">
            <div class="card-body p-4">
                <div class="d-flex align-items-center mb-3">
                    <div class="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3"
                         style="width:64px;height:64px;font-size:1.8rem;">
                        <i class="bi bi-person"></i>
                    </div>
                    <div>
                        <h2 class="mb-0">${(user.name())!''}</h2>
                        <span class="text-muted">${(user.email())!''}</span>
                        <#if user.role() == 'ADMIN'>
                            <span class="badge bg-warning text-dark ms-2">Администратор</span>
                        <#else>
                            <span class="badge bg-info">Пользователь</span>
                        </#if>
                    </div>
                </div>

                <table class="table">
                    <tbody>
                    <tr><th style="width:200px;">Имя</th><td>${(user.name())!''}</td></tr>
                    <tr><th>Email</th><td>${(user.email())!''}</td></tr>
                    <tr><th>Телефон</th><td>${(user.phone())!'—'}</td></tr>
                    <tr><th>Адрес доставки</th><td>${(user.address())!'—'}</td></tr>
                    <tr><th>Баланс</th><td><span class="price-tag" style="font-size:1.4rem;">${(user.balance())!'0'} ₽</span></td></tr>
                    <tr><th>Чеки на почту</th><td>${(user.receiveReceipts())?then('Да', 'Нет')}</td></tr>
                    <tr><th>Дата регистрации</th><td>${(user.createdAtFormatted)!''}</td></tr>
                    </tbody>
                </table>

                <div class="d-flex gap-2 flex-wrap">
                    <a href="/profile/edit" class="btn btn-primary"><i class="bi bi-pencil"></i> Редактировать</a>
                    <a href="/profile/balance" class="btn btn-success"><i class="bi bi-plus-circle"></i> Пополнить баланс</a>
                    <a href="/orders" class="btn btn-outline-secondary">Мои заказы</a>
                    <a href="/cart" class="btn btn-outline-secondary">Корзина</a>
                </div>
            </div>
        </div>
    </div>
</div>
</@l.layout>