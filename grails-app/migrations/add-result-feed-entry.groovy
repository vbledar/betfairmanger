databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424381706025-1") {
        createTable(tableName: "result_feed_entry") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "result_feed_ePK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "data", type: "varchar(255)")

            column(name: "date", type: "varchar(255)")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "game", type: "varchar(255)")

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "market_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "market_name", type: "varchar(255)")

            column(name: "settled", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "time", type: "varchar(255)")

            column(name: "winner", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424381706025-2") {
        addColumn(tableName: "market") {
            column(name: "date_created", type: "datetime") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424381706025-3") {
        addColumn(tableName: "market") {
            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424381706025-4") {
        addColumn(tableName: "market") {
            column(name: "settled", type: "bit")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424381706025-5") {
        addColumn(tableName: "market") {
            column(name: "settlement_on", type: "datetime")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424381706025-6") {
        addColumn(tableName: "market") {
            column(name: "winner", type: "numeric(19,0)") {
                constraints(nullable: "true")
            }
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424381706025-7") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424381706025-8") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }

    changeSet(author: "vbledar (generated)", id: "1424381706025-9") {
        addForeignKeyConstraint(baseColumnNames: "market_id", baseTableName: "result_feed_entry", constraintName: "FK_81iixkh90j0c5yavtx269iy9q", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "market_id", referencedTableName: "market", referencesUniqueColumn: "false")
    }
}
