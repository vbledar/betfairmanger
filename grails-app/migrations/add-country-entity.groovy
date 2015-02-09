databaseChangeLog = {

    changeSet(author: "Bledar (generated)", id: "1423514046248-1") {
        createTable(tableName: "country") {
            column(name: "country_code", type: "varchar(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "countryPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bledar (generated)", id: "1423514046248-2") {
        addColumn(tableName: "event_type") {
            column(name: "event_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
