<#import "/layout.ftl" as l>
<@l.layout "Подтверждение пополнения">
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h2><i class="bi bi-shield-check"></i> Подтверждение</h2>
                <p class="text-muted">На вашу почту отправлен 6-значный код.
                <strong>Смотрите лог консоли IntelliJ</strong> — письмо выводится туда.</p>

                <form method="post" action="/profile/balance/confirm" novalidate>
                    <@l.csrfInput/>

                    <div class="mb-3">
                        <label class="form-label">Код подтверждения <span class="text-danger">*</span></label>
                        <input type="text" name="code" class="form-control text-center" required
                               pattern="[0-9]{6}" maxlength="6"
                               placeholder="000000"
                               style="font-size: 1.5rem; letter-spacing: 0.5rem;">
                    </div>

                    <button type="submit" class="btn btn-success w-100">
                        <i class="bi bi-check-lg"></i> Подтвердить
                    </button>
                    <a href="/profile/balance" class="btn btn-outline-secondary w-100 mt-2">Начать заново</a>
                </form>
            </div>
        </div>
    </div>
</div>
</@l.layout>