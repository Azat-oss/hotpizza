<#import "/layout.ftl" as l>
<@l.layout "Корзина">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="mb-0"><i class="bi bi-cart3"></i> Корзина</h1>
    <span class="text-muted">${items?size} позиций</span>
</div>

<#if items?size == 0>
<div class="alert alert-info text-center">
    <i class="bi bi-bag-x"></i> Корзина пуста.
    <a href="/products" class="alert-link">Перейти к покупкам</a>
</div>
<#else>
<div class="card shadow-sm">
    <div class="card-body p-0">
        <table class="table table-hover mb-0 align-middle">
            <thead class="table-light">
                <tr>
                    <th>Товар</th>
                    <th class="text-center">Цена</th>
                    <th class="text-center">Кол-во</th>
                    <th class="text-end">Сумма</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <#list items as item>
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <img src="${(item.product().imageUrl())!''}" alt="${(item.product().name())!''}" style="width:60px;height:60px;object-fit:cover;" class="rounded me-3">
                            <div>
                                <a href="/products/${(item.product().id())!''}" class="text-decoration-none fw-semibold">${(item.product().name())!''}</a>
                                <div class="small text-muted">
                                    <#if item.product().category() == 'PIZZA'>Пицца
                                    <#elseif item.product().category() == 'SUSHI'>Суши
                                    <#elseif item.product().category() == 'DRINKS'>Напиток
                                    <#elseif item.product().category() == 'DESSERTS'>Десерт
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </td>
                    <td class="text-center">${(item.product().price())!''} ₽</td>
                    <td class="text-center" style="min-width: 140px;">
                        <form method="post" action="/cart/update/${item.cartItemId()}" class="d-flex justify-content-center gap-1">
                            <@l.csrfInput/>
                            <input type="number" name="quantity" value="${item.quantity()!1}" min="1" max="99" class="form-control form-control-sm" style="width:70px;">
                            <button type="submit" class="btn btn-sm btn-outline-secondary" title="Обновить"><i class="bi bi-arrow-clockwise"></i></button>
                        </form>
                    </td>
                    <td class="text-end fw-bold">${(item.lineTotal())!''} ₽</td>
                    <td class="text-end">
                        <form method="post" action="/cart/remove/${item.cartItemId()}">
                            <@l.csrfInput/>
                            <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Удалить товар?')"><i class="bi bi-trash"></i></button>
                        </form>
                    </td>
                </tr>
                </#list>
            </tbody>
            <tfoot class="table-light">
                <tr>
                    <th colspan="3" class="text-end">Итого:</th>
                    <th class="text-end price-tag" style="font-size: 1.3rem;">${total!''} ₽</th>
                    <th></th>
                </tr>
            </tfoot>
        </table>
    </div>
</div>

<div class="d-flex justify-content-between mt-3">
    <a href="/products" class="btn btn-outline-secondary"><i class="bi bi-arrow-left"></i> Продолжить покупки</a>
    <a href="/orders/checkout" class="btn btn-primary"><i class="bi bi-bag-check"></i> Оформить заказ</a>
</div>
</#if>
</@l.layout>