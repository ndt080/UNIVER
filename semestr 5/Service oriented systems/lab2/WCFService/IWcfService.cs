using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Threading.Tasks;

namespace WCFService
{
    [ServiceContract]
    public interface IWcfService
    {

        [OperationContract]
        public Task<List<string>> GetAllTables(string connectionString);

        [OperationContract]
        public Task<string> GetTableData(string connectionString, string tableName);

        [OperationContract]
        public Task<string> GetTableDataWithQuery(string connectionString, string query);

        [OperationContract]
        public Task UpdateTableData(string connectionString, string tableName, string tableData);

        [OperationContract]
        public Task InsertTableData(string connectionString, string tableName, string tableData);

        [OperationContract]
        public Task InsertRow(string connectionString, string tableName, string[] row);

        [OperationContract]
        public Task DeleteRow(string connectionString, string tableName, string column, string rowId);
    }
}