databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423560596513-1") {
        createTable(tableName: "country_information") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "country_inforPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "capital", type: "varchar(255)")

            column(name: "currency", type: "varchar(255)")

            column(name: "currency_name", type: "varchar(255)")

            column(name: "formal_name", type: "varchar(255)")

            column(name: "iso1number_code", type: "varchar(255)")

            column(name: "iso2letter_code", type: "varchar(255)")

            column(name: "iso3letter_code", type: "varchar(255)")

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "telephone_code", type: "varchar(255)")
        }
    }
}
