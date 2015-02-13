databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423788817598-1") {
        createTable(tableName: "market") {
            column(name: "market_id", type: "varchar(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "marketPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "event_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "market_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "market_type_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423788817598-2") {
        createTable(tableName: "market_runner") {
            column(name: "market_runners_id", type: "varchar(255)")

            column(name: "runner_id", type: "numeric(19,0)")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423788817598-3") {
        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
    }

//    changeSet(author: "vbledar (generated)", id: "1423788817598-4") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }

    changeSet(author: "vbledar (generated)", id: "1423788817598-5") {
        addForeignKeyConstraint(baseColumnNames: "event_id", baseTableName: "market", constraintName: "FK_6j5ago9pl95d2cid4y1hc2oec", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "event", referencesUniqueColumn: "false")
    }

    changeSet(author: "vbledar (generated)", id: "1423788817598-6") {
        addForeignKeyConstraint(baseColumnNames: "market_type_id", baseTableName: "market", constraintName: "FK_8r5kvax3j1jac9vx4bu6pd7jx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "name", referencedTableName: "market_type", referencesUniqueColumn: "false")
    }

    changeSet(author: "vbledar (generated)", id: "1423788817598-7") {
        addForeignKeyConstraint(baseColumnNames: "market_runners_id", baseTableName: "market_runner", constraintName: "FK_2pxlc89mgimels25ofscvylnu", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "market_id", referencedTableName: "market", referencesUniqueColumn: "false")
    }

    changeSet(author: "vbledar (generated)", id: "1423788817598-8") {
        addForeignKeyConstraint(baseColumnNames: "runner_id", baseTableName: "market_runner", constraintName: "FK_28a1uli1fbr3juuxnbhr0edsk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "runner", referencesUniqueColumn: "false")
    }
}
