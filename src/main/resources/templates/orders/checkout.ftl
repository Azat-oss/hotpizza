<#import "/layout.ftl" as l>
<@l.layout "Оформление заказа">
<h1 class="mb-4"><i class="bi bi-bag-check"></i> Оформление заказа</h1>

<div class="row">
    <div class="col-md-7">
        <div class="card shadow-sm mb-3">
            <div class="card-header"><b>Доставка</b></div>
            <div class="card-body">
                <form method="post" action="/orders/checkout" novalidate>
                    <@l.csrfInput/>
                    <div class="mb-3">
                        <label class="form-label">Адрес доставки <span class="text-danger">*</span></label>
                        <textarea name="shippingAddress" class="form-control" rows="3" required>${(checkoutForm.shippingAddress())!''}</textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Контактный телефон <span class="text-danger">*</span></label>
                        <input type="text" name="phone" class="form-control" required value="${(checkoutForm.phone())!''}">
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Подтвердить заказ</button>
                    <a href="/cart" class="btn btn-outline-secondary">Назад в корзину</a>
                </form>
            </div>
        </div>
        <div class="alert alert-info">
            <i class="bi bi-info-circle"></i> После оформления заказ получит статус <b>«Ожидает оплаты»</b>. Вы сможете оплатить его на странице истории заказов.
        </div>
    </div>

    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-header"><b>Ваш заказ</b></div>
            <div class="card-body p-0">
                <table class="table mb-0">
                    <tbody>
                        <#list items as item>
                        <tr>
                            <td>${(item.product().name())!''} <span class="text-muted">× ${item.quantity()!1}</span></td>
                            <td class="text-end">${(item.lineTotal())!''} ₽</td>
                        </tr>
                        </#list>
                    </tbody>
                    <tfoot class="table-light">
                        <tr>
                            <th>Итого:</th>
                            <th class="text-end price-tag" style="font-size:1.2rem;">${total!''} ₽</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
</@l.layout>