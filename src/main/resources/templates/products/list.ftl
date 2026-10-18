<#import "/layout.ftl" as l>
<@l.layout "Каталог продуктов">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="mb-0"><i class="bi bi-grid-3x3-gap"></i> Каталог</h1>
    <span class="text-muted">${products?size} товаров</span>
</div>

<form method="get" action="/products" class="card p-3 mb-4 shadow-sm">
    <div class="row g-2 align-items-end">
        <div class="col-md-5">
            <label class="form-label small">Поиск по названию</label>
            <input type="text" name="search" class="form-control" value="${search!''}" placeholder="Например, пепперони">
        </div>
        <div class="col-md-4">
            <label class="form-label small">Категория</label>
            <select name="category" class="form-select">
                <option value="">Все категории</option>
                <#list categories![] as c>
                    <option value="${c}" <#if selectedCategory?? && selectedCategory == c>selected</#if>>
                        <#if c == 'PIZZA'>Пицца
                        <#elseif c == 'SUSHI'>Суши
                        <#elseif c == 'DRINKS'>Напитки
                        <#elseif c == 'DESSERTS'>Десерты
                        </#if>
                    </option>
                </#list>
            </select>
        </div>
        <div class="col-md-3 d-grid gap-2 d-md-flex">
            <button type="submit" class="btn btn-primary"><i class="bi bi-funnel"></i> Фильтр</button>
            <a href="/products" class="btn btn-outline-secondary">Сброс</a>
        </div>
    </div>
</form>

<#if products?size == 0>
<div class="alert alert-info">По заданным критериям ничего не найдено.</div>
<#else>
<div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-3">
    <#list products as p>
        <div class="col">
            <div class="card product-card shadow-sm h-100">
                <img src="${p.imageUrl()!''}" class="card-img-top" alt="${p.name()!}">
                <div class="card-body d-flex flex-column">
                    <div class="d-flex justify-content-between align-items-start mb-1">
                        <span class="badge bg-secondary badge-category">
                            <#if p.category() == 'PIZZA'>🍕 Пицца
                            <#elseif p.category() == 'SUSHI'>🍣 Суши
                            <#elseif p.category() == 'DRINKS'>🥤 Напиток
                            <#elseif p.category() == 'DESSERTS'>🍰 Десерт
                            </#if>
                        </span>
                        <#if p.available()?? && p.available()>
                            <span class="badge bg-success">В наличии</span>
                        <#else>
                            <span class="badge bg-secondary">Нет в наличии</span>
                        </#if>
                    </div>
                    <h6 class="card-title">${p.name()!}</h6>
                    <p class="card-text small flex-grow-1">${(p.description()!'')?truncate(80, '...')}</p>
                    <div class="d-flex justify-content-between align-items-center mt-2">
                        <span class="price-tag">${p.price()!} ₽</span>
                    </div>
                    <div class="d-flex gap-1 mt-2">
                        <a href="/products/${p.id()}" class="btn btn-sm btn-outline-primary flex-grow-1">Подробнее</a>
                        <#if currentUser?? && p.available()?? && p.available()>
                            <form method="post" action="/cart/add/${p.id()}" class="flex-grow-1">
                                <@l.csrfInput/>
                                <input type="hidden" name="quantity" value="1">
                                <button type="submit" class="btn btn-sm btn-success w-100">
                                    <i class="bi bi-cart-plus"></i> В корзину
                                </button>
                            </form>
                        <#elseif !currentUser??>
                            <a href="/login" class="btn btn-sm btn-outline-secondary flex-grow-1">Войдите</a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </#list>
</div>
</#if>
</@l.layout>