package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.domain.model.Company

class CompanyProvider {
    companion object {
        private val companyList = listOf(
            Company(
                id = 1,
                name = "Akamai Technologies",
                description = "A company that provides cloud computing, security, and content delivery services. Akamai's services help businesses and consumers access the internet quickly, reliably, and securely. ",
                type = "Technology",
                ubication = "San José, Costa Rica",
                employees = 150,
                websiteUrl = "https://www.akamai.com/company"
            ),
            Company(
                id = 2,
                name = "Procter & Gamble",
                description = "A multinational consumer goods company that makes and sells personal care, health, and household products.",
                type = "Consumer Goods",
                ubication = "San José, Costa Rica",
                employees = 500,
                websiteUrl = "https://us.pg.com/brands/"
            ),
            Company(
                id = 3,
                name = "Microsoft",
                description = "An technology company that develops and sells software, services, and hardware.",
                type = "Technology",
                ubication = "San José, Costa Rica",
                employees = 340,
                websiteUrl = "https://www.microsoft.com/es-ar/about"
            )
        )

        fun findCompanyById(id: Long): Company? {
            return companyList.find { it.id == id }
        }

        fun findAllCompanies(): List<Company> {
            return companyList
        }
    }
}
