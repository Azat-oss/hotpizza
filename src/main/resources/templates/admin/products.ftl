<#import "/layout.ftl" as l>
<@l.layout "Управление продуктами">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="mb-0"><i class="bi bi-box-seam"></i> Управление продуктами</h1>
    <a href="/admin/products/new" class="btn btn-success"><i class="bi bi-plus-lg"></i> Добавить продукт</a>
</div>

<div class="card shadow-sm">
    <div class="card-body p-0">
        <table class="table table-hover mb-0 align-middle">
            <thead class="table-light">
                <tr>
                    <th style="width:80px;">Фото</th>
                    <th>Название</th>
                    <th>Категория</th>
                    <th class="text-end">Цена</th>
                    <th class="text-center">Статус</th>
                    <th class="text-end">Действия</th>
                </tr>
            </thead>
            <tbody>
                <#list products as p>
                <tr>
                    <td><img src="${(p.imageUrl())!''}" alt="" style="width:60px;height:60px;object-fit:cover;" class="rounded"></td>
                    <td>
                        <b>${(p.name())!''}</b>
                        <div class="small text-muted">${(p.description()!'')?truncate(50, '...')}</div>
                    </td>
                    <td>
                        <span class="badge bg-secondary">
                            <#if p.category() == 'PIZZA'>Пицца
                            <#elseif p.category() == 'SUSHI'>Суши
                            <#elseif p.category() == 'DRINKS'>Напитки
                            <#elseif p.category() == 'DESSERTS'>Десерты
                            </#if>
                        </span>
                    </td>
                    <td class="text-end price-tag">${(p.price())!''} ₽</td>
                    <td class="text-center">
                        <#if p.available()?? && p.available()>
                            <span class="badge bg-success">Активен</span>
                        <#else>
                            <span class="badge bg-secondary">Скрыт</span>
                        </#if>
                    </td>
                    <td class="text-end">
                        <a href="/products/${(p.id())!''}" class="btn btn-sm btn-outline-secondary"><i class="bi bi-eye"></i></a>
                        <a href="/admin/products/${(p.id())!''}/edit" class="btn btn-sm btn-outline-primary"><i class="bi bi-pencil"></i></a>
                        <form method="post" action="/admin/products/${(p.id())!''}/delete" class="d-inline">
                            <@l.csrfInput/>
                            <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Удалить продукт?')"><i class="bi bi-trash"></i></button>
                        </form>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</@l.layout>