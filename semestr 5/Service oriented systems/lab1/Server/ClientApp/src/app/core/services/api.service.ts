import { Inject, Injectable } from "@angular/core";
import { NotificationService } from "./notification.service";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ApiService {
  private connectionString: string;
  url;

  constructor(
    public http: HttpClient,
    @Inject("BASE_URL") baseUrl: string,
    private notification: NotificationService
  ) {
    this.url = baseUrl;
  }

  createConnectionString(dataSource: string) {
    this.connectionString = `Provider=Microsoft.ACE.OLEDB.12.0;Data Source=${dataSource};Persist Security Info=False;`;
    this.notification.showSuccess("Success create connection string", "");
  }

  getTablesName(): Observable<any> {
    return this.http.get<boolean>(
      this.url +
        "Api/GetAllTables?" +
        `connectionString=${this.connectionString}`
    );
  }

  getTableData(table: string): Observable<any> {
    return this.http.get<boolean>(
      this.url +
      "Api/GetTableData?" +
      `connectionString=${this.connectionString}` + "&"+
      `tableName=${table}`
    );
  }

  setTableData(table: [any], tableName: string): Observable<any> {
    return this.http.get<string>(
      this.url +
      "Api/SetTableData?" +
      `connectionString=${this.connectionString}&`+
      `tableName=${table}&` +
      `tableData=${JSON.stringify(table)}`
    );
  }
}
