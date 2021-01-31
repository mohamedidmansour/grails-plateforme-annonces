package com.mbds.projet

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.annotation.Secured

class ApiController {

    def index() {}
    // CRUD USER
    // Methods: GET, PUT, DELETE, PATCH
    def user() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def userInstance = User.get(params.id)
                if (!userInstance)
                    return response.status = 404
                withFormat {
                    json { render userInstance as JSON }
                    xml { render userInstance as XML }
                }
                break

            case "PUT":
                if (!params.id)
                    return response.status = 400
                def oldUser = User.get(params.id);
                oldUser.properties = request.JSON;

                if (oldUser.save(flush: true)) {
                    response.status = 200 // OK
                    render oldUser as JSON
                } else {
                    response.status = 500 //Internal Server Error
                    render "Could not create new User due to errors:\n ${oldUser.errors}"
                }
                break
            case "PATCH":
                break
            case "DELETE":
                if (!params.id)
                    return response.status = 400
                else {
                    UserRole.removeAll(User.get(params.id))
                    def userInstance = User.get(params.id)
                    userInstance.delete(flush: true)
                    return response.status = 200
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }
    // Methods: GET All, POST
    def users() {
        switch (request.getMethod()) {
            case "POST":
                def user = new User(request.JSON)
                if (user.save()) {
                    def adminRole = new Role(authority: "ROLE_ADMIN").save()
                    UserRole.create(user, adminRole, true)
                    response.status = 201 // Created
                    render user as JSON
                } else {
                    response.status = 500
                    // Internal Server Error
                    render "Could not create new User due to errors:\n ${user.errors} \n ${params.user}"
                }
                break
            case "GET":
                def user = new User(request.JSON)
                def userInstance = User.findAll()
                if (!userInstance)
                    return response.status = 404
                withFormat {
                    json { render userInstance as JSON }
                    xml { render userInstance as XML }
                }
                break

        }
    }
    // Get User by Username
    def getUserByUserName(){
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def userInstance = User.findByUsername(params.id)
                if (!userInstance)
                    return response.status = 404
                withFormat {
                    json { render userInstance as JSON }
                    xml { render userInstance as XML }
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    // CRUD Roles

    // Add Role To User
    def addRoleToUser() {
        switch (request.getMethod()) {
            case "POST":
                if (!params.id)
                    return response.status = 400
                def user = User.get(params.id)
                if (user) {
                    def role = new Role(request.JSON)
                    UserRole.create(user, Role.findByAuthority(role.getAuthority()), true)
                    println(role)
                    println(user)
                    response.status = 201 // Created
                } else {
                    response.status = 500
                    // Internal Server Error
                    render "Could not create new User due to errors:\n ${user.errors} \n ${params.user}"
                }
                break

        }
    }
    // delete Role to User
    def deleteRoleToUser() {
        switch (request.getMethod()) {
            case "POST":
                if (!params.id)
                    return response.status = 400
                def user = User.get(params.id)
                if (user) {
                    def role = new Role(request.JSON)
                    UserRole.remove(user, Role.findByAuthority(role.getAuthority()))
                    response.status = 201 // Deleted
                } else {
                    response.status = 500
                    // Internal Server Error
                    render "Could not Delete RoleUser due to errors:\n ${user.errors} \n ${params.user}"
                }
                break

        }
    }

    // Get All Role By Id
    def rolesById() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def roles = Role.findById(params.id)

                if (!roles)
                    return response.status = 404
                withFormat {
                    json { render roles as JSON }
                    xml { render roles as XML }
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }
    //  Get All Role By User
    def rolesByUser() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def roles = UserRole.findAllByUser(User.findById(params.id)).role

                if (!roles)
                    return response.status = 404
                withFormat {
                    json { render roles as JSON }
                    xml { render roles as XML }
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }
    // Get Roles Unused by User
    def roleUnusedByUser() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def roles = UserRole.findAllByUser(User.findById(params.id)).role
                print(roles)
                List<Role> listRole = new ArrayList<>()
                for (Role r : Role.findAll()) {
                    if (!roles.contains(r)) listRole.add(r)
                }
                if (!listRole)
                    return response.status = 404
                withFormat {
                    json { render listRole as JSON }
                    xml { render listRole as XML }
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    // CRUD SaleAd
    // Chart.js
    def getCountSaleAdByUser() {
        switch (request.getMethod()) {
            case "GET":
                def res = User.executeQuery(
                        "SELECT u.username, count(*) as ct FROM User u join u.saleAds s group by u.username"
                )
                if (!res)
                    return response.status = 404
                withFormat {
                    json { render res as JSON }
                    xml { render res as XML }
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    // Get SaleAd By User
    def saleAdByUser() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def saleAd = User.findById(params.id).saleAds.findAll()
                if (!saleAd)
                    return response.status = 404
                withFormat {
                    json { render saleAd as JSON }
                    xml { render saleAd as XML }
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }
    // Methods: GET, PUT, DELETE, PATCH
    def saleAd() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def saleAdInstance = SaleAd.get(params.id)
                if (!saleAdInstance)
                    return response.status = 404
                withFormat {
                    json { render saleAdInstance as JSON }
                    xml { render saleAdInstance as XML }
                }
                break
            case "PUT":
                if (!params.id)
                    return response.status = 400
                def oldSaleAd = SaleAd.get(params.id);
                oldSaleAd.properties = request.JSON;

                if (oldSaleAd.save(flush: true)) {
                    response.status = 200 // OK
                    render oldSaleAd as JSON
                } else {
                    response.status = 500 //Internal Server Error
                    render "Could not Update new SaleAd due to errors:\n ${oldSaleAd.errors}"
                }
                break
            case "DELETE":
                if (!params.id)
                    return response.status = 400
                else {
                    def saleInstance = SaleAd.get(params.id)
                    saleInstance.delete(flush: true)
                    return response.status = 200
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }
    // Methods: GET All, POST, DELETE, PATCH
    def saleAds() {
        switch (request.getMethod()) {
            case "POST":
                def saleAdInstance = new SaleAd(request.JSON)
                if (saleAdInstance.save()) {
                    response.status = 201 // Created
                    render saleAdInstance as JSON
                } else {
                    response.status = 500
                    // Internal Server Error
                    render "Could not create new SaleAd due to errors:\n ${saleAdInstance.errors} \n ${params.user}"
                }
                break
        }
    }


    // CRUD Illustration

    // Methods: GET, PUT, DELETE, PATCH
    def illustrations() {
        switch (request.getMethod()) {
            case "POST":
                //("print")
                def illustrationInstance = new Illustration(request.JSON)
                if (illustrationInstance.save()) {
                    response.status = 201 // Created
                    render illustrationInstance as JSON
                } else {
                    response.status = 500
                    // Internal Server Error
                    render "Could not create new Illustration due to errors:\n ${illustrationInstance.errors} \n ${params.user}"
                }
                break
            case "GET":
                def illustrationInstance = Illustration.findAll()
                if (!illustrationInstance)
                    return response.status = 404
                withFormat {
                    json { render illustrationInstance as JSON }
                    xml { render illustrationInstance as XML }
                }
                break
            case "DELETE":
                if (!params.id)
                    return response.status = 400
                else {
                    def illustrationInstance = Illustration.get(params.id)
                    illustrationInstance.delete(flush: true)
                    return response.status = 200
                }
                break
            default:
                return response.status = 405
                break
        }
    }
    // Get illustration by SaleAd
    def illustrationBySaleAd() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def illustrationInstance = SaleAd.findById(params.id).illustrations
                if (!illustrationInstance)
                    return response.status = 404
                withFormat {
                    json { render illustrationInstance as JSON }
                    xml { render illustrationInstance as XML }
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

}
