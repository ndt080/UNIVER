using System.Collections.Generic;
using System.Threading.Tasks;

namespace Domain.Services
{
    public interface ISqlWorkService
    {
        public Task<List<string>> GetAllTables(string connectionString);
        public Task<string> GetTableData(string connectionString, string tableName);
        public Task<string> GetTableDataWithQuery(string connectionString, string query);
        public Task UpdateTableData(string connectionString, string tableName, string tableData);
        public Task InsertTableData(string connectionString, string tableName, string tableData);
        public Task InsertRow(string connectionString, string tableName, string[] row);
        public Task DeleteRow(string connectionString, string tableName, string column, string rowId);
    }
}