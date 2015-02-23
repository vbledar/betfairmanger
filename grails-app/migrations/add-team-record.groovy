databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424439316236-1") {
        createTable(tableName: "team") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "teamPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "competition_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424439316236-2") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424439316236-3") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424439316236-4") {
//        modifyDataType(columnName: "date_created", newDataType: "datetime", schemaName: "dbo", tableName: "market")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424439316236-5") {
//        addNotNullConstraint(columnDataType: "datetime", columnName: "date_created", schemaName: "dbo", tableName: "market")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424439316236-6") {
//        modifyDataType(columnName: "last_updated", newDataType: "datetime", schemaName: "dbo", tableName: "market")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424439316236-7") {
//        addNotNullConstraint(columnDataType: "datetime", columnName: "last_updated", schemaName: "dbo", tableName: "market")
//    }

    changeSet(author: "vbledar (generated)", id: "1424439316236-8") {
        addForeignKeyConstraint(baseColumnNames: "competition_id", baseTableName: "team", constraintName: "FK_6bt9br9tgbyyrauqdb58vfp7l", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "competition_id", referencedTableName: "competition", referencesUniqueColumn: "false")
    }
}
