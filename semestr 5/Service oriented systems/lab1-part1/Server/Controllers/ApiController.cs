﻿using System;
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
            try
            {
                var output = await _service.GetAllTables(connectionString);
                if (output == null)
                {
                    return NotFound();
                }

                return Ok(output);
            }
            catch (Exception e)
            {
                return BadRequest(e);
            }
        }

        [HttpGet]
        [Route("GetTableData")]
        public async Task<IActionResult> GetTableData(string connectionString, string tableName)
        {
            try
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
            catch (Exception e)
            {
                return BadRequest(e);
            }
        }
        
        [HttpGet]
        [Route("GetTableDataWithQuery")]
        public async Task<IActionResult> GetTableDataWithQuery(string connectionString, string query)
        {
            try
            {
                if (query == null || connectionString == null)
                {
                    return BadRequest();
                }

                var output = await _service.GetTableDataWithQuery(connectionString, query);
                if (output == null)
                {
                    return NotFound();
                }

                return Ok(output);
            }
            catch (Exception e)
            {
                return BadRequest(e);
            }
        }

        [HttpGet]
        [Route("UpdateTableData")]
        public async Task<IActionResult> UpdateTableData(string connectionString, string tableName, string tableData)
        {
            try
            {
                if (tableData == null || connectionString == null)
                {
                    return BadRequest();
                }

                await _service.UpdateTableData(connectionString, tableName, tableData);
                return Ok();
            }
            catch (Exception e)
            {
                return BadRequest(e);
            }
        }

        [HttpGet]
        [Route("InsertTableData")]
        public async Task<IActionResult> InsertTableData(string connectionString, string tableName, string tableData)
        {
            try
            {
                if (tableData == null || connectionString == null)
                {
                    return BadRequest();
                }

                await _service.InsertTableData(connectionString, tableName, tableData);
                return Ok();
            }
            catch (Exception e)
            {
                return BadRequest(e);
            }
        }

        [HttpPost]
        [Route("InsertRow")]
        public async Task<IActionResult> InsertRow(string connectionString, string tableName, string[] row)
        {
            try
            {
                if (row == null || connectionString == null)
                {
                    return BadRequest();
                }

                await _service.InsertRow(connectionString, tableName, row);
                return Ok();
            }
            catch (Exception e)
            {
                return BadRequest(e);
            }
        }

        [HttpDelete]
        [Route("DeleteRow")]
        public async Task<IActionResult> DeleteRow(string connectionString, string tableName, string column,
            string rowId)
        {
            if (rowId == null || connectionString == null)
            {
                return BadRequest();
            }

            await _service.DeleteRow(connectionString, tableName, column, rowId);
            return Ok();
        }
    }
}