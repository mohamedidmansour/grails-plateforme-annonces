<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-saleAd" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-saleAd" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
%{--            <f:table collection="${saleAdList}" />--}%
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="title" title="Title" />
            <g:sortableColumn property="description" title="Description" />
            <g:sortableColumn property="status" title="Status" />
            <g:sortableColumn property="price" title="Price" />
            <th>Illustrations</th>
            <th>Author</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${saleAdList}" var="saleAd">
            <tr>
                <td>
                    <g:link controller="saleAd" action="show" id="${saleAd.id}">${saleAd.title}</g:link>
                </td>
                <td>${saleAd.description}</td>
                <td>${saleAd.status}</td>
                <td>${saleAd.price}</td>
                <td>
                    <g:each in="${saleAd.illustrations}" var="illustration">
                        <asset:image src="${illustration.filename}" />
                    </g:each>
                </td>
                <td>
                    <g:link controller="user" action="show" id="${saleAd.author.id}">${saleAd.author.username}</g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${saleAdCount ?: 0}"/>
    </div>
</div>
</body>
</html>