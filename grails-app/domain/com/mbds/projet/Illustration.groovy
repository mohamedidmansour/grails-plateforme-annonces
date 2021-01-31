package com.mbds.projet

class Illustration {

    // basePath = /var/www/projet/images/
    // baseUrl = http://server.com/projet/images/
    String filename

    static belongsTo = [ad: SaleAd]

    static constraints = {
        filename nullable: false, blank: false
    }
    static mapping = {
        filename type: 'text'
    }
}
