<#import "/layout.ftl" as l>
<@l.layout "Пользователи">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="mb-0"><i class="bi bi-people"></i> Пользователи</h1>
    <span class="text-muted">${users?size} зарегистрировано</span>
</div>

<div class="card shadow-sm">
    <div class="card-body p-0">
        <table class="table table-hover mb-0 align-middle">
            <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Имя</th>
                    <th>Email</th>
                    <th>Телефон</th>
                    <th>Роль</th>
                    <th>Регистрация</th>
                    <th class="text-end">Действия</th>
                </tr>
            </thead>
            <tbody>
                <#list users as u>
                <tr>
                    <td>${(u.id())!''}</td>
                    <td>${(u.name())!''}</td>
                    <td>${(u.email())!''}</td>
                    <td>${(u.phone())!'—'}</td>
                    <td>
                        <#if u.role() == 'ADMIN'>
                            <span class="badge bg-warning text-dark">Админ</span>
                        <#else>
                            <span class="badge bg-info">Пользователь</span>
                        </#if>
                    </td>
                    <td> ${(u.createdAtFormatted)!''}</td>
                    <td class="text-end">
                        <#if u.role() != 'ADMIN'>
                            <form method="post" action="/admin/users/${(u.id())!''}/delete" class="d-inline">
                                <@l.csrfInput/>
                                <button type="submit" class="btn btn-sm btn-outline-danger"
                                        onclick="return confirm('Удалить пользователя ${(u.name())!''}?')">
                                    <i class="bi bi-trash"></i> Удалить
                                </button>
                            </form>
                        <#else>
                            <span class="text-muted small">нельзя удалить</span>
                        </#if>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</@l.layout>