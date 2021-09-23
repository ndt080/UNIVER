using System.Threading.Tasks;
using Domain.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace Server.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ApiController : ControllerBase
    {
        private readonly ISqlWorkService _service;
        private readonly ILogger<ApiController> _logger;

        public ApiController(ILogger<ApiController> logger, ISqlWorkService service)
        {
            _logger = logger;
            _service = service;
        }

        [HttpGet]
        [Route("GetAllTables")]
        public async Task<IActionResult> GetAllTables(string connectionString)
        {
            var output = await _service.GetAllTables(connectionString);
            if (output == null)
            {
                return NotFound();
            }

            return Ok(output);
        }

        [HttpGet]
        [Route("GetTableData")]
        public async Task<IActionResult> GetTableData(string connectionString, string tableName)
        {
            if (tableName == null || connectionString == null)
            {
                return BadRequest();
            }

            var output = await _service.GetTableData(connectionString, tableName);
            if (output == null)
            {
                return NotFound();
            }

            return Ok(output);
        }

        [HttpGet]
        [Route("SetTableData")]
        public async Task<IActionResult> SetTableData(string connectionString, string tableName, string tableData)
        {
            if (tableData == null || connectionString == null)
            {
                return BadRequest();
            }

            await _service.SetTableData(connectionString, tableName, tableData);
            return Ok();
        }
    }
}