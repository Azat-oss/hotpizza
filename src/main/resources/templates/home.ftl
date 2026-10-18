<#import "layout.ftl" as l>
<@l.layout "FoodShop — доставка еды">
<div class="text-center mb-5">
    <h1 class="display-5 fw-bold">Закажи любимую еду <span class="text-warning">онлайн</span></h1>
    <p class="lead text-muted">Пицца, суши, напитки и десерты с доставкой за 30 минут</p>
    <a href="/products" class="btn btn-primary btn-lg mt-2"><i class="bi bi-arrow-right-circle"></i> Открыть каталог</a>
</div>

<#if currentUser?? && currentUser.role()?string == "ADMIN">
<div class="alert alert-warning">
    <i class="bi bi-shield-lock"></i> Вы вошли как администратор. Перейти в <a href="/admin">панель управления</a>.
</div>
</#if>

<div class="row mb-4">
    <#list categories![] as cat>
        <div class="col-md-3 mb-3">
            <a href="/products?category=${cat}" class="text-decoration-none">
                <div class="card text-center h-100 shadow-sm">
                    <div class="card-body">
                        <div style="font-size: 2.5rem;">
                            <#if cat == 'PIZZA'>🍕
                            <#elseif cat == 'SUSHI'>🍣
                            <#elseif cat == 'DRINKS'>🥤
                            <#elseif cat == 'DESSERTS'>🍰
                            </#if>
                        </div>
                        <h5 class="card-title mb-0">
                            <#if cat == 'PIZZA'>Пицца
                            <#elseif cat == 'SUSHI'>Суши и роллы
                            <#elseif cat == 'DRINKS'>Напитки
                            <#elseif cat == 'DESSERTS'>Десерты
                            </#if>
                        </h5>
                    </div>
                </div>
            </a>
        </div>
    </#list>
</div>

<h2 class="mb-3"><i class="bi bi-stars"></i> Популярное</h2>
<div class="row row-cols-1 row-cols-md-4 g-3">
    <#list featured![] as p>
        <div class="col">
            <div class="card product-card shadow-sm">
                <img src="${p.imageUrl()!''}" class="card-img-top" alt="${p.name()!}">
                <div class="card-body d-flex flex-column">
                    <span class="badge bg-secondary badge-category mb-1">
                        <#if p.category() == 'PIZZA'>Пицца
                        <#elseif p.category() == 'SUSHI'>Суши
                        <#elseif p.category() == 'DRINKS'>Напиток
                        <#elseif p.category() == 'DESSERTS'>Десерт
                        </#if>
                    </span>
                    <h6 class="card-title">${p.name()!}</h6>
                    <p class="card-text small flex-grow-1">${(p.description()!'')?truncate(80)}</p>
                    <div class="d-flex justify-content-between align-items-center mt-2">
                        <span class="price-tag">${p.price()!''} ₽</span>
                        <a href="/products/${p.id()}" class="btn btn-sm btn-outline-primary">Подробнее</a>
                    </div>
                </div>
            </div>
        </div>
    </#list>
</div>
</@l.layout>