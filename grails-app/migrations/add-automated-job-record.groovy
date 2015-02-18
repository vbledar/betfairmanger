databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424297144356-1") {
        createTable(tableName: "automated_job_retrieval") {
            column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "automated_jobPK")
            }

            column(name: "version", type: "numeric(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "automatic", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "server_name", type: "varchar(255)")

            column(name: "successful", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424297144356-2") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }
//
//    changeSet(author: "vbledar (generated)", id: "1424297144356-3") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }
}
