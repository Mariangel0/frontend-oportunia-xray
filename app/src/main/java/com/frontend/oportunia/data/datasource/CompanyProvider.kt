package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.domain.model.Company

class CompanyProvider {
    companion object {
        private val companyList = listOf(
            Company(
                id = 1L,
                name = "Intel",
                description = "Tech company specialized in semiconductors.",
                type = "Technology",
                ubication = "California, USA",
                employees = 110000,
                websiteUrl = "https://www.intel.com"
            ),
            Company(
                id = 2L,
                name = "IBM",
                description = "Leading company in cloud and AI.",
                type = "Technology",
                ubication = "New York, USA",
                employees = 280000,
                websiteUrl = "https://www.ibm.com"
            ),
            Company(
                id = 4L,
                name = "P&G",
                description = "Consumer goods corporation with a wide range of brands.",
                type = "Consumer Goods",
                ubication = "Ohio, USA",
                employees = 99000,
                websiteUrl = "https://www.pg.com"
            ),
            Company(
                id = 3L,
                name = "Microsoft",
                description = "Multinational software company known for Windows and Azure.",
                type = "Technology",
                ubication = "Redmond, USA",
                employees = 220000,
                websiteUrl = "https://www.microsoft.com"
            ),
            Company(
                id = 5L,
                name = "Google",
                description = "Search engine and tech giant also known for Android and AI.",
                type = "Technology",
                ubication = "California, USA",
                employees = 180000,
                websiteUrl = "https://www.google.com"
            ),
            Company(
                id = 6L,
                name = "Amazon",
                description = "E-commerce and cloud computing leader.",
                type = "E-commerce",
                ubication = "Seattle, USA",
                employees = 1600000,
                websiteUrl = "https://www.amazon.com"
            )
        )

        fun findAllCompanies(): List<Company> {
            return companyList
        }

        fun findCompanyById(id: Long): Company? {
            return companyList.find { it.id == id }
        }
    }
}
