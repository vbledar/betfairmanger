databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423772619829-1") {
        addColumn(tableName: "event") {
            column(name: "competition_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423772619829-2") {
        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
    }

//    changeSet(author: "vbledar (generated)", id: "1423772619829-3") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }

    changeSet(author: "vbledar (generated)", id: "1423772619829-4") {
        addForeignKeyConstraint(baseColumnNames: "competition_id", baseTableName: "event", constraintName: "FK_8nb3b3d1vkd5foxerxr8egomd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "competition_id", referencedTableName: "competition", referencesUniqueColumn: "false")
    }
}
