databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423609257813-1") {
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

            column(name: "country_id", type: "varchar(255)")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423609257813-2") {
        addForeignKeyConstraint(baseColumnNames: "country_id", baseTableName: "competition", constraintName: "FK_vdg5pkr7wr7k8yfmpsm3e5i8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "country_code", referencedTableName: "country", referencesUniqueColumn: "false")
    }
}
