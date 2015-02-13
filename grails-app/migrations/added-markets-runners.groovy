databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423786200850-1") {
        createTable(tableName: "market_runner") {
            column(name: "market_runners_id", type: "numeric(19,0)")

            column(name: "runner_id", type: "numeric(19,0)")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423786200850-2") {
        createTable(tableName: "runner") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "runnerPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "runner_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "selectionid", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "sort_priority", type: "int") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423786200850-3") {
        addColumn(tableName: "market") {
            column(name: "market_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423786200850-4") {
        addColumn(tableName: "market") {
            column(name: "market_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423786200850-5") {
        addColumn(tableName: "market_type") {
            column(name: "active", type: "bit")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423786200850-6") {
        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
    }

//    changeSet(author: "vbledar (generated)", id: "1423786200850-7") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }

    changeSet(author: "vbledar (generated)", id: "1423786200850-8") {
        addForeignKeyConstraint(baseColumnNames: "market_runners_id", baseTableName: "market_runner", constraintName: "FK_2pxlc89mgimels25ofscvylnu", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "market", referencesUniqueColumn: "false")
    }

    changeSet(author: "vbledar (generated)", id: "1423786200850-9") {
        addForeignKeyConstraint(baseColumnNames: "runner_id", baseTableName: "market_runner", constraintName: "FK_28a1uli1fbr3juuxnbhr0edsk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "runner", referencesUniqueColumn: "false")
    }
}
