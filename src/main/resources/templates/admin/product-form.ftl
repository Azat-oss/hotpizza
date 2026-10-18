<#import "/layout.ftl" as l>
<@l.layout "Форма продукта">
<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <h2 class="card-title mb-4">
                    <#if productId??>
                        <i class="bi bi-pencil"></i> Редактирование продукта
                    <#else>
                        <i class="bi bi-plus-lg"></i> Новый продукт
                    </#if>
                </h2>

                <form method="post" action="<#if productId??>/admin/products/${productId}<#else>/admin/products</#if>" novalidate>
                    <@l.csrfInput/>
                    <#if productId??><input type="hidden" name="id" value="${productId}"></#if>

                    <div class="mb-3">
                        <label class="form-label">Название <span class="text-danger">*</span></label>
                        <input type="text" name="name" class="form-control" required value="${(productForm.name())!''}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Описание <span class="text-danger">*</span></label>
                        <textarea name="description" class="form-control" rows="4" required>${(productForm.description())!''}</textarea>
                    </div>

                    <div class="row">
                        <div class="col-md-4">
                            <div class="mb-3">
                                <label class="form-label">Цена, ₽ <span class="text-danger">*</span></label>
                                <input type="number" step="0.01" min="0.01" name="price" class="form-control" required value="${(productForm.price())!''}">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="mb-3">
                                <label class="form-label">Категория <span class="text-danger">*</span></label>
                                <select name="category" class="form-select" required>
                                    <#list categories![] as c>
                                        <option value="${c}" <#if (productForm.category())?? && productForm.category() == c>selected</#if>>
                                            <#if c == 'PIZZA'>🍕 Пицца
                                            <#elseif c == 'SUSHI'>🍣 Суши
                                            <#elseif c == 'DRINKS'>🥤 Напитки
                                            <#elseif c == 'DESSERTS'>🍰 Десерты
                                            </#if>
                                        </option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="mb-3">
                                <label class="form-label">Доступность</label>
                                <select name="available" class="form-select">
                                    <option value="true" <#if (productForm.available())?? && productForm.available()>selected</#if>>Активен</option>
                                    <option value="false" <#if (productForm.available())?? && !productForm.available()>selected</#if>>Скрыт</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">URL изображения</label>
                        <input type="url" name="imageUrl" class="form-control" value="${(productForm.imageUrl())!''}" placeholder="https://...">
                        <div class="form-text">Можно использовать <code>https://picsum.photos/seed/название/400/300</code></div>
                    </div>

                    <#if (productForm.imageUrl())?? && productForm.imageUrl() != ''>
                    <div class="mb-3">
                        <label class="form-label small text-muted">Превью:</label><br>
                        <img src="${productForm.imageUrl()}" style="max-width:200px;max-height:150px;object-fit:cover;" class="rounded">
                    </div>
                    </#if>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Сохранить</button>
                        <a href="/admin/products" class="btn btn-outline-secondary">Отмена</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@l.layout>