package projet_maroc_grails_rest

import com.mbds.projet.Illustration
import com.mbds.projet.Role
import com.mbds.projet.SaleAd
import com.mbds.projet.User
import com.mbds.projet.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminUser = new User(username: "admin", password: "password").save()
        // à ce stade l'utilisateur n'est peut être pas encore sauvegardé
        def adminRole = new Role(authority: "ROLE_ADMIN").save()
        def clientRole = new Role(authority: "ROLE_CLIENT").save()
        def ModeratorRole = new Role(authority: "ROLE_MODERATOR").save()
        UserRole.create(adminUser, adminRole, true)

        // On crée 5 utilisateurs
        ["Bob", "Alice", "IDMANSOUR", "MOHAMED", "AYOUB"].each {
            String username ->
                // On crée une instance d'utilisateur
                def userInstance = new User(username: username, password: "password")
                // On itère sur une liste de 5
                (1..5).each {
                    Integer index ->
                        // On crée une annonce
                        def saleAdInstance = new SaleAd(
                                title: "title " + index,
                                description: "description " + index,
                                longDescription: "Description longue de l'annonce " + index,
                                status: Boolean.TRUE,
                                price: 100F * index
                        )
                        // On itère sur une liste de 5
                        (1..5).each {
                            // On crée et ajoute une illustration à l'annonce
                            saleAdInstance.addToIllustrations(
                                    new Illustration(filename: "assets\\images\\avatar\\g.png")
                            )
                        }
                        // On ajoute l'annonce à l'utilisateur
                        userInstance.addToSaleAds(saleAdInstance)
                        // On sauvegarde le parent le plus haut qui va sauvegarder les enfants
                        userInstance.save(flush: true, failOnError: true)
                        UserRole.create(userInstance, Role.findByAuthority("ROLE_CLIENT"), true)

                }
        }
    }

    def destroy = {

    }
}
