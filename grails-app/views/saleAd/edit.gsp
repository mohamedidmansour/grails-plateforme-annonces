<%@ page import="com.mbds.projet.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<a href="#edit-saleAd" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="edit-saleAd" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.saleAd}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.saleAd}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:uploadForm resource="${this.saleAd}" method="PUT">
        <g:hiddenField name="version" value="${this.saleAd?.version}"/>

        <fieldset class="form">
            <div class="fieldcontain required">
                <label for="title">Title
                    <span class="required-indicator">*</span>
                </label>
                <input type="text" name="title" value="${saleAd.title}" required="" maxlength="50" id="title">
            </div>

            <div class="fieldcontain required">
                <label for="description">Description
                    <span class="required-indicator">*</span>
                </label>
                <input type="text" name="description" value="${saleAd.description}" required="" id="description">
            </div>

            <div class="fieldcontain required">
                <label for="longDescription">Description longue
                    <span class="required-indicator">*</span>
                </label>
                <input type="text" name="longDescription" value="${saleAd.longDescription}" required=""
                       id="longDescription">
            </div>

            <div class="fieldcontain">
                <label for="status">Status</label>
                <input type="hidden" name="_status">
                <g:if test="${saleAd.status}">
                    <input type="checkbox" name="status" checked="checked" id="status">
                </g:if>
                <g:else>
                    <input type="checkbox" name="status" id="status">
                </g:else>
            </div>

            <div class="fieldcontain required">
                <label for="price">Price
                    <span class="required-indicator">*</span>
                </label>
                <input type="number decimal" name="price" value="${saleAd.price}" required="" step="0.01" min="0.0"
                       id="price">
            </div>

            <div class="fieldcontain">
                <label for="illustrations">Illustrations</label>
                <g:each in="${saleAd.illustrations}" var="illustration">
                    <asset:image src="${illustration.filename}"/>
                </g:each>
            </div>

            <div class="fieldcontain">
                <label for="file">Add new Illustration</label>
                <input style="display: inline;" type="file" name="file" >
            </div>

            <div class="fieldcontain required">
                <label for="author">Author
                    <span class="required-indicator">*</span>
                </label>
                <g:select name="author.id" from="${User.list()}" optionKey="id" optionValue="username" />
            </div>
        </fieldset>
        <fieldset class="buttons">
            <input class="save" type="submit"
                   value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
    </g:uploadForm>
</div>
</body>
</html>
