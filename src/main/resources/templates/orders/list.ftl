<#import "/layout.ftl" as l>
<@l.layout "Мои заказы">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="mb-0"><i class="bi bi-receipt"></i> История заказов</h1>
    <a href="/products" class="btn btn-outline-primary">Новый заказ</a>
</div>

<#if orders?size == 0>
<div class="alert alert-info text-center">
    У вас ещё нет заказов.
    <a href="/products" class="alert-link">Перейти к покупкам</a>
</div>
<#else>
<div class="row row-cols-1 g-3">
    <#list orders as order>
    <div class="col">
        <div class="card shadow-sm">
            <div class="card-header d-flex justify-content-between align-items-center">
                <div>
                    <b>Заказ #${(order.id())!''}</b>
                    <span class="text-muted ms-2">${(order.createdAtFormatted)!''}</span>
                </div>
                <div>
                    <#assign st = (order.status())!'PENDING'>
                    <#if st == 'PENDING'>
                        <span class="badge bg-warning order-status-pending">Ожидает оплаты</span>
                    <#elseif st == 'PAID'>
                        <span class="badge bg-success order-status-paid">Оплачен</span>
                    <#elseif st == 'DELIVERED'>
                        <span class="badge bg-primary order-status-delivered">Доставлен</span>
                    <#elseif st == 'CANCELLED'>
                        <span class="badge bg-danger order-status-cancelled">Отменён</span>
                    </#if>
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <table class="table table-sm mb-0">
                            <tbody>
                                <#list order.items() as item>
                                <tr>
                                    <td style="width:60px;">
                                        <#if item.imageUrl()?? && item.imageUrl() != ''>
                                        <img src="${item.imageUrl()}" style="width:50px;height:50px;object-fit:cover;" class="rounded">
                                        </#if>
                                    </td>
                                    <td>${(item.productName())!''}</td>
                                    <td class="text-center">×${(item.quantity())!1}</td>
                                    <td class="text-end">${(item.unitPrice())!''} ₽</td>
                                    <td class="text-end fw-semibold">${(item.lineTotal())!''} ₽</td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-4 border-start">
                        <p class="mb-1 small text-muted">Доставка:</p>
                        <p class="small">${(order.shippingAddress())!'—'}</p>
                        <p class="mb-1 small text-muted">Телефон:</p>
                        <p class="small">${(order.phone())!'—'}</p>
                        <h5 class="mt-2">Итого: <span class="price-tag">${(order.totalPrice())!''} ₽</span></h5>

                        <#if order.status() == 'PENDING'>
                            <form method="post" action="/orders/${order.id()}/pay">
                                <@l.csrfInput/>
                                <button type="submit" class="btn btn-success btn-sm w-100 mt-2">
                                    <i class="bi bi-credit-card"></i> Оплатить
                                </button>
                            </form>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </#list>
</div>
</#if>
</@l.layout>