databaseChangeLog = {

    changeSet(author: "Bledar (generated)", id: "1423247851053-1") {
        createTable(tableName: "betfair_error") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "betfair_errorPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "code", type: "varchar(255)")

            column(name: "message", type: "varchar(255)")
        }
    }

    changeSet(author: "Bledar (generated)", id: "1423247851053-2") {
        createTable(tableName: "betfair_exception") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "betfair_excepPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "betfair_error_id", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "error_code", type: "varchar(255)")

            column(name: "error_details", type: "varchar(255)")

            column(name: "exception_name", type: "varchar(255)")

            column(name: "requestuuid", type: "varchar(255)")
        }
    }

    changeSet(author: "Bledar (generated)", id: "1423247851053-3") {
        createTable(tableName: "developer_app") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "developer_appPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "app_id", type: "numeric(19,0)")

            column(name: "app_name", type: "varchar(255)")
        }
    }

    changeSet(author: "Bledar (generated)", id: "1423247851053-4") {
        createTable(tableName: "developer_app_version") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "developer_verPK")
            }

            column(name: "version", type: "varchar(255)")

            column(name: "active", type: "bit")

            column(name: "application_key", type: "varchar(255)")

            column(name: "delay_data", type: "bit")

            column(name: "developer_app_id", type: "numeric(19,0)")

            column(name: "owner", type: "varchar(255)")

            column(name: "owner_managed", type: "bit")

            column(name: "subscription_required", type: "bit")

            column(name: "version_id", type: "numeric(19,0)")
        }
    }

    changeSet(author: "Bledar (generated)", id: "1423247851053-5") {
        createTable(tableName: "event_type") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "event_typePK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "processed", type: "bit")
        }
    }

    changeSet(author: "Bledar (generated)", id: "1423247851053-8") {
        createIndex(indexName: "betfair_error_id_uniq_1423247850992", tableName: "betfair_exception", unique: "true") {
            column(name: "betfair_error_id")
        }
    }

    changeSet(author: "Bledar (generated)", id: "1423247851053-6") {
        addForeignKeyConstraint(baseColumnNames: "betfair_error_id", baseTableName: "betfair_exception", constraintName: "FK_1v2yssjtahatvvk9mg0ittl1r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "betfair_error", referencesUniqueColumn: "false")
    }

    changeSet(author: "Bledar (generated)", id: "1423247851053-7") {
        addForeignKeyConstraint(baseColumnNames: "developer_app_id", baseTableName: "developer_app_version", constraintName: "FK_q0fbtf1bfc7kps7yj8tr63ruj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "developer_app", referencesUniqueColumn: "false")
    }

	include file: 'add-country-entity.groovy'

	include file: 'add-country-infromation.groovy'


	include file: 'add-competition-entity.groovy'

	include file: 'add-market-type-entity.groovy'

	include file: 'add-ci-on-country.groovy'

	include file: 'connect-event-competition.groovy'
}
