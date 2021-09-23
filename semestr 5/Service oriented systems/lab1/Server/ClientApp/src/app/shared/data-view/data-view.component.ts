import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { ApiService } from "../../core/services/api.service";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { NotificationService } from "../../core/services/notification.service";

interface fieldTable {
  field: string;
  sortable: boolean;
  filter: boolean;
  checkboxSelection: boolean;
  cellEditor: string;
  editable: boolean;
}

@Component({
  selector: "data-view",
  templateUrl: "./data-view.component.html",
  styleUrls: ["./data-view.component.css"],
})
export class DataViewComponent implements OnInit, OnDestroy {
  @Input() data;
  @Input() tableName;
  @Input() saving;
  private destroy = new Subject();
  isEdited = false;
  bodyTable;
  headerTable: Array<any> = [];

  constructor(
    private api: ApiService,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    let keys = Object.keys(this.data[0]);
    keys.forEach((item) => {
      this.headerTable.push({
        field: item,
        sortable: true,
        filter: true,
        checkboxSelection: item == keys[0],
        cellEditor: "agTextCellEditor",
        editable: true,
      } as fieldTable);
    });
    this.bodyTable = this.data;
    console.log(this.headerTable);
  }

  savingChanges() {
    this.api
      .setTableData(this.bodyTable, this.tableName)
      .pipe(takeUntil(this.destroy))
      .subscribe(
        (x) => {
          console.log(this.bodyTable);
          console.log(x);
          this.notification.showSuccess("Success saving table data!", "");
        },
        (error) => {
          console.log(error);
          this.notification.showError(error, "Error! Incorrect data");
        }
      );
  }

  ngOnDestroy() {
    this.destroy.next(true);
  }
}
