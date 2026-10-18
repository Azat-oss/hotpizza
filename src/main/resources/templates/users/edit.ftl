<#import "/layout.ftl" as l>
<@l.layout "Редактирование профиля">
<div class="row justify-content-center">
    <div class="col-md-7">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h2 class="card-title mb-4"><i class="bi bi-pencil"></i> Редактирование профиля</h2>

                <form method="post" action="/profile">
                    <@l.csrfInput/>
                    <div class="mb-3">
                        <label class="form-label">Имя</label>
                        <input type="text" name="name" class="form-control" required value="${(userForm.name())!''}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control" required value="${(userForm.email())!''}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Новый пароль <small class="text-muted">(оставьте пустым, если не меняете)</small></label>
                        <input type="password" name="password" class="form-control" minlength="6">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Телефон</label>
                        <input type="text" name="phone" class="form-control" required value="${(userForm.phone())!''}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Адрес</label>
                        <textarea name="address" class="form-control" rows="2">${(userForm.address())!''}</textarea>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" name="receiveReceipts" class="form-check-input" id="receipts"
                               value="true" <#if (userForm.receiveReceipts())?? && userForm.receiveReceipts()>checked</#if>>
                        <label class="form-check-label" for="receipts">
                            Получать чеки на почту после оплаты заказов
                        </label>
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Сохранить</button>
                        <a href="/profile" class="btn btn-outline-secondary">Отмена</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@l.layout>