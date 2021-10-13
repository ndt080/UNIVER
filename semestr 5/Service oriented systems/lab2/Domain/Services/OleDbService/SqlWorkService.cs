using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Domain.Services
{
    public class SqlWorkService : ISqlWorkService
    {
        private OleDbConnection Connection { get; set; }

        private void OpenConnection()
        {
            Connection.Open();
        }

        private void CloseConnection()
        {
            Connection.Close();
        }

        public Task<List<string>> GetAllTables(string connectionString)
        {
            return Task.Run(() =>
            {
                Connection = new OleDbConnection();
                Connection.ConnectionString = connectionString;
                OpenConnection();
                var tableInfo = Connection.GetOleDbSchemaTable(OleDbSchemaGuid.Tables,
                    new object[]
                    {
                        null, null, null, "TABLE"
                    });
                var tables = (from DataRow row in tableInfo?.Rows
                    select row["TABLE_NAME"].ToString()).ToList();
                CloseConnection();
                return tables;
            });
        }

        public Task<string> GetTableData(string connectionString, string tableName)
        {
            return Task.Run(async () =>
            {
                var query = $"SELECT * FROM [{tableName}]";
                Connection = new OleDbConnection();
                Connection.ConnectionString = connectionString;
                OpenConnection();
                var dr = new OleDbCommand(query, Connection).ExecuteReader();
                var tableData = new DataTable();
                tableData.Load(dr);

                CloseConnection();
                return await DataTableToJsonWithJsonNet(tableData);
            });
        }

        public Task<string> GetTableDataWithQuery(string connectionString, string query)
        {
            return Task.Run(async () =>
            {
                Connection = new OleDbConnection();
                Connection.ConnectionString = connectionString;
                OpenConnection();
                var dr = new OleDbCommand(query, Connection).ExecuteReader();
                var tableData = new DataTable();
                tableData.Load(dr);
                CloseConnection();
                return await DataTableToJsonWithJsonNet(tableData);
            });
        }

        public Task UpdateTableData(string connectionString, string tableName, string tableData)
        {
            return Task.Run(async () =>
            {
                var dataTable = await JsonToDataTable(tableData);
                var columns = dataTable.Columns;
                var rows = dataTable.Rows;

                foreach (DataRow row in rows)
                {
                    Connection = new OleDbConnection();
                    Connection.ConnectionString = connectionString;

                    var options = (false, new DataColumn(), new object());
                    var queryBuilder = new StringBuilder($"UPDATE {tableName} SET ");

                    for (var cell = 0; cell < row.ItemArray.Length; cell++)
                    {
                        if (!options.Item1)
                        {
                            options.Item1 = true;
                            options.Item2 = columns[cell];
                            options.Item3 = row.ItemArray[cell];
                        }
                        else
                        {
                            queryBuilder.Append($"{tableName}.{columns[cell]}='{row.ItemArray[cell]}', ");
                        }
                    }

                    queryBuilder.Length -= 2;
                    queryBuilder.Append($" WHERE {tableName}.{options.Item2}='{options.Item3}';");

                    var cmd = new OleDbCommand(queryBuilder.ToString(), Connection);

                    OpenConnection();
                    cmd.ExecuteNonQuery();
                    CloseConnection();
                }

                return Task.CompletedTask;
            });
        }

        public Task InsertTableData(string connectionString, string tableName, string tableData)
        {
            return Task.Run(async () =>
            {
                var dataTable = await JsonToDataTable(tableData);
                var rows = dataTable.Rows;

                foreach (DataRow row in rows)
                {
                    Connection = new OleDbConnection();
                    Connection.ConnectionString = connectionString;

                    var queryBuilder = new StringBuilder($"INSERT INTO {tableName} VALUES (");

                    foreach (var cell in row.ItemArray)
                    {
                        queryBuilder.Append($"'{cell}', ");
                    }

                    queryBuilder.Length -= 2;
                    queryBuilder.Append(");");

                    var cmd = new OleDbCommand(queryBuilder.ToString(), Connection);

                    OpenConnection();
                    cmd.ExecuteNonQuery();
                    CloseConnection();
                }

                return Task.CompletedTask;
            });
        }

        public Task InsertRow(string connectionString, string tableName, string[] row)
        {
            return Task.Run(() =>
            {
                Connection = new OleDbConnection();
                Connection.ConnectionString = connectionString;

                var queryBuilder = new StringBuilder($"INSERT INTO {tableName} VALUES (");

                foreach (var cell in row)
                {
                    queryBuilder.Append($"'{cell}', ");
                }

                queryBuilder.Length -= 2;
                queryBuilder.Append(");");

                var cmd = new OleDbCommand(queryBuilder.ToString(), Connection);

                OpenConnection();
                cmd.ExecuteNonQuery();
                CloseConnection();
            });
        }

        public Task DeleteRow(string connectionString, string tableName, string column, string rowId)
        {
            return Task.Run(() =>
            {
                Connection = new OleDbConnection();
                Connection.ConnectionString = connectionString;

                var queryBuilder = new StringBuilder($"DELETE FROM {tableName} WHERE {tableName}.{column}='{rowId}'");
                var cmd = new OleDbCommand(queryBuilder.ToString(), Connection);

                OpenConnection();
                cmd.ExecuteNonQuery();
                CloseConnection();
            });
        }

        private static async Task<DataTable> JsonToDataTable(string tableData)
        {
            return await Task.Run(() =>
                JsonConvert.DeserializeObject<DataTable>(tableData,
                    new IsoDateTimeConverter { DateTimeFormat = "yyyy-MM-dd" }));
        }

        private static async Task<string> DataTableToJsonWithJsonNet(DataTable table)
        {
            return await Task.Run(() => JsonConvert.SerializeObject(table));
        }

        private static async Task<string> ErrorToJsonWithJsonNet(Exception e)
        {
            return await Task.Run(() => JsonConvert.SerializeObject(e));
        }
    }
}