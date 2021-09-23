using System.Collections.Generic;
using System.Threading.Tasks;

namespace Domain.Services
{
    public interface ISqlWorkService
    {
        public Task<List<string>> GetAllTables(string connectionString);
        public Task<string> GetTableData(string connectionString, string tableName);
        public Task SetTableData(string connectionString, string tableName, string tableData);
    }
}