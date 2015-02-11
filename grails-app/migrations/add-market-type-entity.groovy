databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423677945252-1") {
        createTable(tableName: "market_type") {
            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "market_typePK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }
        }
    }
}
