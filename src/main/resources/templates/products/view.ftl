<#import "/layout.ftl" as l>
<@l.layout "${(product.name())!''}">
<div class="row">
    <div class="col-md-5">
        <img src="${(product.imageUrl())!''}" class="img-fluid rounded shadow-sm" alt="${(product.name())!''}">
    </div>
    <div class="col-md-7">
        <span class="badge bg-secondary mb-2">
            <#if product.category() == 'PIZZA'>🍕 Пицца
            <#elseif product.category() == 'SUSHI'>🍣 Суши
            <#elseif product.category() == 'DRINKS'>🥤 Напиток
            <#elseif product.category() == 'DESSERTS'>🍰 Десерт
            </#if>
        </span>
        <h1>${(product.name())!''}</h1>
        <h3 class="price-tag mb-3">${(product.price())!''} ₽</h3>

        <#if product.available()?? && product.available()>
            <span class="badge bg-success mb-3">В наличии</span>
        <#else>
            <span class="badge bg-danger mb-3">Нет в наличии</span>
        </#if>

        <h5>Описание</h5>
        <p class="text-muted">${(product.description())!''}</p>

        <#if currentUser?? && product.available()?? && product.available()>
            <form method="post" action="/cart/add/${(product.id())!''}" class="row g-2 align-items-end mt-3">
                <@l.csrfInput/>
                <div class="col-auto">
                    <label class="form-label small">Количество</label>
                    <input type="number" name="quantity" value="1" min="1" max="99" class="form-control" style="width: 90px;">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-success"><i class="bi bi-cart-plus"></i> В корзину</button>
                </div>
            </form>
        <#elseif !currentUser??>
            <div class="alert alert-info mt-3">
                <i class="bi bi-info-circle"></i> Войдите, чтобы добавить товар в корзину.
                <a href="/login" class="btn btn-sm btn-primary ms-2">Войти</a>
            </div>
        <#else>
            <div class="alert alert-warning mt-3">Товар временно недоступен</div>
        </#if>

        <a href="/products" class="btn btn-outline-secondary mt-3"><i class="bi bi-arrow-left"></i> Назад к каталогу</a>
    </div>
</div>
</@l.layout>