import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { NotificationService } from "../../core/services/notification.service";
import { ApiService } from "../../core/services/api.service";
import { StorageService } from "../../core/services/storage.service";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"],
})
export class HomeComponent implements OnInit, OnDestroy {
  private destroy = new Subject();
  public dataSource: string;
  public tables: Array<string>;
  public refresh = false;
  dataTable;

  fileControl = new FormControl("", [
    Validators.required,
    Validators.minLength(5),
  ]);
  queryControl = new FormControl("", [
    Validators.required,
    Validators.minLength(10),
  ]);
  selectTableControl = new FormControl("", [Validators.required]);

  form: FormGroup = new FormGroup({
    file: this.fileControl,
    query: this.queryControl,
    selectTable: this.selectTableControl,
  });

  constructor(
    public notification: NotificationService,
    private api: ApiService,
    private storage: StorageService
  ) {}

  ngOnInit() {
    this.fileControl.valueChanges
      .pipe(takeUntil(this.destroy))
      .subscribe((x) => {
        this.dataSource = this.fileControl.value;
      });
    this.dataSource = this.storage.dataSourceConnection;
  }

  refreshTable() {
    this.refresh = true;
    // this.selectTable();
    setTimeout(() => {
      this.refresh = false;
    }, 1000);
  }

  connectDB() {
    this.api.createConnectionString(this.dataSource);
    this.api
      .getTablesName()
      .pipe(takeUntil(this.destroy))
      .subscribe(
        (x) => {
          this.tables = x;
          console.log(x);
          this.notification.showSuccess("Success get tables name of DB!", "");
        },
        (error) => {
          console.log(error);
          this.notification.showError(error, "Error!");
        }
      );
    this.storage.dataSourceConnection = this.dataSource;
  }

  selectTable() {
    this.api.getTableData(this.selectTableControl.value).subscribe(
      (x) => {
        this.dataTable = x;
        this.notification.showSuccess(
          `Success get table ${this.selectTableControl.value}!`,
          ""
        );
      },
      (error) => {
        console.log(error);
        this.notification.showError(error, "Error!");
      }
    );
    this.refreshTable();
  }

  executeQuery(){
    this.api.getTableDataWithQuery(this.queryControl.value).subscribe(
      (x) => {
        this.dataTable = x;
        this.notification.showSuccess(
          `Success execute the SQL query!`,
          ""
        );
      },
      (error) => {
        console.log(error);
        this.notification.showError(error.error, "Error!");
      }
    );
    this.refreshTable();
  }

  ngOnDestroy() {
    this.destroy.next(true);
  }
}
