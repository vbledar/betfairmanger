databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423740886390-1") {
        createTable(tableName: "event") {
            column(name: "id", type: "varchar(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "eventPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "country_id", type: "varchar(255)")

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "open_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "timezone", type: "varchar(255)")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423740886390-2") {
        createTable(tableName: "market") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "marketPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "event_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "market_type_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423740886390-3") {
        addColumn(tableName: "country") {
            column(name: "country_information_id", type: "numeric(19,0)") {
                constraints(nullable: "true", unique: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423740886390-8") {
        createIndex(indexName: "country_information_id_uniq_1423740871951", tableName: "country", unique: "false") {
            column(name: "country_information_id")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423740886390-4") {
        addForeignKeyConstraint(baseColumnNames: "country_information_id", baseTableName: "country", constraintName: "FK_7uapkxahw6sso6pas8pxjvsql", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "country_information", referencesUniqueColumn: "false")
    }

    changeSet(author: "vbledar (generated)", id: "1423740886390-5") {
        addForeignKeyConstraint(baseColumnNames: "country_id", baseTableName: "event", constraintName: "FK_n9aeg7qa42mxrr1rj5h9xoicj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "country_code", referencedTableName: "country", referencesUniqueColumn: "false")
    }

    changeSet(author: "vbledar (generated)", id: "1423740886390-6") {
        addForeignKeyConstraint(baseColumnNames: "event_id", baseTableName: "market", constraintName: "FK_6j5ago9pl95d2cid4y1hc2oec", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "event", referencesUniqueColumn: "false")
    }

    changeSet(author: "vbledar (generated)", id: "1423740886390-7") {
        addForeignKeyConstraint(baseColumnNames: "market_type_id", baseTableName: "market", constraintName: "FK_8r5kvax3j1jac9vx4bu6pd7jx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "name", referencedTableName: "market_type", referencesUniqueColumn: "false")
    }
}
