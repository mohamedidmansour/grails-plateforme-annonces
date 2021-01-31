package com.mbds.projet

class SaleAd {

    String title
    String description
    String longDescription
    Boolean status = Boolean.FALSE
    Float price
    Date dateCreated
    Date lastUpdated

    static belongsTo = [author: User]

    static hasMany = [illustrations: Illustration]

    static constraints = {
        title nullable: false, blank: false, size: 5..50
        description nullable: false, blank: false
        longDescription nullable: false, blank: false
        status nullable: false
        price min: 0F, scale: 2
    }
}
