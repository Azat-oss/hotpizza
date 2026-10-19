<#import "/layout.ftl" as l>
<@l.layout "Пополнение баланса">
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h2><i class="bi bi-credit-card"></i> Пополнение баланса</h2>
                <p class="text-muted small">Введите данные псевдо-карты и сумму пополнения.</p>

                <form method="post" action="/profile/balance" novalidate>
                    <@l.csrfInput/>

                    <div class="mb-3">
                        <label class="form-label">Номер карты <span class="text-danger">*</span></label>
                        <input type="text" name="cardNumber" class="form-control" required
                               pattern="[0-9]{13,19}" maxlength="19"
                               placeholder="0000 0000 0000 0000"
                               value="${(topUpForm.cardNumber())!''}">
                        <div class="form-text">13-19 цифр, без пробелов</div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Сумма пополнения, ₽ <span class="text-danger">*</span></label>
                        <input type="number" step="0.01" min="1" max="100000" name="amount" class="form-control" required
                               value="${(topUpForm.amount())!''}">
                    </div>

                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-send"></i> Получить код подтверждения
                    </button>
                    <a href="/profile" class="btn btn-outline-secondary w-100 mt-2">Отмена</a>
                </form>
            </div>
        </div>
    </div>
</div>
</@l.layout>