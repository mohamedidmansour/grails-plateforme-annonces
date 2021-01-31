package com.mbds.projet

import grails.gorm.services.Service

@Service(SaleAd)
interface SaleAdService {

    SaleAd get(Serializable id)

    List<SaleAd> list(Map args)

    Long count()

    void delete(Serializable id)

    SaleAd save(SaleAd saleAd)

}