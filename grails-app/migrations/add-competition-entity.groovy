databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423591493834-1") {
        createTable(tableName: "competition") {
            column(name: "competition_id", type: "varchar(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "competitionPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "competition_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
